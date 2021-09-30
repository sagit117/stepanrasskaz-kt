package ru.axel.stepanrasskaz.templates.pages

import io.ktor.html.*
import kotlinx.html.FlowContent
import kotlinx.html.*
import ru.axel.stepanrasskaz.templates.components.Button
import ru.axel.stepanrasskaz.templates.data.listcontrols.AuthListControlsData

/**
 * Страница логина
 */
class LoginPage: BasePage() {
    private val btnList = TemplatePlaceholder<Button>()

    override fun FlowContent.apply() {
        div {
            classes = setOf("auth__wrapper")

            div {
                id = "auth-form"
                classes = setOf("auth-form__wrapper", "p2")

                div {
                    id = "spinner-wrapper"
                    classes = setOf("spinner__wrapper")
                }

                h4 {
                    +"Вход в систему"
                }

                label {
                    htmlFor = "email"
                    +"Email:"
                }
                input {
                    classes = setOf("mb2")
                    type = InputType.email
                    name = "email"
                    id = "email"
                    placeholder = "Введите email"
                }

                label {
                    htmlFor = "password"
                    +"Пароль:"
                }
                input {
                    classes = setOf("mb2")
                    type = InputType.password
                    name = "password"
                    id = "password"
                    placeholder = "Введите пароль"
                }

                div {
                    classes = setOf("auth-form__controls", "pt2", "pb2")

                    val btns = AuthListControlsData().getControlsForLogin()

                    for (btn in btns) {
                        insert(Button(btn), btnList)
                    }
                }
            }

            script {
                src = "/static/spinner.js"
                type = "module"
            }

            script {
                src = "/static/api.js"
                type = "module"
            }

            script {
                src = "/static/auth-form.js"
                type = "module"
            }
        }
    }
}