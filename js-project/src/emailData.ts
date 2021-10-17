'use strict'
import Toast from "./toasts";
import Api from "./api"

export interface IEmailData {
    id: string
    email: string
    isConfirmEmail: boolean
}

export default class Email {
    #emailData: IEmailData
    readonly #rootDiv: HTMLElement | null
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

        /** Если email не подтвержден, показываем предупреждение */
        if (!this.#emailData.isConfirmEmail) {
            this.#emailDiv.classList.add("email-not-confirm")

            const emailConfirmDiv = document.createElement("div")
            emailConfirmDiv.classList.add("email-confirm-wrapper")
            emailConfirmDiv.innerHTML = "<div>Email не подтвержден</div><div style='color: white'>нажмите для подтверждения</div>"

            this.#emailDiv.insertAdjacentElement("beforeend", emailConfirmDiv)

            this.#emailDiv.addEventListener("click", this.#confirmationEmail)
        }

        this.#rootDiv?.insertAdjacentElement("beforeend", this.#emailDiv)
    }

    #confirmationEmail = () => {
        console.error(this.#emailData.id)
        Api.confirmationEmailGetCode(this.#emailData.id)
            .then((res) => {
                if (res.ok) {
                    return new Toast("Код подтверждения отправлен",
                        "На указанный email отправлен код подтверждения",
                        "SUCCESS",
                        3
                    )
                        .render("toasts")
                }

                throw new Error(String(res.status))
            })
            .catch((error) => {
                switch (error) {
                    default:
                        new Toast("Ошибка",
                            "Произошла внутренняя ошибка сервера",
                            "ERROR",
                            3
                        )
                            .render("toasts");
                }
            })
    }
}