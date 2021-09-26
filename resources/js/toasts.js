'use strict'

export default class Toast {
    #type
    #message
    #title
    #timer
    #cb
    #id = Math.random() * 100

    /**
     * Создаем toast data
     * @param title - заголовок
     * @param message - сообщение
     * @param type - тип ERROR | WARNING | SUCCESS
     * @param timer - время показа в секундах
     * @param cb - callback будет выполнен после закрытия
     */
    constructor(title, message, type, timer, cb) {
        this.#type = type
        this.#message = message
        this.#title = title
        this.#timer = timer
        this.#cb = cb
    }

    render(id) {
        const root = document.getElementById(id)

        if (!root) throw new Error("root div is required")

        /** toast */
        const toastDiv = document.createElement("div")
        toastDiv.setAttribute("id", this.#id)
        toastDiv.classList.add("toast")

        /** header */
        const toastHeader = document.createElement("div")
        toastHeader.classList.add("toast__header")
        toastHeader.innerHTML = `
            <div class="toast-status toast-status__${this.#type} mr1"></div>
            <strong class="mr-auto"> ${this.#title} </strong>
            <img src="/static/close-thick.svg" />
        `
        toastDiv.insertAdjacentElement("afterbegin", toastHeader)

        root.insertAdjacentElement("afterbegin", toastDiv)
    }
}