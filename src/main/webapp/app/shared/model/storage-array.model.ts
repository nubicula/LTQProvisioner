import { IPhysicalDatacenter } from 'app/shared/model//physical-datacenter.model';

export interface IStorageArray {
    id?: number;
    name?: string;
    ipAddress?: string;
    userName?: string;
    password?: string;
    physicaldatacenter?: IPhysicalDatacenter;
}

export class StorageArray implements IStorageArray {
    constructor(
        public id?: number,
        public name?: string,
        public ipAddress?: string,
        public userName?: string,
        public password?: string,
        public physicaldatacenter?: IPhysicalDatacenter
    ) {}
}
