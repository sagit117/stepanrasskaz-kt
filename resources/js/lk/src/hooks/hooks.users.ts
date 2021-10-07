import { reactive } from "vue";
import { IUser } from "../interfaces";
// @ts-ignore
import Api from "../../../src/api"

export function getUserRepositories(id: string): IUser | null | object {
    if (!id) return null

    const user = reactive<IUser | object>({})

    Api.getUserById(id)

    return user
}