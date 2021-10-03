package ru.axel.stepanrasskaz.domain.user.session

import io.ktor.auth.*

/** класс хранилище для cookie */
data class UserSession(val token: String): Principal