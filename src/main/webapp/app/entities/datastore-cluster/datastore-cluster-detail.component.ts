import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDatastoreCluster } from 'app/shared/model/datastore-cluster.model';

@Component({
    selector: 'jhi-datastore-cluster-detail',
    templateUrl: './datastore-cluster-detail.component.html'
})
export class DatastoreClusterDetailComponent implements OnInit {
    datastoreCluster: IDatastoreCluster;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ datastoreCluster }) => {
            this.datastoreCluster = datastoreCluster;
        });
    }

    previousState() {
        window.history.back();
    }
}
