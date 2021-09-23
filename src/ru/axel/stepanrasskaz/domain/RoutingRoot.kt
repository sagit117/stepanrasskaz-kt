package ru.axel.stepanrasskaz.domain

import io.ktor.application.*
import io.ktor.routing.*
import ru.axel.stepanrasskaz.domain.user.auth.loginRoute

/**
 * Объединяем все модули с маршрутами
 */
@Suppress("unused") // Referenced in application.conf
fun Application.moduleRoutingRoot() {
    routing {
        loginRoute()
    }
}