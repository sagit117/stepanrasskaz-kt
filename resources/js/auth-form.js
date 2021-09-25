"use strict"
import Api from "./api.js"

const inputEmail = document.getElementById("email")
const inputPasswor = document.getElementById("password")

const btnAuth = document.getElementById("auth")
btnAuth?.addEventListener("click", authClickHandler)

function authClickHandler() {
    Api.auth(inputEmail?.value, inputPasswor?.value)
        .then((res) => {
            console.log(res)
        })
}

