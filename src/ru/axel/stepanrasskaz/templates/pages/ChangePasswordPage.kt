package ru.axel.stepanrasskaz.templates.pages

import io.ktor.html.*
import kotlinx.html.*
import ru.axel.stepanrasskaz.templates.components.Button
import ru.axel.stepanrasskaz.templates.data.listcontrols.AuthListControlsData

class ChangePasswordPage: BasePage() {
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
                    +"Восстановление пароля"
                }

                label {
                    htmlFor = "code"
                    +"Код из email:"
                }
                input {
                    classes = setOf("mb2")
                    type = InputType.text
                    name = "code"
                    id = "code"
                    placeholder = "Введите код из email"
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

                label {
                    htmlFor = "password-confirm"
                    +"Пароль еще раз:"
                }
                input {
                    classes = setOf("mb2")
                    type = InputType.password
                    name = "password-confirm"
                    id = "password-confirm"
                    placeholder = "Введите пароль еще раз"
                }

                div {
                    classes = setOf("auth-form__controls", "pt2", "pb2")

                    val btns = AuthListControlsData().getControlsForChangePassword()

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