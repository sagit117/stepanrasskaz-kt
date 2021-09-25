package ru.axel.stepanrasskaz.templates.pages

import io.ktor.html.*
import kotlinx.html.FlowContent

/**
 * Базовый класс страницы для наследования
 */
open class BasePage: Template<FlowContent> {
    override fun FlowContent.apply() {

    }
}