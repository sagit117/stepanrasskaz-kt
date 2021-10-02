package ru.axel.stepanrasskaz.domain.user.auth.dto

import ru.axel.stepanrasskaz.domain.user.helpers.Email

/**
 * DTO данные для регистрации по логину и паролю
 */
data class RegistryDTO(val login: String, val password: String) {
    private val email: String = Email(login).toString()

    fun getEmail(): String {
        return email
    }

    override fun toString(): String {
        return "email=$email password=$password"
    }
}