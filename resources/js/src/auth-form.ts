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

const btnRegistry = document.getElementById("registry")
btnRegistry?.addEventListener("click", registryClickHandler)

const inputEmail: HTMLInputElement | null = document.getElementById("email") as HTMLInputElement
inputEmail?.addEventListener("keypress", (event) => {
    if (event.key === "Enter" && btnAuth) authClickHandler()
    if (event.key === "Enter" && btnRegistry) registryClickHandler()
})

const inputPassword: HTMLInputElement | null = document.getElementById("password") as HTMLInputElement
inputPassword?.addEventListener("keypress", (event) => {
    if (event.key === "Enter" && btnAuth) authClickHandler()
    if (event.key === "Enter" && btnRegistry) registryClickHandler()
})

const inputPasswordConfirm: HTMLInputElement | null = document.getElementById("password-confirm") as HTMLInputElement
inputPasswordConfirm?.addEventListener("keypress", (event) => {
    if (event.key === "Enter" && btnRegistry) registryClickHandler()
})

/**
 * обработчик кнопки войти
 */
function authClickHandler() {
    Api.auth(inputEmail?.value, inputPassword?.value)
        .then((res) => {
            if (res.ok) return res.json()

            throw new Error(String(res.status))
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

/**
 * обработчик кнопки регистрация
 */
function registryClickHandler() {
    Api.registry({ login: inputEmail?.value, password: inputPassword?.value })
        .then((res) => {
            if (res.ok) return res.json()

            throw new Error(String(res.status))
        })
        .then((_res: object) => {
            new Toast("Учетные данные подтверждены", "Вы зарегистрированы в системе", "SUCCESS", 3, () => {
                goRoute("/login")
            })
                .render("toasts")
        })
        .catch((error) => {
            switch (+error.message) {
                case 400:
                case 401:
                    new Toast("Ошибка", "Возможно email уже зарегистрирован", "ERROR", 3).render("toasts");
                    break;

                default:
                    new Toast("Ошибка", "Произошла внутренняя ошибка сервера", "ERROR", 3).render("toasts");
            }
        })
}


