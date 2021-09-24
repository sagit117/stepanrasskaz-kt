package ru.axel.stepanrasskaz.templates.components

import io.ktor.html.*
import kotlinx.html.*
import ru.axel.stepanrasskaz.Config
import ru.axel.stepanrasskaz.templates.data.menu.TopMenuData

/**
 * Верхняя панель
 */
class TopPanel: Template<FlowContent> {

    override fun FlowContent.apply() {
        div {
            classes = setOf("panel-top__wrapper", "p1")

            div {
                classes = setOf("panel-top__logo")
                +"LOGO"
            }
            div {
                classes = setOf("panel-top__title")
                +Config.appTitle
            }
            div {
                classes = setOf("panel-top__menu")

                val buttons = TopMenuData().getButtons()

                for (btn in buttons) {
                    div {
                        id = btn.id
                        classes = btn.classes
                        +btn.title
                    }
                }
            }
        }
    }
}