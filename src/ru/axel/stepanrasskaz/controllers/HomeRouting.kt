package ru.axel.stepanrasskaz.controllers

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import ru.axel.stepanrasskaz.templates.layouts.DefaultLayout
import ru.axel.stepanrasskaz.templates.pages.HomePage

fun Route.homeRouting() {
    get("/") {
        call.respondHtmlTemplate(DefaultLayout(HomePage())) {

        }
    }
}