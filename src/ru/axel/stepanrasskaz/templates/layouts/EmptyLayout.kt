package ru.axel.stepanrasskaz.templates.layouts

import io.ktor.html.*
import kotlinx.html.*
import ru.axel.stepanrasskaz.Config
import ru.axel.stepanrasskaz.templates.components.TopPanel
import ru.axel.stepanrasskaz.templates.pages.BasePage

/**
 * Пустой слой, который имеет только верхнюю панель
 */
class EmptyLayout(private val Page: BasePage): Template<HTML> {
    private val topPanel = TemplatePlaceholder<TopPanel>()
    private val page = TemplatePlaceholder<BasePage>()

    override fun HTML.apply() {
        head {
            meta(name = "viewport", content = "width=device-width, initial-scale=1")
            link {
                rel = "shortcut icon"
                href = Config.appFavicon
                type = "image/png"
            }
            styleLink(url = "/static/index.css")
            title(Config.appTitle)
        }
        body {
            insert(TopPanel(), topPanel)

            div {
                classes = setOf("content")

                div {
                    id = "toasts"
                    classes = setOf("toasts__wrapper", "bottom", "right", "p2")
                }

                insert(Page, page)
            }

            script {
                src = "static/toasts.js"
                type = "module"
            }
        }
    }
}