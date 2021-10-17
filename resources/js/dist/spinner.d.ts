export default class Spinner {
    #private;
    /**
     * Класс spinner
     * @param idDivBlocking - id элемента, который необходимо покрыть оверлеем
     */
    constructor(idDivBlocking?: string);
    render(id: string): void;
    destroy(): void;
    isRender(): boolean;
}
