package ru.axel.stepanrasskaz

import io.ktor.application.*
import io.ktor.routing.*
import ru.axel.stepanrasskaz.controllers.homeRouting
import ru.axel.stepanrasskaz.controllers.loginRoute
import ru.axel.stepanrasskaz.utils.ConfigJWT

/**
 * Объединяем все модули с маршрутами
 */
@Suppress("unused") // Referenced in application.conf
fun Application.moduleRoutingRoot() {
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val clientRealm = environment.config.property("jwt.realm").getString()

    routing {
        loginRoute(ConfigJWT(secret, issuer, audience, clientRealm))
        homeRouting()
    }
}