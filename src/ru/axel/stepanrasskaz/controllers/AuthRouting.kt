package ru.axel.stepanrasskaz.controllers

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import kotlinx.coroutines.runBlocking
import ru.axel.stepanrasskaz.connectors.DataBase
import ru.axel.stepanrasskaz.domain.user.UserService
import ru.axel.stepanrasskaz.domain.user.UserSession
import ru.axel.stepanrasskaz.domain.user.auth.dto.AuthDTO
import ru.axel.stepanrasskaz.templates.layouts.EmptyLayout
import ru.axel.stepanrasskaz.templates.pages.LoginPage
import ru.axel.stepanrasskaz.templates.pages.RegistryPage
import ru.axel.stepanrasskaz.utils.ConfigJWT

fun Route.authRoute(configJWT: ConfigJWT) {
    get("/login") {
        call.respondHtmlTemplate(EmptyLayout(LoginPage())) {

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

                    call.sessions.set(token?.let { it -> UserSession(token = it) })
                    call.respond(HttpStatusCode.OK, mapOf("id" to user.id.toString(), "token" to token))
                } else {
                    call.respond(HttpStatusCode.Unauthorized)
                }
            }
        }
    }

    get("/registry") {
        call.respondHtmlTemplate(EmptyLayout(RegistryPage())) {

        }
    }
}