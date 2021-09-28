package ru.axel.stepanrasskaz.controllers

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import ru.axel.stepanrasskaz.Config.userRepoAttributeKey
import ru.axel.stepanrasskaz.domain.role.RoleRepository
import ru.axel.stepanrasskaz.templates.layouts.DefaultLayout
import ru.axel.stepanrasskaz.templates.pages.HomePage

fun Route.homeRouting() {
    get("/") {
        val connectUserData = call.attributes[userRepoAttributeKey]

        call.respondHtmlTemplate(DefaultLayout(HomePage())) {
            isAdmin = connectUserData.role == RoleRepository.ADMIN
        }
    }
}