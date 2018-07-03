import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IStorageArray } from 'app/shared/model/storage-array.model';
import { StorageArrayService } from './storage-array.service';
import { IPhysicalDatacenter } from 'app/shared/model/physical-datacenter.model';
import { PhysicalDatacenterService } from 'app/entities/physical-datacenter';

@Component({
    selector: 'jhi-storage-array-update',
    templateUrl: './storage-array-update.component.html'
})
export class StorageArrayUpdateComponent implements OnInit {
    private _storageArray: IStorageArray;
    isSaving: boolean;

    physicaldatacenters: IPhysicalDatacenter[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private storageArrayService: StorageArrayService,
        private physicalDatacenterService: PhysicalDatacenterService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ storageArray }) => {
            this.storageArray = storageArray;
        });
        this.physicalDatacenterService.query().subscribe(
            (res: HttpResponse<IPhysicalDatacenter[]>) => {
                this.physicaldatacenters = res.body;
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

    trackPhysicalDatacenterById(index: number, item: IPhysicalDatacenter) {
        return item.id;
    }
    get storageArray() {
        return this._storageArray;
    }

    set storageArray(storageArray: IStorageArray) {
        this._storageArray = storageArray;
    }
}
