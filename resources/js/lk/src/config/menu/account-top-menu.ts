export interface IAccountTopMenuItem {
    id: AccountTopMenuItems
    title: String
    extClass?: String
}

export enum AccountTopMenuItems {
    owner,
    changePass,
    exit
}

export const accountTopMenuItems: IAccountTopMenuItem[] = [
    {
        id: AccountTopMenuItems.owner,
        title: "Владелец"
    },
    {
        id: AccountTopMenuItems.changePass,
        title: "Смена пароля"
    },
    {
        id: AccountTopMenuItems.exit,
        title: "Выход",
        extClass: "accountTopMenu__item__red"
    }
]