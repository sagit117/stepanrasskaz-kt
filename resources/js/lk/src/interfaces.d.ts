import { Ref } from "vue";

export interface IUser {
    email: string
    dateTimeAtCreation: number
    id: string
    isBlocked: boolean
    isNeedPassword: boolean
    name: string
    password: string
    role: string[]
    roleGroupsId: string[]
}

export interface IUserRepository {
    user: Ref<IUser | null>,
    error: Ref<number>,
    isLoading: Ref<boolean>
}

export interface IUserGetResponse {
    user: IUser
}