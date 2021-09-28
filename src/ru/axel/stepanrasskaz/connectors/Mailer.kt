package ru.axel.stepanrasskaz.connectors

import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.SimpleEmail
import ru.axel.stepanrasskaz.utils.ConfigMailer

class Mailer(configMailer: ConfigMailer) {
    private val email = SimpleEmail()

    init {
        email.hostName = configMailer.hostName
        email.setSmtpPort(configMailer.smtpPort)
        email.setAuthenticator(DefaultAuthenticator(configMailer.user, configMailer.password))
        email.isSSLOnConnect = configMailer.isSSLOnConnect
        email.setFrom(configMailer.from)
    }

    fun send(subject: String, msg: String, emails: Set<String>) {
        email.subject = subject
        email.setMsg(msg)

        for(mail in emails) {
            email.addTo(mail)
        }

        email.send()
    }
}