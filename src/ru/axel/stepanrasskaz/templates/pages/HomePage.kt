package ru.axel.stepanrasskaz.templates.pages

import kotlinx.html.FlowContent
import kotlinx.html.*
import ru.axel.stepanrasskaz.domain.user.UserRepository

class HomePage(val connectUserData: UserRepository?): BasePage() {
    override fun FlowContent.apply() {
        div {
            h1 {
                +"index"
                +connectUserData?.email.toString()
            }
        }
    }
}