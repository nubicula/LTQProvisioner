import { IVcenter } from 'app/shared/model//vcenter.model';

export interface IVMHostCluster {
    id?: number;
    moRef?: string;
    name?: string;
    vcenter?: IVcenter;
}

export class VMHostCluster implements IVMHostCluster {
    constructor(public id?: number, public moRef?: string, public name?: string, public vcenter?: IVcenter) {}
}
