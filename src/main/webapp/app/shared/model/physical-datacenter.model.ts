import { IVMHost } from 'app/shared/model//vm-host.model';
import { IStorageArray } from 'app/shared/model//storage-array.model';

export interface IPhysicalDatacenter {
    id?: number;
    name?: string;
    address?: string;
    vmhost?: IVMHost;
    storagearray?: IStorageArray;
}

export class PhysicalDatacenter implements IPhysicalDatacenter {
    constructor(
        public id?: number,
        public name?: string,
        public address?: string,
        public vmhost?: IVMHost,
        public storagearray?: IStorageArray
    ) {}
}
