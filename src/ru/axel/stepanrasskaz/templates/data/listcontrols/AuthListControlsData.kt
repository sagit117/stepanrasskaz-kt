package ru.axel.stepanrasskaz.templates.data.listcontrols

import ru.axel.stepanrasskaz.templates.data.buttons.ButtonData

/**
 * Контролы для форм аутентификации
 */
class AuthListControlsData {
    fun getControlsForLogin(): List<ButtonData> {
        return listOf(
            ButtonData("go-forgot-password", "Забыли пароль?", setOf("btn", "btn-outline", "mr1")),
            ButtonData("go-registry", "Регистрация", setOf("btn", "btn-primary", "mr1")),
            ButtonData("auth", "Войти", setOf("btn", "btn-success"))
        )
    }

    fun getControlsForRegistry(): List<ButtonData> {
        return listOf(
            ButtonData("go-auth", "Войти", setOf("btn", "btn-primary", "mr1", "ml-auto")),
            ButtonData("registry", "Регистрация", setOf("btn", "btn-success"))
        )
    }

    fun getControlsForRecoveryPassword(): List<ButtonData> {
        return listOf(
            ButtonData("go-auth", "Войти", setOf("btn", "btn-primary", "mr1", "ml-auto")),
            ButtonData("forgot-password", "Запросить пароль", setOf("btn", "btn-success"))
        )
    }
}