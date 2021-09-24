package ru.axel.stepanrasskaz.templates.data.menu

import ru.axel.stepanrasskaz.templates.data.buttons.ButtonData

/**
 * Класс для конфигураций меню верхней понели
 */
class TopMenuData {
    fun getButtons(): List<ButtonData> {
        return listOf(ButtonData("account","Войти", setOf("btn", "btn-success"), "/static/account-arrow-right.svg"))
    }
}

