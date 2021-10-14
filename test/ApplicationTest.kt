package ru.axel

import io.ktor.config.*
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.TestCase.*
import org.junit.Test
import ru.axel.stepanrasskaz.moduleRoutingRoot
import ru.axel.stepanrasskaz.utils.randomCode

class ApplicationTest {
    @Test
    fun testHomeRouting() = withTestApplication() {
        (environment.config as MapApplicationConfig).apply {
            put("jwt.secret", "secret")
            put("jwt.issuer", "issuer")
            put("jwt.audience", "audience")
            put("jwt.realm", "realm")

            put("mailer.hostName", "")
            put("mailer.smtpPort", "587")
            put("mailer.user", "")
            put("mailer.password", "")
            put("mailer.isSSLOnConnect", "true")
            put("mailer.from", "")
            put("mailer.charSet", "")
        }

        application.moduleRoutingRoot(true)
        with(handleRequest(HttpMethod.Get, "/")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun testRandom() {
        assertFalse(randomCode(10) == randomCode(10))
    }
}
