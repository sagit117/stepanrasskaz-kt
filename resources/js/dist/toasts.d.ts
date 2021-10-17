export default class Toast {
    #private;
    /**
     * Создаем toast data
     * @param title - заголовок
     * @param message - сообщение
     * @param type - тип ERROR | WARNING | SUCCESS
     * @param timer - время показа в секундах
     * @param cb - callback будет выполнен после закрытия
     */
    constructor(title: string, message: string, type: "ERROR" | "WARNING" | "SUCCESS", timer: number, cb?: () => void);
    destroy(): void;
    render(id: string): void;
}
