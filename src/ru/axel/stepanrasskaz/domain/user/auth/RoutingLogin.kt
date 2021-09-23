package ru.axel.stepanrasskaz.domain.user.auth

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import kotlinx.html.*
import ru.axel.stepanrasskaz.templates.layouts.EmptyLayout

fun Route.loginRoute() {
    fun t() : String {
        return "login"
    }

    get("/login") {
        call.respondHtmlTemplate(EmptyLayout()) {
            page {
                +t()
            }
        }
    }

    post("/login") {

    }
}