import { IVCenter } from 'app/shared/model//v-center.model';

export interface IVMHostCluster {
    id?: number;
    moRef?: string;
    name?: string;
    vCenter?: IVCenter;
}

export class VMHostCluster implements IVMHostCluster {
    constructor(public id?: number, public moRef?: string, public name?: string, public vCenter?: IVCenter) {}
}
