export interface IVcenter {
    id?: number;
    moRef?: string;
    name?: string;
    ipAddress?: string;
    userName?: string;
    password?: string;
}

export class Vcenter implements IVcenter {
    constructor(
        public id?: number,
        public moRef?: string,
        public name?: string,
        public ipAddress?: string,
        public userName?: string,
        public password?: string
    ) {}
}
