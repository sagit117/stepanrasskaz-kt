package ru.axel.stepanrasskaz.domain.user.auth.dto

import ru.axel.stepanrasskaz.domain.user.helpers.Email

/**
 * DTO данные для регистрации по логину и паролю
 */
data class RegistryDTO(val login: String, val password: String) {
    private val email: Email = Email(login)

    fun getEmail(): String {
        return email.toString()
    }

    override fun toString(): String {
        return "email=${ email.toString() } password=$password"
    }
}