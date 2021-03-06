import { IDatastore } from 'app/shared/model//datastore.model';
import { IVirtualVolume } from 'app/shared/model//virtual-volume.model';
import { IStorageArray } from 'app/shared/model//storage-array.model';

export interface IVirtualVolume {
    id?: number;
    name?: string;
    wwn?: string;
    lunID?: string;
    peerVolume?: string;
    datastore?: IDatastore;
    virtualvolumepeer?: IVirtualVolume;
    storagearray?: IStorageArray;
    virtualvolume?: IVirtualVolume;
}

export class VirtualVolume implements IVirtualVolume {
    constructor(
        public id?: number,
        public name?: string,
        public wwn?: string,
        public lunID?: string,
        public peerVolume?: string,
        public datastore?: IDatastore,
        public virtualvolumepeer?: IVirtualVolume,
        public storagearray?: IStorageArray,
        public virtualvolume?: IVirtualVolume
    ) {}
}
