import { IPhysicalDatacenter } from 'app/shared/model//physical-datacenter.model';
import { IVMHostCluster } from 'app/shared/model//vm-host-cluster.model';
import { IDatastore } from 'app/shared/model//datastore.model';
import { IDatastoreCluster } from 'app/shared/model//datastore-cluster.model';

export interface IVMHost {
    id?: number;
    moRef?: string;
    name?: string;
    ipAddress?: string;
    physicaldatacenter?: IPhysicalDatacenter;
    vmhostcluster?: IVMHostCluster;
    datastores?: IDatastore[];
    datastoreclusters?: IDatastoreCluster[];
}

export class VMHost implements IVMHost {
    constructor(
        public id?: number,
        public moRef?: string,
        public name?: string,
        public ipAddress?: string,
        public physicaldatacenter?: IPhysicalDatacenter,
        public vmhostcluster?: IVMHostCluster,
        public datastores?: IDatastore[],
        public datastoreclusters?: IDatastoreCluster[]
    ) {}
}
