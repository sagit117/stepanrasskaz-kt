package ru.axel.stepanrasskaz.domain.user.auth

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import ru.axel.stepanrasskaz.templates.layouts.EmptyLayout
import ru.axel.stepanrasskaz.templates.pages.LoginPage

fun Route.loginRoute() {
    get("/login") {
        call.respondHtmlTemplate(EmptyLayout(LoginPage())) {

        }
    }

    post("/api/v1/login") {

    }
}