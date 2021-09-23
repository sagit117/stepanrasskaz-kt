package ru.axel.stepanrasskaz.templates.layouts

import io.ktor.html.*
import kotlinx.html.*

/**
 * Пустой слой, который имеет только верхнюю панель
 */
class EmptyLayout: Template<HTML> {
    val page = Placeholder<FlowContent>()

    override fun HTML.apply() {
        head {
            styleLink(url = "/static/index.css")
        }
        body {
            insert(page)
            h1 {
                +"test"
            }
        }

    }
}