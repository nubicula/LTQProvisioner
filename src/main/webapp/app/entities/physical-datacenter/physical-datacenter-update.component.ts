import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPhysicalDatacenter } from 'app/shared/model/physical-datacenter.model';
import { PhysicalDatacenterService } from './physical-datacenter.service';

@Component({
    selector: 'jhi-physical-datacenter-update',
    templateUrl: './physical-datacenter-update.component.html'
})
export class PhysicalDatacenterUpdateComponent implements OnInit {
    private _physicalDatacenter: IPhysicalDatacenter;
    isSaving: boolean;

    constructor(private physicalDatacenterService: PhysicalDatacenterService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ physicalDatacenter }) => {
            this.physicalDatacenter = physicalDatacenter;
        });
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
    get physicalDatacenter() {
        return this._physicalDatacenter;
    }

    set physicalDatacenter(physicalDatacenter: IPhysicalDatacenter) {
        this._physicalDatacenter = physicalDatacenter;
    }
}
