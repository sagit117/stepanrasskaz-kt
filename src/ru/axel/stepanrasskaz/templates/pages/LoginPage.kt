package ru.axel.stepanrasskaz.templates.pages

import io.ktor.html.*
import kotlinx.html.FlowContent
import kotlinx.html.*

class LoginPage: BasePage() {

    override fun FlowContent.apply() {
        div {
            h1 {
                +"login"
            }
        }
    }
}