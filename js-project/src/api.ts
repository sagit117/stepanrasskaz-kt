class Api {
    #url = "/api/v1"

    auth(login?: string, password?: string) {
        return fetch(this.#url + "/login", {
            method: "post",
            headers: {
                'Content-Type': 'application/json'
                // 'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: JSON.stringify({ login, password }),
            credentials: "include",
        })
            .then((res) => {
                return Promise.resolve(res)
            })
            .catch((error) => {
                console.error(error)

                return Promise.reject(error)
            })
    }

    registry(body: object) {
        return fetch(this.#url + "/registry", {
            method: "post",
            headers: {
                'Content-Type': 'application/json'
                // 'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: JSON.stringify(body),
            credentials: "include",
        })
            .then((res) => {
                return Promise.resolve(res)
            })
            .catch((error) => {
                console.error(error)

                return Promise.reject(error)
            })
    }

    /**
     * Запросить код для восстановления пароля
     * @param body
     */
    passwordCodeSet(body: object) {
        return fetch(this.#url + "/password/code/set", {
            method: "post",
            headers: {
                'Content-Type': 'application/json'
                // 'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: JSON.stringify(body),
            credentials: "include",
        })
            .then((res) => {
                return Promise.resolve(res)
            })
            .catch((error) => {
                console.error(error)

                return Promise.reject(error)
            })
    }

    /**
     * Смена пароля
     * @param body
     */
    passwordChange(body: object) {
        return fetch(this.#url + "/password/change", {
            method: "post",
            headers: {
                'Content-Type': 'application/json'
                // 'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: JSON.stringify(body),
            credentials: "include",
        })
            .then((res) => {
                return Promise.resolve(res)
            })
            .catch((error) => {
                console.error(error)

                return Promise.reject(error)
            })
    }

    getUserById(id: string) {
        return fetch(this.#url + `/user/get/${id}`, {
            method: "get",
            credentials: "include",
        })
            .then((res) => {
                return Promise.resolve(res)
            })
            .catch((error) => {
                console.error(error)

                return Promise.reject(error)
            })
    }

    saveUser(body: object) {
        return fetch(this.#url + "/user/save", {
            method: "post",
            headers: {
                'Content-Type': 'application/json'
                // 'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: JSON.stringify(body),
            credentials: "include",
        })
            .then((res) => {
                return Promise.resolve(res)
            })
            .catch((error) => {
                console.error(error)

                return Promise.reject(error)
            })
    }

    confirmationEmailGetCode(id: string) {
        return fetch(this.#url + `/user/${id}/confirmation/email/code/get`, {
            method: "get",
            credentials: "include",
        })
            .then((res) => {
                return Promise.resolve(res)
            })
            .catch((error) => {
                console.error(error)

                return Promise.reject(error)
            })
    }
}

export default new Api()