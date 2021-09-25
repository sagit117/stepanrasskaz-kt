"use strict"

const account = document.getElementById("account")
account?.addEventListener("click", loginClickHandler)

const logo = document.getElementById("logo")
logo?.addEventListener("click", goHomeClickHandler)

const title = document.getElementById("title")
title?.addEventListener("click", goHomeClickHandler)

/**
 * Обработка клика по кнопки домой
 */
function goHomeClickHandler() {
    const loc = document.location
    const path = "/"

    if (loc.pathname !== path) loc.assign(path)
}

/**
 * Обработка клика по кнопки войти
 */
function loginClickHandler() {
    const loc = document.location
    const path = "/login"

    if (loc.pathname !== path) loc.assign(path)
}