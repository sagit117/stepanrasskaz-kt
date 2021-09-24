package ru.axel.stepanrasskaz.templates.pages

import kotlinx.html.FlowContent
import kotlinx.html.*

class LoginPage: BasePage() {

    override fun FlowContent.apply() {
        div {
            classes = setOf("login__wrapper")

            div {
                classes = setOf("login-form__wrapper p2")

                h4 {
                    +"Вход в систему"
                }

                input {
                    type = InputType.email
                    name = "email"
                    id = "email"
                    placeholder = "Введите email"
                }

                input {
                    type = InputType.password
                    name = "password"
                    id = "password"
                    placeholder = "Введите пароль"
                }
            }
        }
    }
}