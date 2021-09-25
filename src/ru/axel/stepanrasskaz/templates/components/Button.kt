package ru.axel.stepanrasskaz.templates.components

import io.ktor.html.*
import kotlinx.html.*
import ru.axel.stepanrasskaz.templates.data.buttons.ButtonData

/**
 * Стандартная кнопка
 */
class Button(private val btn: ButtonData): Template<FlowContent> {
    override fun FlowContent.apply() {
        div {
            id = btn.id
            classes = btn.classes
            tabIndex = "0"

            if (btn.iconUrl !== null) {
                div {
                    classes = setOf("btn__img")

                    img {
                        src = btn.iconUrl
                        alt = btn.id
                    }
                }
            }

            div {
                +btn.title
            }
        }
    }
}