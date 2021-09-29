package ru.axel.stepanrasskaz.templates.data.menu

import ru.axel.stepanrasskaz.templates.data.buttons.ButtonData

/**
 * Класс для конфигураций меню верхней понели
 */
class TopMenuData {
    fun getButtons(): List<ButtonData> {
        return listOf(ButtonData("account","Войти", setOf("btn", "btn-primary"), "/static/account-arrow-right.svg"))
    }

    fun getAdminButtons(): List<ButtonData> {
        return listOf(ButtonData("account","Кабинет", setOf("btn", "btn-primary"), "/static/account-arrow-right.svg"))
    }

    fun getClientButtons(): List<ButtonData> {
        return listOf(ButtonData("account","Кабинет", setOf("btn", "btn-primary"), "/static/account-arrow-right.svg"))
    }
}

