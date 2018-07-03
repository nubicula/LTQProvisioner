import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDatastore } from 'app/shared/model/datastore.model';
import { DatastoreService } from './datastore.service';
import { IStorageArray } from 'app/shared/model/storage-array.model';
import { StorageArrayService } from 'app/entities/storage-array';
import { IDatastoreCluster } from 'app/shared/model/datastore-cluster.model';
import { DatastoreClusterService } from 'app/entities/datastore-cluster';
import { IVMHost } from 'app/shared/model/vm-host.model';
import { VMHostService } from 'app/entities/vm-host';

@Component({
    selector: 'jhi-datastore-update',
    templateUrl: './datastore-update.component.html'
})
export class DatastoreUpdateComponent implements OnInit {
    private _datastore: IDatastore;
    isSaving: boolean;

    storagearrays: IStorageArray[];

    datastoreclusters: IDatastoreCluster[];

    vmhosts: IVMHost[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private datastoreService: DatastoreService,
        private storageArrayService: StorageArrayService,
        private datastoreClusterService: DatastoreClusterService,
        private vMHostService: VMHostService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ datastore }) => {
            this.datastore = datastore;
        });
        this.storageArrayService.query().subscribe(
            (res: HttpResponse<IStorageArray[]>) => {
                this.storagearrays = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.datastoreClusterService.query().subscribe(
            (res: HttpResponse<IDatastoreCluster[]>) => {
                this.datastoreclusters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.vMHostService.query().subscribe(
            (res: HttpResponse<IVMHost[]>) => {
                this.vmhosts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.datastore.id !== undefined) {
            this.subscribeToSaveResponse(this.datastoreService.update(this.datastore));
        } else {
            this.subscribeToSaveResponse(this.datastoreService.create(this.datastore));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDatastore>>) {
        result.subscribe((res: HttpResponse<IDatastore>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackStorageArrayById(index: number, item: IStorageArray) {
        return item.id;
    }

    trackDatastoreClusterById(index: number, item: IDatastoreCluster) {
        return item.id;
    }

    trackVMHostById(index: number, item: IVMHost) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get datastore() {
        return this._datastore;
    }

    set datastore(datastore: IDatastore) {
        this._datastore = datastore;
    }
}
