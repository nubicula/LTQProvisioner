import { IDatastoreCluster } from 'app/shared/model//datastore-cluster.model';
import { IDatastore } from 'app/shared/model//datastore.model';

export interface IVMHost {
    id?: number;
    moRef?: string;
    name?: string;
    ipAddress?: string;
    datastorecluster?: IDatastoreCluster;
    datastores?: IDatastore[];
}

export class VMHost implements IVMHost {
    constructor(
        public id?: number,
        public moRef?: string,
        public name?: string,
        public ipAddress?: string,
        public datastorecluster?: IDatastoreCluster,
        public datastores?: IDatastore[]
    ) {}
}
