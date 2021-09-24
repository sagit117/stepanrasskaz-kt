package ru.axel.stepanrasskaz.templates.pages

import kotlinx.html.FlowContent
import kotlinx.html.*

class HomePage(): BasePage() {
    override fun FlowContent.apply() {
        div {
            h1 {
                +"index"
            }
        }
    }
}