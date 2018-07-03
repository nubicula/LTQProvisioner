import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IVcenter } from 'app/shared/model/vcenter.model';
import { VcenterService } from './vcenter.service';

@Component({
    selector: 'jhi-vcenter-update',
    templateUrl: './vcenter-update.component.html'
})
export class VcenterUpdateComponent implements OnInit {
    private _vcenter: IVcenter;
    isSaving: boolean;

    constructor(private vcenterService: VcenterService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vcenter }) => {
            this.vcenter = vcenter;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.vcenter.id !== undefined) {
            this.subscribeToSaveResponse(this.vcenterService.update(this.vcenter));
        } else {
            this.subscribeToSaveResponse(this.vcenterService.create(this.vcenter));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVcenter>>) {
        result.subscribe((res: HttpResponse<IVcenter>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get vcenter() {
        return this._vcenter;
    }

    set vcenter(vcenter: IVcenter) {
        this._vcenter = vcenter;
    }
}
