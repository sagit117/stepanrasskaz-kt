package ru.axel.stepanrasskaz.domain.user.auth

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import kotlinx.html.*

fun Route.loginRoute() {
    get("/login") {
        call.respondHtmlTemplate(LayoutTemplate()) {
            header {
                +"login!!!!"
            }
        }
    }

    post("/login") {

    }
}

class LayoutTemplate: Template<HTML> {
    val header = Placeholder<FlowContent>()

    override fun HTML.apply() {
        body {
            h1 {
                insert(header)
            }
        }
    }
}