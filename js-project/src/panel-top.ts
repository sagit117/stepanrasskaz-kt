"use strict"

import { goRoute } from "./utils.js"

const account = document.getElementById("account")
account?.addEventListener("click", () => goRoute("/login"))

const logo = document.getElementById("logo")
logo?.addEventListener("click", goHomeClickHandler)

const title = document.getElementById("title")
title?.addEventListener("click", goHomeClickHandler)

/**
 * Обработка клика по кнопки домой
 */
function goHomeClickHandler() {
    goRoute("/")
}