'use strict';
export default class Toast {
    #type;
    #message;
    #title;
    #timer;
    #cb;
    #id = "toast-" + Math.random() * 100;
    /**
     * Создаем toast data
     * @param title - заголовок
     * @param message - сообщение
     * @param type - тип ERROR | WARNING | SUCCESS
     * @param timer - время показа в секундах
     * @param cb - callback будет выполнен после закрытия
     */
    constructor(title, message, type, timer, cb) {
        this.#type = type;
        this.#message = message;
        this.#title = title;
        this.#timer = +timer;
        this.#cb = (typeof cb === "function") ? cb : null;
        setTimeout(() => {
            this.destroy();
        }, (this.#timer * 1000) || 3000);
    }
    destroy() {
        const elem = document.getElementById(this.#id);
        elem?.remove();
        this.#cb?.();
    }
    render(id) {
        const root = document.getElementById(id);
        if (!root)
            throw new Error("root div is required");
        /** toast */
        const toastDiv = document.createElement("div");
        toastDiv.setAttribute("id", String(this.#id));
        toastDiv.classList.add("toast");
        toastDiv.classList.add("mx1");
        /** header */
        const toastHeader = document.createElement("div");
        toastHeader.classList.add("toast__header");
        toastHeader.innerHTML = `
            <div class="toast-status toast-status__${this.#type} mr1"></div>
            <strong class="mr-auto"> ${this.#title} </strong>
        `;
        /** close */
        const btnCloce = document.createElement("img");
        btnCloce.setAttribute("src", "/static/close-thick.svg");
        btnCloce.addEventListener("click", () => this.destroy());
        /** body */
        const toastBody = document.createElement("div");
        toastBody.classList.add("toast__body");
        toastBody.innerText = this.#message;
        /** inserts */
        toastHeader.insertAdjacentElement("beforeend", btnCloce);
        toastDiv.insertAdjacentElement("afterbegin", toastHeader);
        toastDiv.insertAdjacentElement("beforeend", toastBody);
        root.insertAdjacentElement("afterbegin", toastDiv);
    }
}
//# sourceMappingURL=toasts.js.map