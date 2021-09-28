package ru.axel.stepanrasskaz

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import kotlinx.coroutines.runBlocking
import ru.axel.stepanrasskaz.Config.userRepoAttributeKey
import ru.axel.stepanrasskaz.connectors.DataBase
import ru.axel.stepanrasskaz.controllers.homeRouting
import ru.axel.stepanrasskaz.controllers.authRoute
import ru.axel.stepanrasskaz.domain.user.UserRepository
import ru.axel.stepanrasskaz.domain.user.UserService
import ru.axel.stepanrasskaz.domain.user.UserSession
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
    val charSet = environment.config.property("mailer.charSet").getString()

    val configMailer = ConfigMailer(hostName, smtpPort, user, password, from, isSSLOnConnect, charSet)

    routing {
        /** перехват данных из куки */
        intercept(ApplicationCallPipeline.Features) {
            if (!call.request.uri.startsWith("/static")) {
                val token = call.sessions.get<UserSession>()?.token

                val verifierToken = try {
                    jwtVerifier.verify(token)
                } catch (error: Exception) {
                    null
                }

                val email = verifierToken?.getClaim("email")?.asString()

                val userService = UserService(DataBase.getCollection())

                runBlocking {
                    val userRepository = email?.let { it1 -> userService.getUser(it1) }

                    if (userRepository != null) {
                        call.attributes.put(userRepoAttributeKey, userRepository)
                    }
                }
            }

        }

        authRoute(configJWT, configMailer)
        homeRouting()
    }
}