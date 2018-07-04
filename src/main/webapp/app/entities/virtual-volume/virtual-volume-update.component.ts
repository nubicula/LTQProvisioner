import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVirtualVolume } from 'app/shared/model/virtual-volume.model';
import { VirtualVolumeService } from './virtual-volume.service';
import { IDatastore } from 'app/shared/model/datastore.model';
import { DatastoreService } from 'app/entities/datastore';
import { IStorageArray } from 'app/shared/model/storage-array.model';
import { StorageArrayService } from 'app/entities/storage-array';

@Component({
    selector: 'jhi-virtual-volume-update',
    templateUrl: './virtual-volume-update.component.html'
})
export class VirtualVolumeUpdateComponent implements OnInit {
    private _virtualVolume: IVirtualVolume;
    isSaving: boolean;

    datastores: IDatastore[];

    virtualvolumepeers: IVirtualVolume[];

    storagearrays: IStorageArray[];

    virtualvolumes: IVirtualVolume[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private virtualVolumeService: VirtualVolumeService,
        private datastoreService: DatastoreService,
        private storageArrayService: StorageArrayService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ virtualVolume }) => {
            this.virtualVolume = virtualVolume;
        });
        this.datastoreService.query({ filter: 'virtualvolume-is-null' }).subscribe(
            (res: HttpResponse<IDatastore[]>) => {
                if (!this.virtualVolume.datastore || !this.virtualVolume.datastore.id) {
                    this.datastores = res.body;
                } else {
                    this.datastoreService.find(this.virtualVolume.datastore.id).subscribe(
                        (subRes: HttpResponse<IDatastore>) => {
                            this.datastores = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.virtualVolumeService.query({ filter: 'virtualvolume(name)-is-null' }).subscribe(
            (res: HttpResponse<IVirtualVolume[]>) => {
                if (!this.virtualVolume.virtualvolumepeer || !this.virtualVolume.virtualvolumepeer.id) {
                    this.virtualvolumepeers = res.body;
                } else {
                    this.virtualVolumeService.find(this.virtualVolume.virtualvolumepeer.id).subscribe(
                        (subRes: HttpResponse<IVirtualVolume>) => {
                            this.virtualvolumepeers = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.storageArrayService.query().subscribe(
            (res: HttpResponse<IStorageArray[]>) => {
                this.storagearrays = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.virtualVolume.id !== undefined) {
            this.subscribeToSaveResponse(this.virtualVolumeService.update(this.virtualVolume));
        } else {
            this.subscribeToSaveResponse(this.virtualVolumeService.create(this.virtualVolume));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVirtualVolume>>) {
        result.subscribe((res: HttpResponse<IVirtualVolume>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVirtualVolumeById(index: number, item: IVirtualVolume) {
        return item.id;
    }

    trackStorageArrayById(index: number, item: IStorageArray) {
        return item.id;
    }
    get virtualVolume() {
        return this._virtualVolume;
    }

    set virtualVolume(virtualVolume: IVirtualVolume) {
        this._virtualVolume = virtualVolume;
    }
}
