import { IVMHost } from 'app/shared/model//vm-host.model';

export interface IVMHostCluster {
    id?: number;
    moRef?: string;
    name?: string;
    vmhost?: IVMHost;
}

export class VMHostCluster implements IVMHostCluster {
    constructor(public id?: number, public moRef?: string, public name?: string, public vmhost?: IVMHost) {}
}
