package ru.axel.stepanrasskaz

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.routing.*
import ru.axel.stepanrasskaz.controllers.homeRouting
import ru.axel.stepanrasskaz.controllers.authRoute
import ru.axel.stepanrasskaz.utils.ConfigJWT
import ru.axel.stepanrasskaz.utils.ConfigMailer

/**
 * Объединяем все модули с маршрутами
 */
@Suppress("unused") // Referenced in application.conf
fun Application.moduleRoutingRoot() {
    /** настройки jwt */
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

    /** настройки mailer */
    val hostName = environment.config.property("mailer.hostName").getString()
    val smtpPort = environment.config.property("mailer.smtpPort").getString().toInt()
    val user = environment.config.property("mailer.user").getString()
    val password = environment.config.property("mailer.password").getString()
    val isSSLOnConnect = environment.config.property("mailer.isSSLOnConnect").getString().toBoolean()
    val from = environment.config.property("mailer.from").getString()

    val configMailer = ConfigMailer(hostName, smtpPort, user, password, from, isSSLOnConnect)

    routing {
        authRoute(configJWT, configMailer)
        homeRouting(jwtVerifier)
    }
}