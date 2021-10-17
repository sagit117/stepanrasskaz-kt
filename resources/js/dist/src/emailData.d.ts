export interface IEmailData {
    email: string;
    isConfirmEmail: boolean;
}
export default class Email {
    #private;
    constructor(emailData: IEmailData, divId: string);
    render(): void;
}
//# sourceMappingURL=emailData.d.ts.map