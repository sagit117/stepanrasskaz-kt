'use strict'

const account = document.getElementById("account")
account?.addEventListener("click", loginClickHandler)

/**
 * Обработка клика по кнопки войти
 */
function loginClickHandler() {
    const loc = document.location
    const path = "/login"

    if (loc.pathname !== path) loc.assign(path)
}