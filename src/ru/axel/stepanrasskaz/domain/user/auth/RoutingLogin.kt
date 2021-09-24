package ru.axel.stepanrasskaz.domain.user.auth

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import ru.axel.stepanrasskaz.templates.layouts.EmptyLayout

fun Route.loginRoute() {
    get("/login") {
        call.respondHtmlTemplate(EmptyLayout()) {
            page { }
        }
    }

    post("/login") {

    }
}