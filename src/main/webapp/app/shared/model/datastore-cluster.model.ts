import { IDatastore } from 'app/shared/model//datastore.model';

export interface IDatastoreCluster {
    id?: number;
    moRef?: string;
    name?: string;
    datastore?: IDatastore;
}

export class DatastoreCluster implements IDatastoreCluster {
    constructor(public id?: number, public moRef?: string, public name?: string, public datastore?: IDatastore) {}
}
