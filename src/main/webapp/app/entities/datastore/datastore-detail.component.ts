import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDatastore } from 'app/shared/model/datastore.model';

@Component({
    selector: 'jhi-datastore-detail',
    templateUrl: './datastore-detail.component.html'
})
export class DatastoreDetailComponent implements OnInit {
    datastore: IDatastore;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ datastore }) => {
            this.datastore = datastore;
        });
    }

    previousState() {
        window.history.back();
    }
}
