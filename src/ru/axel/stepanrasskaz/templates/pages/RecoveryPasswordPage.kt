package ru.axel.stepanrasskaz.templates.pages

import io.ktor.html.*
import kotlinx.html.*
import ru.axel.stepanrasskaz.templates.components.Button
import ru.axel.stepanrasskaz.templates.data.listcontrols.AuthListControlsData

class RecoveryPasswordPage: BasePage() {
    private val btnList = TemplatePlaceholder<Button>()

    override fun FlowContent.apply() {
        div {
            classes = setOf("auth__wrapper")

            div {
                classes = setOf("auth-form__wrapper", "p2")

                h4 {
                    +"Восстановление пароля"
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

                div {
                    classes = setOf("auth-form__controls", "pt2", "pb2")

                    val btns = AuthListControlsData().getControlsForRecoveryPassword()

                    for (btn in btns) {
                        insert(Button(btn), btnList)
                    }
                }
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