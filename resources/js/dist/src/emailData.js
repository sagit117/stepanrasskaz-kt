'use strict';
export default class Email {
    #emailData;
    #rootDiv;
    #emailDiv = null;
    constructor(emailData, divId) {
        if (!emailData)
            throw new Error("Email data is required!");
        this.#emailData = emailData;
        this.#rootDiv = document.getElementById(divId);
        if (!this.#rootDiv)
            throw new Error("Root div is required");
    }
    render() {
        this.#emailDiv?.remove();
        this.#emailDiv = document.createElement("div");
        this.#emailDiv.classList.add("email");
        this.#emailDiv.innerText = this.#emailData.email;
        if (!this.#emailData.isConfirmEmail) {
            this.#emailDiv.classList.add("email-not-confirm");
            const emailConfirmDiv = document.createElement("div");
            emailConfirmDiv.classList.add("email-confirm-wrapper");
            emailConfirmDiv.innerText = "Email не подтвержден";
            this.#emailDiv.insertAdjacentElement("beforeend", emailConfirmDiv);
        }
        this.#rootDiv?.insertAdjacentElement("beforeend", this.#emailDiv);
    }
}
//# sourceMappingURL=emailData.js.map