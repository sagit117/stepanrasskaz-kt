package ru.axel.stepanrasskaz.connectors

import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.SimpleEmail
import ru.axel.stepanrasskaz.utils.ConfigMailer

class Mailer(configMailer: ConfigMailer) {
    val email = SimpleEmail()

    init {
        email.hostName = configMailer.hostName
        email.setSmtpPort(configMailer.smtpPort)
        email.setAuthenticator(DefaultAuthenticator(configMailer.user, configMailer.password))
        email.isSSLOnConnect = configMailer.isSSLOnConnect
        email.setFrom(configMailer.from)
    }

    fun send() {
        email.subject = "test"
        email.setMsg("message-content")
        email.addTo("sagit117@gmail.com")
        email.send()
    }
}