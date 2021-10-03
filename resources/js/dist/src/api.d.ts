declare class Api {
    #private;
    auth(login?: string, password?: string): Promise<Response>;
    registry(body: object): Promise<Response>;
    /**
     * Запросить код для восстановления пароля
     * @param body
     */
    passwordCodeSet(body: object): Promise<Response>;
}
declare const _default: Api;
export default _default;
//# sourceMappingURL=api.d.ts.map