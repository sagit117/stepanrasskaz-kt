package ru.axel.stepanrasskaz.domain.user.auth

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.axel.stepanrasskaz.connectors.DataBase
import ru.axel.stepanrasskaz.domain.user.UserController
import ru.axel.stepanrasskaz.domain.user.auth.dto.AuthDTO
import ru.axel.stepanrasskaz.templates.layouts.EmptyLayout
import ru.axel.stepanrasskaz.templates.pages.LoginPage
import java.security.MessageDigest

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
            val userController = UserController(DataBase.getDB())

            launch {
                val user = userController.findOne(authDTO.getEmail())

                fun hash(input: String): String {
                    val bytes = input.toByteArray()
                    val md = MessageDigest.getInstance("SHA-256")
                    val digest = md.digest(bytes)
                    return digest.fold("") { str, it -> str + "%02x".format(it) }
                }

                fun String.sha256(): String {
                    return hash(this)
                }

                println("123".sha256())
                println("123".sha256())

                call.respond(mapOf("user" to user))
            }
        }
    }
}