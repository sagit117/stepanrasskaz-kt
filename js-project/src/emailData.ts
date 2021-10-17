'use strict'

export interface IEmailData {
    email: string
    isConfirmEmail: boolean
}

export default class Email {
    #emailData: IEmailData
    #rootDiv: HTMLElement | null
    #emailDiv: HTMLElement | null = null

    constructor(emailData: IEmailData, divId: string) {
        if (!emailData) throw new Error("Email data is required!")

        this.#emailData = emailData
        this.#rootDiv = document.getElementById(divId)

        if (!this.#rootDiv) throw new Error("Root div is required")
    }

    public render() {
        this.#emailDiv?.remove()

        this.#emailDiv = document.createElement("div")

        this.#emailDiv.classList.add("email")
        this.#emailDiv.innerText = this.#emailData.email

        if (!this.#emailData.isConfirmEmail) {
            this.#emailDiv.classList.add("email-not-confirm")

            const emailConfirmDiv = document.createElement("div")
            emailConfirmDiv.classList.add("email-confirm-wrapper")
            emailConfirmDiv.innerHTML = "<div>Email не подтвержден</div><div style='color: white'>нажмите для подтверждения</div>"

            this.#emailDiv.insertAdjacentElement("beforeend", emailConfirmDiv)
        }

        this.#rootDiv?.insertAdjacentElement("beforeend", this.#emailDiv)
    }
}