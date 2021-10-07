package ru.axel.stepanrasskaz.templates.pages

import kotlinx.html.*

class AccountPage: BasePage() {
    override fun FlowContent.apply() {
        div {
            id = "account-page"
        }

        script {
            src = "/static/account.js"
            type = "module"
        }
    }
}