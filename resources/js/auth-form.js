"use strict"

import Api from "./api.js"
import Toast from "./toasts.js"

const inputEmail = document.getElementById("email")
inputEmail?.addEventListener("keypress", (event) => {
    if (event.key === "Enter") authClickHandler()
})

const inputPasswor = document.getElementById("password")
inputPasswor?.addEventListener("keypress", (event) => {
    if (event.key === "Enter") authClickHandler()
})

const btnAuth = document.getElementById("auth")
btnAuth?.addEventListener("click", authClickHandler)

/**
 * бработчик кнопки войти
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
                const loc = document.location
                const path = "/"

                if (loc.pathname !== path) loc.assign(path)
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
                    new Toast("Ошибка", "Произошла внутреняя ошибка сервера", "ERROR", 3).render("toasts");
            }
        })
}

