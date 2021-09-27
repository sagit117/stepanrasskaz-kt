package ru.axel.stepanrasskaz.domain.user

import io.ktor.auth.*

data class UserSession(val token: String): Principal