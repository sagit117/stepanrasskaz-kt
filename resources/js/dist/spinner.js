'use strict';
export default class Spinner {
    #id = "spinner-" + Math.random() * 100;
    #idDivBlocking = null;
    #divOverlay = null;
    #isRender = false;
    /**
     * Класс spinner
     * @param idDivBlocking - id элемента, который необходимо покрыть оверлеем
     */
    constructor(idDivBlocking) {
        if (idDivBlocking)
            this.#idDivBlocking = document.getElementById(idDivBlocking);
    }
    render(id) {
        const root = document.getElementById(id);
        if (!root)
            throw new Error("root div is required");
        /** spinner */
        const spinnerDiv = document.createElement("div");
        spinnerDiv.setAttribute("id", String(this.#id));
        spinnerDiv.classList.add("spinner");
        spinnerDiv.innerText = "Loading...";
        root.insertAdjacentElement("afterbegin", spinnerDiv);
        /** div blocking */
        if (this.#idDivBlocking) {
            this.#divOverlay = document.createElement("div");
            this.#divOverlay.classList.add("overlay");
            this.#idDivBlocking.insertAdjacentElement("afterbegin", this.#divOverlay);
            this.#isRender = true;
        }
    }
    destroy() {
        const elem = document.getElementById(this.#id);
        elem?.remove();
        if (this.#divOverlay) {
            this.#divOverlay.remove();
            this.#isRender = false;
        }
    }
    isRender() {
        return this.#isRender;
    }
}
