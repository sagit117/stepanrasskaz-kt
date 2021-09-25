package ru.axel.stepanrasskaz

import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.routing.*
import org.litote.kmongo.KMongo
import ru.axel.stepanrasskaz.templates.layouts.EmptyLayout
import ru.axel.stepanrasskaz.templates.pages.HomePage
import java.util.*

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

    install(Authentication) {
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

    routing {
        get("/") {
            call.respondHtmlTemplate(EmptyLayout(HomePage())) {

            }
        }

        static("static") {
            resources("css")
            resources("image")
            resources("js")
        }
    }


}
