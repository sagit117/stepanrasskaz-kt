package ru.axel.stepanrasskaz.templates.data.menu

data class TopMenuButton(val id: String, val title: String, val classes: Set<String>, val iconUrl: String? = null)

/**
 * Класс для конфигураций меню верхней понели
 */
class TopMenuData {

    fun getButtons(): List<TopMenuButton> {
        return listOf(TopMenuButton("account","Войти", setOf("btn", "btn-success"), "/static/account-arrow-right.svg"))
    }
}

