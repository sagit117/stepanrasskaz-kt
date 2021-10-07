package ru.axel.stepanrasskaz.controllers

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.axel.stepanrasskaz.Config
import ru.axel.stepanrasskaz.domain.user.UserRepository
import ru.axel.stepanrasskaz.templates.layouts.EmptyLayout
import ru.axel.stepanrasskaz.templates.pages.ChangePasswordPage
import ru.axel.stepanrasskaz.templates.pages.LoginPage
import ru.axel.stepanrasskaz.templates.pages.RecoveryPasswordPage
import ru.axel.stepanrasskaz.templates.pages.RegistryPage

fun Route.authRoute() {
    get("/login") {
        val connectUserData = try {
            call.attributes[Config.userRepoAttributeKey]
        } catch (error: Exception) {
            null
        }

        if (connectUserData == null) {
            call.respondHtmlTemplate(EmptyLayout(LoginPage())) {

            }
        } else {
            call.respondRedirect("/account/${connectUserData.id}")
        }
    }

    get("/registry") {
        val connectUserData: UserRepository? = try {
            call.attributes[Config.userRepoAttributeKey]
        } catch (error: Exception) {
            null
        }

        if (connectUserData == null) {
            call.respondHtmlTemplate(EmptyLayout(RegistryPage())) {

            }
        } else {
            call.respondRedirect("/account/${connectUserData.id}")
        }
    }

    route("/password") {
        get("/recovery") {
            val connectUserData = try {
                call.attributes[Config.userRepoAttributeKey]
            } catch (error: Exception) {
                null
            }

            if (connectUserData == null) {
                call.respondHtmlTemplate(EmptyLayout(RecoveryPasswordPage())) {

                }
            } else {
                call.respondRedirect("/account/${connectUserData.id}")
            }
        }

        get("/change") {
            val connectUserData = try {
                call.attributes[Config.userRepoAttributeKey]
            } catch (error: Exception) {
                null
            }

            if (connectUserData == null) {
                call.respondHtmlTemplate(EmptyLayout(ChangePasswordPage())) {

                }
            } else {
                call.respondRedirect("/account/${connectUserData.id}")
            }
        }
    }
}