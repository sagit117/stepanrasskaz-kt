package ru.axel.stepanrasskaz.domain.user.helpers

import java.util.regex.Pattern

/**
 * Класс для валидации Email
 */
class Email(val email: String) {

    init {
        val emailPattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
        )

        if (!emailPattern.matcher(email).matches()) {
            throw IllegalArgumentException("Email required")
        }
    }

    override fun toString(): String {
        return email
    }
}