package ru.axel.stepanrasskaz.controllers

import com.auth0.jwt.*
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import io.ktor.sessions.*
import ru.axel.stepanrasskaz.domain.user.UserSession
import ru.axel.stepanrasskaz.templates.layouts.DefaultLayout
import ru.axel.stepanrasskaz.templates.pages.HomePage

fun Route.homeRouting(jwtVerifier: JWTVerifier) {

    get("/") {

        val token = call.sessions.get<UserSession>()?.token

        val email = try {
            jwtVerifier
                .verify(token)
                .getClaim("email")
                .asString()
        } catch (error: Exception) {
            null
        }

//        println(email)

        call.respondHtmlTemplate(DefaultLayout(HomePage())) {

        }
    }
}