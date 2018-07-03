import { IVirtualVolume } from 'app/shared/model//virtual-volume.model';

export interface IDatastore {
    id?: number;
    moRef?: string;
    name?: string;
    wwn?: string;
    virtualvolumes?: IVirtualVolume[];
}

export class Datastore implements IDatastore {
    constructor(
        public id?: number,
        public moRef?: string,
        public name?: string,
        public wwn?: string,
        public virtualvolumes?: IVirtualVolume[]
    ) {}
}
