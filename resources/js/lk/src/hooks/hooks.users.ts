import { ref } from "vue";
import {IUser, IUserGetResponse, IUserRepository} from "../interfaces";
// @ts-ignore
import Api from "../../../src/api"

export function getUserRepositories(id: string): IUserRepository {
    const user = ref<IUser | null>(null)
    const error = ref<number>(0)
    const isLoading = ref<boolean>(true)

    Api.getUserById(id)
        .then((res) => {
            if (res.ok) return res.json()

            throw new Error(String(res.status))
        })
        .then((userRepo: IUserGetResponse) => {
            user.value = userRepo.user
        })
        .catch((err: Error) => {
            error.value = Number(err?.message)
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