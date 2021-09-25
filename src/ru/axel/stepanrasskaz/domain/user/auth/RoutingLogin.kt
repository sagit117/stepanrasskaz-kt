package ru.axel.stepanrasskaz.domain.user.auth

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.axel.stepanrasskaz.domain.user.auth.dto.AuthDTO
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
            println(authDTO)

            call.respond(mapOf("hello" to "world"))
        }
    }
}