import { IVMHost } from 'app/shared/model//vm-host.model';

export interface IDatastoreCluster {
    id?: number;
    moRef?: string;
    name?: string;
    vmhosts?: IVMHost[];
}

export class DatastoreCluster implements IDatastoreCluster {
    constructor(public id?: number, public moRef?: string, public name?: string, public vmhosts?: IVMHost[]) {}
}
