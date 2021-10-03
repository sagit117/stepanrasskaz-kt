package ru.axel.stepanrasskaz.controllers

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.axel.stepanrasskaz.Config
import ru.axel.stepanrasskaz.connectors.DataBase
import ru.axel.stepanrasskaz.connectors.Mailer
import ru.axel.stepanrasskaz.domain.user.UserRepository
import ru.axel.stepanrasskaz.domain.user.services.UserService
import ru.axel.stepanrasskaz.domain.user.session.UserSession
import ru.axel.stepanrasskaz.domain.user.auth.dto.AuthDTO
import ru.axel.stepanrasskaz.domain.user.auth.dto.ChangePasswordDTO
import ru.axel.stepanrasskaz.domain.user.auth.dto.RegistryDTO
import ru.axel.stepanrasskaz.domain.user.auth.dto.SetCodeDTO
import ru.axel.stepanrasskaz.domain.user.session.UserDataMemory
import ru.axel.stepanrasskaz.domain.user.session.UserID
import ru.axel.stepanrasskaz.domain.user.session.UserStack
import ru.axel.stepanrasskaz.templates.codeForChangePassword
import ru.axel.stepanrasskaz.templates.entryMail
import ru.axel.stepanrasskaz.templates.layouts.EmptyLayout
import ru.axel.stepanrasskaz.templates.pages.ChangePasswordPage
import ru.axel.stepanrasskaz.templates.pages.LoginPage
import ru.axel.stepanrasskaz.templates.pages.RecoveryPasswordPage
import ru.axel.stepanrasskaz.templates.pages.RegistryPage
import ru.axel.stepanrasskaz.templates.registryMail
import ru.axel.stepanrasskaz.utils.ConfigJWT
import ru.axel.stepanrasskaz.utils.ConfigMailer
import ru.axel.stepanrasskaz.utils.randomCode

