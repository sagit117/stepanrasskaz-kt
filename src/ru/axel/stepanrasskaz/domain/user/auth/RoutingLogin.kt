package ru.axel.stepanrasskaz.domain.user.auth

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.runBlocking
import ru.axel.stepanrasskaz.connectors.DataBase
import ru.axel.stepanrasskaz.domain.user.UserController
import ru.axel.stepanrasskaz.domain.user.auth.dto.AuthDTO
import ru.axel.stepanrasskaz.utils.sha256
import ru.axel.stepanrasskaz.templates.layouts.EmptyLayout
import ru.axel.stepanrasskaz.templates.pages.LoginPage

fun Route.loginRoute() {
    get("/login") {
        call.respondHtmlTemplate(EmptyLayout(LoginPage())) {

        }
    }

    post("/api/v1/login") {
        val authData = call.receive<AuthDTO>()
        var authDTO: AuthDTO? = null

        try {
            authDTO = AuthDTO(authData.login, authData.password)
        } catch (error: IllegalArgumentException) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to error.message.toString()))
        }

        if (authDTO != null) {
            val userController = UserController(DataBase.getCollection())

            runBlocking {
                val user = userController.findOne(authDTO.getEmail())

                if (user?.password == authDTO.password.sha256()) {
                    call.respond(HttpStatusCode.OK, mapOf("id" to user.id.toString()))
                } else {
                    call.respond(HttpStatusCode.Unauthorized)
                }
            }
        }
    }
}