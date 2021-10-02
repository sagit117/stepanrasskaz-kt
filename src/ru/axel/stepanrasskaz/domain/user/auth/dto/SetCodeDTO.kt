package ru.axel.stepanrasskaz.domain.user.auth.dto

import ru.axel.stepanrasskaz.domain.user.helpers.Email

data class SetCodeDTO(val login: String) {
    private val email: Email = Email(login)

    fun getEmail(): String {
        return email.toString()
    }

    override fun toString(): String {
        return "email=$email"
    }
}