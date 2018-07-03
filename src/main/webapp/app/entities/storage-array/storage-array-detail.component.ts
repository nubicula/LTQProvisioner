import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStorageArray } from 'app/shared/model/storage-array.model';

@Component({
    selector: 'jhi-storage-array-detail',
    templateUrl: './storage-array-detail.component.html'
})
export class StorageArrayDetailComponent implements OnInit {
    storageArray: IStorageArray;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ storageArray }) => {
            this.storageArray = storageArray;
        });
    }

    previousState() {
        window.history.back();
    }
}
