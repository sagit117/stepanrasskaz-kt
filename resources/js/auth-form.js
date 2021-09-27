"use strict"

import Api from "./api.js"
import Toast from "./toasts.js"
import { goRoute } from "./utils.js"

const btnAuth = document.getElementById("auth")
btnAuth?.addEventListener("click", authClickHandler)

const btnGoRegistry = document.getElementById("go-registry")
btnGoRegistry?.addEventListener("click", () => {
    goRoute("/registry")
})

const btnGoAuth = document.getElementById("go-auth")
btnGoAuth?.addEventListener("click", () => {
    goRoute("/login")
})

const inputEmail = document.getElementById("email")
inputEmail?.addEventListener("keypress", (event) => {
    if (event.key === "Enter" && btnAuth) authClickHandler()
})

const inputPassword = document.getElementById("password")
inputPassword?.addEventListener("keypress", (event) => {
    if (event.key === "Enter" && btnAuth) authClickHandler()
})

/**
 * обработчик кнопки войти
 */
function authClickHandler() {
    Api.auth(inputEmail?.value, inputPasswor?.value)
        .then((res) => {
            if (res.ok) return res.json()

            throw new Error(res.status)
        })
        .then((res) => {
            localStorage.setItem("token", res.token)

            new Toast("Учетные данные подтверждены", "Вы вошли в систему", "SUCCESS", 3, () => {
                goRoute("/")
            })
                .render("toasts")
        })
        .catch((error) => {
            switch (+error.message) {
                case 400:
                case 401:
                    new Toast("Ошибка", "Не верный логин или пароль", "ERROR", 3).render("toasts");
                    break;

                default:
                    new Toast("Ошибка", "Произошла внутренняя ошибка сервера", "ERROR", 3).render("toasts");
            }
        })
}

