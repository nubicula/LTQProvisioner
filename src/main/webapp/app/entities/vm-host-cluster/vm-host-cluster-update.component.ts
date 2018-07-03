import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVMHostCluster } from 'app/shared/model/vm-host-cluster.model';
import { VMHostClusterService } from './vm-host-cluster.service';
import { IVMHost } from 'app/shared/model/vm-host.model';
import { VMHostService } from 'app/entities/vm-host';

@Component({
    selector: 'jhi-vm-host-cluster-update',
    templateUrl: './vm-host-cluster-update.component.html'
})
export class VMHostClusterUpdateComponent implements OnInit {
    private _vMHostCluster: IVMHostCluster;
    isSaving: boolean;

    vmhosts: IVMHost[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private vMHostClusterService: VMHostClusterService,
        private vMHostService: VMHostService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vMHostCluster }) => {
            this.vMHostCluster = vMHostCluster;
        });
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
        if (this.vMHostCluster.id !== undefined) {
            this.subscribeToSaveResponse(this.vMHostClusterService.update(this.vMHostCluster));
        } else {
            this.subscribeToSaveResponse(this.vMHostClusterService.create(this.vMHostCluster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVMHostCluster>>) {
        result.subscribe((res: HttpResponse<IVMHostCluster>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get vMHostCluster() {
        return this._vMHostCluster;
    }

    set vMHostCluster(vMHostCluster: IVMHostCluster) {
        this._vMHostCluster = vMHostCluster;
    }
}
