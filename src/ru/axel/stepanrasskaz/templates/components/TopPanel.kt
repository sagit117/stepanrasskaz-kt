package ru.axel.stepanrasskaz.templates.components

import io.ktor.html.*
import kotlinx.html.FlowContent
import kotlinx.html.classes
import kotlinx.html.div

class TopPanel: Template<FlowContent> {

    override fun FlowContent.apply() {
        div {
            classes = setOf("panel-top__wrapper")
            +"test"
        }
    }
}