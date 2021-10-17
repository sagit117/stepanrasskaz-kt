export interface IEmailData {
    id: string;
    email: string;
    isConfirmEmail: boolean;
}
export default class Email {
    #private;
    constructor(emailData: IEmailData, divId: string);
    render(): void;
}
