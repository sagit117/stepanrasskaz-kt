'use strict'

const account = document.getElementById("account")
account?.addEventListener("click", loginClickHandler)

function loginClickHandler() {
    document.location.assign("/login")
}