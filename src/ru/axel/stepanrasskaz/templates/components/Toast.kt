package ru.axel.stepanrasskaz.templates.components

import io.ktor.html.*
import kotlinx.html.FlowContent
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.id
import ru.axel.stepanrasskaz.templates.data.toasts.ToastData
import kotlin.random.Random

/**
 * Стандартный toast
 */
class Toast(private val toastData: ToastData): Template<FlowContent> {
    private val _id = Random(100).nextInt()

    override fun FlowContent.apply() {
        div {
            id = _id.toString()
            classes = setOf("toast")

            div {
                classes = setOf("toast__header")

                div {
                    classes = setOf("toast__header__title")

                    +toastData.title
                }
            }

            div {
                classes = setOf("toast__body")

                +toastData.message
            }
        }
    }
}