package ru.axel.stepanrasskaz.templates.layouts

import io.ktor.html.*
import kotlinx.html.*
import ru.axel.stepanrasskaz.templates.components.TopPanel

/**
 * Пустой слой, который имеет только верхнюю панель
 */
class EmptyLayout: Template<HTML> {
    val page = Placeholder<FlowContent>()
    val topPanel = TemplatePlaceholder<TopPanel>()

    override fun HTML.apply() {
        head {
            styleLink(url = "/static/index.css")
        }
        body {
            insert(TopPanel(), topPanel)
            insert(page)
        }
    }
}