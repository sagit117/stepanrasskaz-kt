class Api {
    #url = "/api/v1"

    auth(login, password) {
        return fetch(this.#url + "/login", {
            method: "post",
            headers: {
                'Content-Type': 'application/json'
                // 'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: JSON.stringify({ login, password }),
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