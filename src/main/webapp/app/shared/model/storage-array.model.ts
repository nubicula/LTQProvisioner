import { IDatastore } from 'app/shared/model//datastore.model';
import { IVirtualVolume } from 'app/shared/model//virtual-volume.model';

export interface IStorageArray {
    id?: number;
    name?: string;
    ipAddress?: string;
    userName?: string;
    password?: string;
    datastore?: IDatastore;
    virtualvolume?: IVirtualVolume;
}

export class StorageArray implements IStorageArray {
    constructor(
        public id?: number,
        public name?: string,
        public ipAddress?: string,
        public userName?: string,
        public password?: string,
        public datastore?: IDatastore,
        public virtualvolume?: IVirtualVolume
    ) {}
}
