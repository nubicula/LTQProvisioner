import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IVCenter } from 'app/shared/model/v-center.model';
import { VCenterService } from './v-center.service';

@Component({
    selector: 'jhi-v-center-update',
    templateUrl: './v-center-update.component.html'
})
export class VCenterUpdateComponent implements OnInit {
    private _vCenter: IVCenter;
    isSaving: boolean;

    constructor(private vCenterService: VCenterService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vCenter }) => {
            this.vCenter = vCenter;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.vCenter.id !== undefined) {
            this.subscribeToSaveResponse(this.vCenterService.update(this.vCenter));
        } else {
            this.subscribeToSaveResponse(this.vCenterService.create(this.vCenter));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVCenter>>) {
        result.subscribe((res: HttpResponse<IVCenter>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get vCenter() {
        return this._vCenter;
    }

    set vCenter(vCenter: IVCenter) {
        this._vCenter = vCenter;
    }
}
