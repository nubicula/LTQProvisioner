import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVMHost } from 'app/shared/model/vm-host.model';
import { VMHostService } from './vm-host.service';
import { IDatastoreCluster } from 'app/shared/model/datastore-cluster.model';
import { DatastoreClusterService } from 'app/entities/datastore-cluster';
import { IDatastore } from 'app/shared/model/datastore.model';
import { DatastoreService } from 'app/entities/datastore';

@Component({
    selector: 'jhi-vm-host-update',
    templateUrl: './vm-host-update.component.html'
})
export class VMHostUpdateComponent implements OnInit {
    private _vMHost: IVMHost;
    isSaving: boolean;

    datastoreclusters: IDatastoreCluster[];

    datastores: IDatastore[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private vMHostService: VMHostService,
        private datastoreClusterService: DatastoreClusterService,
        private datastoreService: DatastoreService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vMHost }) => {
            this.vMHost = vMHost;
        });
        this.datastoreClusterService.query().subscribe(
            (res: HttpResponse<IDatastoreCluster[]>) => {
                this.datastoreclusters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.datastoreService.query().subscribe(
            (res: HttpResponse<IDatastore[]>) => {
                this.datastores = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.vMHost.id !== undefined) {
            this.subscribeToSaveResponse(this.vMHostService.update(this.vMHost));
        } else {
            this.subscribeToSaveResponse(this.vMHostService.create(this.vMHost));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVMHost>>) {
        result.subscribe((res: HttpResponse<IVMHost>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDatastoreClusterById(index: number, item: IDatastoreCluster) {
        return item.id;
    }

    trackDatastoreById(index: number, item: IDatastore) {
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
    get vMHost() {
        return this._vMHost;
    }

    set vMHost(vMHost: IVMHost) {
        this._vMHost = vMHost;
    }
}
