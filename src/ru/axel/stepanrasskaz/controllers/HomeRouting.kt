package ru.axel.stepanrasskaz.controllers

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import io.ktor.sessions.*
import ru.axel.stepanrasskaz.domain.user.UserSession
import ru.axel.stepanrasskaz.templates.layouts.DefaultLayout
import ru.axel.stepanrasskaz.templates.pages.HomePage

fun Route.homeRouting() {
    get("/") {
        println(call.sessions.get<UserSession>().toString())

        call.respondHtmlTemplate(DefaultLayout(HomePage())) {

        }
    }
}