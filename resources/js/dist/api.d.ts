declare class Api {
    #private;
    auth(login?: string, password?: string): Promise<Response>;
    registry(body: object): Promise<Response>;
    /**
     * Запросить код для восстановления пароля
     * @param body
     */
    passwordCodeSet(body: object): Promise<Response>;
    /**
     * Смена пароля
     * @param body
     */
    passwordChange(body: object): Promise<Response>;
    getUserById(id: string): Promise<Response>;
    saveUser(body: object): Promise<Response>;
    confirmationEmailGetCode(id: string): Promise<Response>;
}
declare const _default: Api;
export default _default;
