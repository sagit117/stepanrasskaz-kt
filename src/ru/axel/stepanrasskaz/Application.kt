package ru.axel.stepanrasskaz

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.sessions.*
import ru.axel.stepanrasskaz.domain.user.UserSession

fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Compression) {
        gzip {
            // TODO: потом нормально настроить
//            condition {
//                request.headers[HttpHeaders.Referrer]?.startsWith("https://my.domain/") == true
//            }
            priority = 1.0
        }
        deflate {
            // TODO: потом нормально настроить
//            condition {
//                request.headers[HttpHeaders.Referrer]?.startsWith("https://my.domain/") == true
//            }
            priority = 10.0
            minimumSize(1024) // condition
        }
    }

    install(CORS) { // TODO: потом настроить нормально
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    // https://ktor.io/servers/features/https-redirect.html#testing
//    if (!testing) { // TODO: потом настроить нормально
//        install(HttpsRedirect) {
//            // The port to redirect to. By default 443, the default HTTPS port.
//            sslPort = 443
//            // 301 Moved Permanently, or 302 Found redirect.
//            permanentRedirect = true
//        }
//    }

//    val clientRealm = environment.config.property("jwt.realm").getString()
//    val secret = environment.config.property("jwt.secret").getString()
//    val issuer = environment.config.property("jwt.issuer").getString()
//    val audience = environment.config.property("jwt.audience").getString()

    install(Authentication) {
//        jwt("auth-jwt") {
//            realm = clientRealm
//            verifier(
//                JWT
//                .require(Algorithm.HMAC256(secret))
//                .withAudience(audience)
//                .withIssuer(issuer)
//                .build())
//
//            validate { credential ->
//                if (credential.payload.getClaim("email").asString() != "") {
//                    println(credential.payload.getClaim("email").asString())
//
//                    JWTPrincipal(credential.payload)
//                } else {
//                    null
//                }
//            }
//        }
    }

    /** серриализация объектов запроса */
    install(ContentNegotiation) {
        gson {
        }
    }

    /** логирование запросов */
    install(CallLogging) {
        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            val userAgent = call.request.headers["User-Agent"]
            val uri = call.request.uri

            "Status: $status, HTTP method: $httpMethod, User agent: $userAgent, uri: $uri"
        }
    }

    install(Sessions) {
        cookie<UserSession>("user_session") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 600
            cookie.httpOnly = true
            cookie.extensions["SameSite"] = "lax"
        }
    }

    routing {
        static("static") {
            resources("css")
            resources("image")
            resources("js")
        }
    }
}
