package ru.axel.stepanrasskaz.connectors

import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.SimpleEmail

class Mailer {
    val email = SimpleEmail()

    init {
        email.hostName = "smtp.googlemail.com"
        email.setSmtpPort(587)
        email.setAuthenticator(DefaultAuthenticator("reports.sti.mailer@gmail.com", "neosjhdbhJHJHf34f"))
        email.isSSLOnConnect = true
        email.setFrom("reports.sti.mailer@gmail.com")
        email.subject = "test"
        email.setMsg("message-content")
        email.addTo("sagit117@gmail.com")
        email.send()
    }
}