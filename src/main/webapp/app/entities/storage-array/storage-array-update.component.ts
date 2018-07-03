import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IStorageArray } from 'app/shared/model/storage-array.model';
import { StorageArrayService } from './storage-array.service';
import { IDatastore } from 'app/shared/model/datastore.model';
import { DatastoreService } from 'app/entities/datastore';
import { IVirtualVolume } from 'app/shared/model/virtual-volume.model';
import { VirtualVolumeService } from 'app/entities/virtual-volume';

@Component({
    selector: 'jhi-storage-array-update',
    templateUrl: './storage-array-update.component.html'
})
export class StorageArrayUpdateComponent implements OnInit {
    private _storageArray: IStorageArray;
    isSaving: boolean;

    datastores: IDatastore[];

    virtualvolumes: IVirtualVolume[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private storageArrayService: StorageArrayService,
        private datastoreService: DatastoreService,
        private virtualVolumeService: VirtualVolumeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ storageArray }) => {
            this.storageArray = storageArray;
        });
        this.datastoreService.query().subscribe(
            (res: HttpResponse<IDatastore[]>) => {
                this.datastores = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.virtualVolumeService.query().subscribe(
            (res: HttpResponse<IVirtualVolume[]>) => {
                this.virtualvolumes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.storageArray.id !== undefined) {
            this.subscribeToSaveResponse(this.storageArrayService.update(this.storageArray));
        } else {
            this.subscribeToSaveResponse(this.storageArrayService.create(this.storageArray));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStorageArray>>) {
        result.subscribe((res: HttpResponse<IStorageArray>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDatastoreById(index: number, item: IDatastore) {
        return item.id;
    }

    trackVirtualVolumeById(index: number, item: IVirtualVolume) {
        return item.id;
    }
    get storageArray() {
        return this._storageArray;
    }

    set storageArray(storageArray: IStorageArray) {
        this._storageArray = storageArray;
    }
}
