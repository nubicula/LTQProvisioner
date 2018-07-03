import { IStorageArray } from 'app/shared/model//storage-array.model';
import { IDatastoreCluster } from 'app/shared/model//datastore-cluster.model';
import { IVMHost } from 'app/shared/model//vm-host.model';

export interface IDatastore {
    id?: number;
    moRef?: string;
    name?: string;
    wwn?: string;
    storagearray?: IStorageArray;
    datastorecluster?: IDatastoreCluster;
    vmhosts?: IVMHost[];
}

export class Datastore implements IDatastore {
    constructor(
        public id?: number,
        public moRef?: string,
        public name?: string,
        public wwn?: string,
        public storagearray?: IStorageArray,
        public datastorecluster?: IDatastoreCluster,
        public vmhosts?: IVMHost[]
    ) {}
}
