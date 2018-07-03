import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDatastoreCluster } from 'app/shared/model/datastore-cluster.model';
import { DatastoreClusterService } from './datastore-cluster.service';
import { IDatastore } from 'app/shared/model/datastore.model';
import { DatastoreService } from 'app/entities/datastore';

@Component({
    selector: 'jhi-datastore-cluster-update',
    templateUrl: './datastore-cluster-update.component.html'
})
export class DatastoreClusterUpdateComponent implements OnInit {
    private _datastoreCluster: IDatastoreCluster;
    isSaving: boolean;

    datastores: IDatastore[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private datastoreClusterService: DatastoreClusterService,
        private datastoreService: DatastoreService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ datastoreCluster }) => {
            this.datastoreCluster = datastoreCluster;
        });
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
        if (this.datastoreCluster.id !== undefined) {
            this.subscribeToSaveResponse(this.datastoreClusterService.update(this.datastoreCluster));
        } else {
            this.subscribeToSaveResponse(this.datastoreClusterService.create(this.datastoreCluster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDatastoreCluster>>) {
        result.subscribe((res: HttpResponse<IDatastoreCluster>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get datastoreCluster() {
        return this._datastoreCluster;
    }

    set datastoreCluster(datastoreCluster: IDatastoreCluster) {
        this._datastoreCluster = datastoreCluster;
    }
}
