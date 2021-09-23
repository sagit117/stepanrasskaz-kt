package ru.axel.stepanrasskaz.domain.user.auth

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.loginRoute() {
    get("/login") {
        call.respondText("login", contentType = ContentType.Text.Plain)
    }
}