fun Route.authRoute(configJWT: ConfigJWT, configMailer: ConfigMailer) {
    get("/login") {
        val connectUserData = try {
            call.attributes[Config.userRepoAttributeKey]
        } catch (error: Exception) {
            null
        }

        if (connectUserData == null) {
            call.respondHtmlTemplate(EmptyLayout(LoginPage())) {

            }
        } else {
            call.respondRedirect("/account/${connectUserData.id}")
        }
    }

    get("/registry") {
        val connectUserData: UserRepository? = try {
            call.attributes[Config.userRepoAttributeKey]
        } catch (error: Exception) {
            null
        }

        if (connectUserData == null) {
            call.respondHtmlTemplate(EmptyLayout(RegistryPage())) {

            }
        } else {
            call.respondRedirect("/account/${connectUserData.id}")
        }
    }

    route("/password") {
        get("/recovery") {
            val connectUserData = try {
                call.attributes[Config.userRepoAttributeKey]
            } catch (error: Exception) {
                null
            }

            if (connectUserData == null) {
                call.respondHtmlTemplate(EmptyLayout(RecoveryPasswordPage())) {

                }
            } else {
                call.respondRedirect("/account/${connectUserData.id}")
            }
        }

        get("/change") {
            val connectUserData = try {
                call.attributes[Config.userRepoAttributeKey]
            } catch (error: Exception) {
                null
            }

            if (connectUserData == null) {
                call.respondHtmlTemplate(EmptyLayout(ChangePasswordPage())) {

                }
            } else {
                call.respondRedirect("/account/${connectUserData.id}")
            }
        }
    }

    route("/api/v1") {
        post("/login") {
            val authData = call.receive<AuthDTO>()

            /** проверяем на ошибки в веденных данных */
            val authDTO = try {
                AuthDTO(authData.login, authData.password)
            } catch (error: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to error.message.toString()))

                return@post
            }

            val userService = UserService(DataBase.getCollection())

            val user = userService.getUser(authDTO)

            if (user?.let { userService.checkAuth(it, authDTO) } == true) {

                /** создать jwt для ответа */
                val token = userService.createJWT(configJWT, user)

                call.sessions.set(UserSession(token = token))

                call.respond(HttpStatusCode.OK, mapOf("id" to user.id.toString(), "token" to token))

                Mailer(configMailer)
                    .send(
                        "Вы вошли в систему",
                        entryMail(),
                        setOf(user.email),
                        "Если это были не Вы, восстановите пароль!"
                    )
            } else {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }

        post("/registry") {
            val registryData = call.receive<RegistryDTO>()

            /** проверяем на ошибки в веденных данных */
            val registryDTO = try {
                RegistryDTO(registryData.login, registryData.password)
            } catch (error: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to error.message.toString()))

                return@post
            }

            val userService = UserService(DataBase.getCollection())

            val user = userService.getUser(registryDTO)

            if (user == null) {
                val result = userService.insertOne(registryDTO)

                if (result.wasAcknowledged()) {
                    call.respond(HttpStatusCode.OK)

                    Mailer(configMailer)
                        .send(
                            "Вы зарегистрированы",
                            registryMail(),
                            setOf(registryDTO.getEmail()),
                            "Вы успешно зарегистрировались на ресурсе!"
                        )
                } else {
                    call.respond(HttpStatusCode.InternalServerError)
                }
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        route("/password") {
            post("/code/set") {
                val setCodeData = call.receive<SetCodeDTO>()

                /** проверяем на ошибки в веденных данных */
                val setCodeDTO = try {
                    SetCodeDTO(setCodeData.login)
                } catch (error: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to error.message.toString()))

                    return@post
                }

                val userService = UserService(DataBase.getCollection())

                val user = userService.getUser(setCodeDTO.getEmail())
                val id = call.sessions.get<UserID>()?.id

                if (user != null && id != null) {
                    val code = randomCode(10)

                    UserStack.setUserDbId(id, user.id.toString())
                    UserStack.setPassCode(id, code)

                    call.respond(HttpStatusCode.OK)

                    Mailer(configMailer)
                        .send(
                            "Код для восстановления пароля",
                            codeForChangePassword(code),
                            setOf(user.email),
                            "Ваш код для востановления пароля $code, введите его в соответствующее поле. Важно использовать тот-же браузер, с которого был запрос!"
                        )
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            post("/change") {
                val changePassData = call.receive<ChangePasswordDTO>()

                /** проверяем на ошибки в веденных данных */
                val changePassDTO = try {
                    ChangePasswordDTO(changePassData.code, changePassData.password)
                } catch (error: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to error.message.toString()))

                    return@post
                }

                val id = call.sessions.get<UserID>()?.id

                if (id != null) {
                    val userDataMemory: UserDataMemory? = UserStack.getUser(id)
                    val userId = userDataMemory?.userDbId
                    val passwordChangeCode = userDataMemory?.passwordChangeCode

                    if (userId != null && passwordChangeCode != null) {
                        if (passwordChangeCode == changePassDTO.code) {
                            val userService = UserService(DataBase.getCollection())

                            val result = userService.updatePassword(userId, changePassDTO.password)

                            if (result.wasAcknowledged()) {
                                call.respond(HttpStatusCode.OK)

                                UserStack.setPassCode(id, null)

                                val user = userService.findOneById(userId)

                                if (user != null) {
                                    Mailer(configMailer)
                                        .send(
                                            "Вы сменили пароль от ресурса",
                                            registryMail(),
                                            setOf(user.email),
                                            "Вы успешно сменили пароль от ресурса!"
                                        )
                                }
                            } else {
                                /** ошибка записи в БД */
                                call.respond(HttpStatusCode.InternalServerError)
                            }
                        } else {
                            /** код не верен */
                            UserStack.setCountRequestChangePass(id)

                            call.respond(HttpStatusCode.Unauthorized)
                        }
                    } else {
                        /** или неопределен id для базы или код */
                        call.respond(HttpStatusCode.BadRequest)
                    }
                } else {
                    /** запрос без куки */
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}