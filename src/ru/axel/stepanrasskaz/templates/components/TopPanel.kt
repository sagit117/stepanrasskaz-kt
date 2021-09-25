package ru.axel.stepanrasskaz.templates.components

import io.ktor.html.*
import kotlinx.html.*
import ru.axel.stepanrasskaz.Config
import ru.axel.stepanrasskaz.templates.data.menu.TopMenuData

/**
 * Верхняя панель
 */
class TopPanel: Template<FlowContent> {
    private val btnList = TemplatePlaceholder<Button>()

    override fun FlowContent.apply() {
        div {
            classes = setOf("panel-top__wrapper", "p1")

            div {
                id = "logo"
                classes = setOf("panel-top__logo")

                img {
                    src = Config.logoUrl
                    alt = Config.appTitle
                }
            }
            div {
                id = "title"
                classes = setOf("panel-top__title")
                +Config.appTitle
            }
            div {
                classes = setOf("panel-top__menu")

                val btns = TopMenuData().getButtons()
                for (btn in btns) {
                    insert(Button(btn), btnList)
                }
            }
        }

        script { src = "/static/panel-top.js" }
    }
}