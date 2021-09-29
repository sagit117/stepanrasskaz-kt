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
import ru.axel.stepanrasskaz.domain.role.RoleRepository
import ru.axel.stepanrasskaz.domain.user.UserRepository
import ru.axel.stepanrasskaz.domain.user.UserService
import ru.axel.stepanrasskaz.domain.user.UserSession
import ru.axel.stepanrasskaz.domain.user.auth.dto.AuthDTO
import ru.axel.stepanrasskaz.domain.user.auth.dto.RegistryDTO
import ru.axel.stepanrasskaz.templates.entryMail
import ru.axel.stepanrasskaz.templates.layouts.EmptyLayout
import ru.axel.stepanrasskaz.templates.pages.LoginPage
import ru.axel.stepanrasskaz.templates.pages.RecoveryPasswordPage
import ru.axel.stepanrasskaz.templates.pages.RegistryPage
import ru.axel.stepanrasskaz.templates.registryMail
import ru.axel.stepanrasskaz.utils.ConfigJWT
import ru.axel.stepanrasskaz.utils.ConfigMailer

fun Route.authRoute(configJWT: ConfigJWT, configMailer: ConfigMailer) {
    get("/login") {
        val connectUserData = try {
            call.attributes[Config.userRepoAttributeKey]
        } catch (error: Exception) {
            null
        }

        if (connectUserData == null) {
            call.respondHtmlTemplate(EmptyLayout(LoginPage())) {
                user = connectUserData
            }
        } else {
            call.respondRedirect("/account/${connectUserData.id}")
        }
    }

    post("/api/v1/login") {
        val authData = call.receive<AuthDTO>()
        var authDTO: AuthDTO? = null

        /** проверяем на ошибки в веденных данных */
        try {
            authDTO = AuthDTO(authData.login, authData.password)
        } catch (error: IllegalArgumentException) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to error.message.toString()))
        }

        if (authDTO != null) {
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
    }

    get("/registry") {
        val connectUserData: UserRepository? = try {
            call.attributes[Config.userRepoAttributeKey]
        } catch (error: Exception) {
            null
        }

        if (connectUserData == null) {
            call.respondHtmlTemplate(EmptyLayout(RegistryPage())) {
                user = connectUserData
            }
        } else {
            call.respondRedirect("/account/${connectUserData.id}")
        }
    }

    post("api/v1/registry") {
        val registryData = call.receive<RegistryDTO>()
        var registryDTO: RegistryDTO? = null

        /** проверяем на ошибки в веденных данных */
        try {
            registryDTO = RegistryDTO(registryData.login, registryData.password)
        } catch (error: IllegalArgumentException) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to error.message.toString()))
        }

        if (registryDTO != null) {
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
    }

    get("/recovery/password") {
        val connectUserData = try {
            call.attributes[Config.userRepoAttributeKey]
        } catch (error: Exception) {
            null
        }

        if (connectUserData == null) {
            call.respondHtmlTemplate(EmptyLayout(RecoveryPasswordPage())) {
                user = connectUserData
            }
        } else {
            call.respondRedirect("/account/${connectUserData.id}")
        }
    }
}