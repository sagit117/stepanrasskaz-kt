package ru.axel.stepanrasskaz.templates.pages

import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.h1

class RecoveryPasswordPage: BasePage() {
    override fun FlowContent.apply() {
        div {
            h1 {
                +"recovery "
            }
        }
    }
}