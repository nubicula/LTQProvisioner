import { IVirtualVolume } from 'app/shared/model//virtual-volume.model';

export interface IVirtualVolume {
    id?: number;
    name?: string;
    wwn?: string;
    lunID?: string;
    peerVolume?: string;
    virtualvolume?: IVirtualVolume;
}

export class VirtualVolume implements IVirtualVolume {
    constructor(
        public id?: number,
        public name?: string,
        public wwn?: string,
        public lunID?: string,
        public peerVolume?: string,
        public virtualvolume?: IVirtualVolume
    ) {}
}
