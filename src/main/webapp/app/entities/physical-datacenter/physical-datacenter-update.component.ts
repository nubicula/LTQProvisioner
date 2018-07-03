import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPhysicalDatacenter } from 'app/shared/model/physical-datacenter.model';
import { PhysicalDatacenterService } from './physical-datacenter.service';
import { IVMHost } from 'app/shared/model/vm-host.model';
import { VMHostService } from 'app/entities/vm-host';
import { IStorageArray } from 'app/shared/model/storage-array.model';
import { StorageArrayService } from 'app/entities/storage-array';

@Component({
    selector: 'jhi-physical-datacenter-update',
    templateUrl: './physical-datacenter-update.component.html'
})
export class PhysicalDatacenterUpdateComponent implements OnInit {
    private _physicalDatacenter: IPhysicalDatacenter;
    isSaving: boolean;

    vmhosts: IVMHost[];

    storagearrays: IStorageArray[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private physicalDatacenterService: PhysicalDatacenterService,
        private vMHostService: VMHostService,
        private storageArrayService: StorageArrayService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ physicalDatacenter }) => {
            this.physicalDatacenter = physicalDatacenter;
        });
        this.vMHostService.query().subscribe(
            (res: HttpResponse<IVMHost[]>) => {
                this.vmhosts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.storageArrayService.query().subscribe(
            (res: HttpResponse<IStorageArray[]>) => {
                this.storagearrays = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.physicalDatacenter.id !== undefined) {
            this.subscribeToSaveResponse(this.physicalDatacenterService.update(this.physicalDatacenter));
        } else {
            this.subscribeToSaveResponse(this.physicalDatacenterService.create(this.physicalDatacenter));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPhysicalDatacenter>>) {
        result.subscribe((res: HttpResponse<IPhysicalDatacenter>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVMHostById(index: number, item: IVMHost) {
        return item.id;
    }

    trackStorageArrayById(index: number, item: IStorageArray) {
        return item.id;
    }
    get physicalDatacenter() {
        return this._physicalDatacenter;
    }

    set physicalDatacenter(physicalDatacenter: IPhysicalDatacenter) {
        this._physicalDatacenter = physicalDatacenter;
    }
}
