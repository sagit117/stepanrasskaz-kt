package ru.axel.stepanrasskaz

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import io.ktor.features.*
import io.ktor.auth.*
import io.ktor.gson.*

fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = true) {
    install(Compression) {
        gzip {
            // @TODO: потом нормально настроить
//            condition {
//                request.headers[HttpHeaders.Referrer]?.startsWith("https://my.domain/") == true
//            }
            priority = 1.0
        }
        deflate {
            // @TODO: потом нормально настроить
//            condition {
//                request.headers[HttpHeaders.Referrer]?.startsWith("https://my.domain/") == true
//            }
            priority = 10.0
            minimumSize(1024) // condition
        }
    }

    install(CORS) { //@TODO: потом настроить нормально
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
//        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
//        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    // https://ktor.io/servers/features/https-redirect.html#testing
    if (!testing) {
        install(HttpsRedirect) {
            // The port to redirect to. By default 443, the default HTTPS port.
            sslPort = 443
            // 301 Moved Permanently, or 302 Found redirect.
            permanentRedirect = true
        }
    }

    install(Authentication) {
    }

    install(ContentNegotiation) {
        gson {
        }
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/html-dsl") {
            call.respondHtml {
                body {
                    h1 { +"HTML" }
                    ul {
                        for (n in 1..10) {
                            li { +"$n" }
                        }
                    }
                }
            }
        }

        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}

