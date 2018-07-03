import { IVMHostCluster } from 'app/shared/model//vm-host-cluster.model';

export interface IVCenter {
    id?: number;
    moRef?: string;
    name?: string;
    ipAddress?: string;
    userName?: string;
    password?: string;
    vmhostcluster?: IVMHostCluster;
}

export class VCenter implements IVCenter {
    constructor(
        public id?: number,
        public moRef?: string,
        public name?: string,
        public ipAddress?: string,
        public userName?: string,
        public password?: string,
        public vmhostcluster?: IVMHostCluster
    ) {}
}
