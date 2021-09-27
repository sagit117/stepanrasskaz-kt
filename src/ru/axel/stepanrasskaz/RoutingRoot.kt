package ru.axel.stepanrasskaz

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.sessions.*
import ru.axel.stepanrasskaz.controllers.homeRouting
import ru.axel.stepanrasskaz.controllers.loginRoute
import ru.axel.stepanrasskaz.domain.user.UserSession
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

    val configJWT = ConfigJWT(secret, issuer, audience, clientRealm)

    val jwtVerifier = JWT
        .require(Algorithm.HMAC256(secret))
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    routing {
        loginRoute(configJWT)
        homeRouting(jwtVerifier)
    }
}