// @ts-ignore
import Owner from "../../components/owner.vue"

export interface IAccountTopMenuItem {
    id: AccountTopMenuItems
    title: string
    component?: any
    extClass?: string
}

export enum AccountTopMenuItems {
    owner,
    changePass,
    exit
}

export const accountTopMenuItems: IAccountTopMenuItem[] = [
    {
        id: AccountTopMenuItems.owner,
        title: "Владелец",
        component: Owner
    },
    {
        id: AccountTopMenuItems.changePass,
        title: "Смена пароля",
    },
    {
        id: AccountTopMenuItems.exit,
        title: "Выход",
        extClass: "accountTopMenu__item__red"
    }
]