export interface IPhysicalDatacenter {
    id?: number;
    name?: string;
    address?: string;
}

export class PhysicalDatacenter implements IPhysicalDatacenter {
    constructor(public id?: number, public name?: string, public address?: string) {}
}
