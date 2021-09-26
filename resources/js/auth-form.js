"use strict"

import Api from "./api.js"
import Toast from "./toasts.js"

const inputEmail = document.getElementById("email")
const inputPasswor = document.getElementById("password")

const btnAuth = document.getElementById("auth")
btnAuth?.addEventListener("click", authClickHandler)

function authClickHandler() {
    Api.auth(inputEmail?.value, inputPasswor?.value)
        .then((res) => res.json())
        .then((res) => {
            localStorage.setItem("token", res.token)

            const toast = new Toast("Учетные данные подтверждены", "Вы вошли в систему", "SUCCESS", 3, () => {
                const loc = document.location
                const path = "/"

                if (loc.pathname !== path) loc.assign(path)
            })

            toast.render("toasts")
        })
        .catch((error) => {
            console.error(error?.message)
        })
}

