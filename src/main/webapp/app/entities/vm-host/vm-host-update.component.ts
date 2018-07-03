import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVMHost } from 'app/shared/model/vm-host.model';
import { VMHostService } from './vm-host.service';
import { IVMHostCluster } from 'app/shared/model/vm-host-cluster.model';
import { VMHostClusterService } from 'app/entities/vm-host-cluster';
import { IPhysicalDatacenter } from 'app/shared/model/physical-datacenter.model';
import { PhysicalDatacenterService } from 'app/entities/physical-datacenter';
import { IDatastore } from 'app/shared/model/datastore.model';
import { DatastoreService } from 'app/entities/datastore';
import { IDatastoreCluster } from 'app/shared/model/datastore-cluster.model';
import { DatastoreClusterService } from 'app/entities/datastore-cluster';

@Component({
    selector: 'jhi-vm-host-update',
    templateUrl: './vm-host-update.component.html'
})
export class VMHostUpdateComponent implements OnInit {
    private _vMHost: IVMHost;
    isSaving: boolean;

    vmhostclusters: IVMHostCluster[];

    physicaldatacenters: IPhysicalDatacenter[];

    datastores: IDatastore[];

    datastoreclusters: IDatastoreCluster[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private vMHostService: VMHostService,
        private vMHostClusterService: VMHostClusterService,
        private physicalDatacenterService: PhysicalDatacenterService,
        private datastoreService: DatastoreService,
        private datastoreClusterService: DatastoreClusterService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vMHost }) => {
            this.vMHost = vMHost;
        });
        this.vMHostClusterService.query().subscribe(
            (res: HttpResponse<IVMHostCluster[]>) => {
                this.vmhostclusters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.physicalDatacenterService.query().subscribe(
            (res: HttpResponse<IPhysicalDatacenter[]>) => {
                this.physicaldatacenters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.datastoreService.query().subscribe(
            (res: HttpResponse<IDatastore[]>) => {
                this.datastores = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.datastoreClusterService.query().subscribe(
            (res: HttpResponse<IDatastoreCluster[]>) => {
                this.datastoreclusters = res.body;
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

    trackVMHostClusterById(index: number, item: IVMHostCluster) {
        return item.id;
    }

    trackPhysicalDatacenterById(index: number, item: IPhysicalDatacenter) {
        return item.id;
    }

    trackDatastoreById(index: number, item: IDatastore) {
        return item.id;
    }

    trackDatastoreClusterById(index: number, item: IDatastoreCluster) {
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
