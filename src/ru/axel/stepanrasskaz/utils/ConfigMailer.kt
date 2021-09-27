package ru.axel.stepanrasskaz.utils

data class ConfigMailer(
    val hostName: String,
    val smtpPort: Int,
    val user: String,
    val password: String,
    val from: String,
    val isSSLOnConnect: Boolean = true)

