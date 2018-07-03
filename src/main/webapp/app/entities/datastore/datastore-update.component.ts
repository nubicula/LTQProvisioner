import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDatastore } from 'app/shared/model/datastore.model';
import { DatastoreService } from './datastore.service';
import { IVirtualVolume } from 'app/shared/model/virtual-volume.model';
import { VirtualVolumeService } from 'app/entities/virtual-volume';

@Component({
    selector: 'jhi-datastore-update',
    templateUrl: './datastore-update.component.html'
})
export class DatastoreUpdateComponent implements OnInit {
    private _datastore: IDatastore;
    isSaving: boolean;

    virtualvolumes: IVirtualVolume[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private datastoreService: DatastoreService,
        private virtualVolumeService: VirtualVolumeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ datastore }) => {
            this.datastore = datastore;
        });
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
        if (this.datastore.id !== undefined) {
            this.subscribeToSaveResponse(this.datastoreService.update(this.datastore));
        } else {
            this.subscribeToSaveResponse(this.datastoreService.create(this.datastore));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDatastore>>) {
        result.subscribe((res: HttpResponse<IDatastore>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVirtualVolumeById(index: number, item: IVirtualVolume) {
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
    get datastore() {
        return this._datastore;
    }

    set datastore(datastore: IDatastore) {
        this._datastore = datastore;
    }
}
