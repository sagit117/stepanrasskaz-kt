import { ref } from "vue";
import { IUser, IUserRepository } from "../interfaces";
// @ts-ignore
import Api from "../../../src/api"

export function getUserRepositories(id: string): IUserRepository {
    const user = ref<IUser | null>(null)
    const error = ref<string>("")
    const isLoading = ref<boolean>(true)

    Api.getUserById(id)
        .then((res) => {
            if (res.ok) return res.json()

            throw new Error(String(res.status))
        })
        .then((userRepo: IUser) => {
            user.value = userRepo
        })
        .catch((error) => {
            error.value = error
        })
        .finally(() => {
            isLoading.value = false
        })

    return {
        user,
        error,
        isLoading
    }
}