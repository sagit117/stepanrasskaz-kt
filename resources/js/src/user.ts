'use strict'

export interface IUser {
    id: string
    dateTimeAtCreation: number
    email: string
    zipCode: string
    address: string
    isBlocked: boolean
    isNeedPassword: boolean
    name: string
    password: string
    role: string[]
    roleGroupsId: string[]
}

export default class User {
    #user: IUser
    #rootDiv: HTMLElement | null

    constructor(user: IUser, divId: string) {
        if (!user) throw new Error("User is required!")

        this.#user = user
        this.#rootDiv = document.getElementById(divId)

        if (!this.#rootDiv) throw new Error("Root div is required")
    }

    public showEmail() {
        const emailDiv = document.createElement("div")
    }
}