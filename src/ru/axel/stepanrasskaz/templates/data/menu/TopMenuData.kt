package ru.axel.stepanrasskaz.templates.data.menu

data class TopMenuButton(val id: String, val isDisable: Boolean = false, val title: String)

class TopMenuData {

    fun getButtons(): List<TopMenuButton> {
        return listOf<TopMenuButton>(TopMenuButton("login", false, "Войти"))
    }
}

