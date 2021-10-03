package ru.axel.stepanrasskaz.controllers

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import kotlinx.coroutines.runBlocking
import ru.axel.stepanrasskaz.Config
import ru.axel.stepanrasskaz.connectors.DataBase
import ru.axel.stepanrasskaz.connectors.Mailer
import ru.axel.stepanrasskaz.domain.user.UserRepository
import ru.axel.stepanrasskaz.domain.user.services.UserService
import ru.axel.stepanrasskaz.domain.user.session.UserSession
import ru.axel.stepanrasskaz.domain.user.auth.dto.AuthDTO
import ru.axel.stepanrasskaz.domain.user.auth.dto.RegistryDTO
import ru.axel.stepanrasskaz.domain.user.auth.dto.SetCodeDTO
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

    post("/api/v1/login") {
        val authData = call.receive<AuthDTO>()

        /** проверяем на ошибки в веденных данных */
        val authDTO = try {
            AuthDTO(authData.login, authData.password)
        } catch (error: IllegalArgumentException) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to error.message.toString()))

            return@post
        }

        val userService = UserService(DataBase.getCollection())

        runBlocking {
            val user = userService.getUser(authDTO)

            if (user?.let { it -> userService.checkAuth(it, authDTO) } == true) {

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

    post("api/v1/registry") {
        val registryData = call.receive<RegistryDTO>()

        /** проверяем на ошибки в веденных данных */
        val registryDTO = try {
            RegistryDTO(registryData.login, registryData.password)
        } catch (error: IllegalArgumentException) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to error.message.toString()))

            return@post
        }

        val userService = UserService(DataBase.getCollection())

        runBlocking {
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
    }

    get("/password/recovery") {
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

    post("/api/v1/password/code/set") {
        val setCodeData = call.receive<SetCodeDTO>()

        /** проверяем на ошибки в веденных данных */
        val setCodeDTO = try {
            SetCodeDTO(setCodeData.login)
        } catch (error: IllegalArgumentException) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to error.message.toString()))

            return@post
        }

        val userService = UserService(DataBase.getCollection())

        runBlocking {
            val user = userService.getUser(setCodeDTO.getEmail())
            val id = call.sessions.get<UserID>()?.id

            if (user != null && id != null) {
                val code = randomCode(10)

                UserStack.getUser(id)?.passwordChangeCode = code

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
    }

    get("/password/change") {
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