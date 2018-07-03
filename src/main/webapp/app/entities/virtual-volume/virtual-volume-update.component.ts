import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVirtualVolume } from 'app/shared/model/virtual-volume.model';
import { VirtualVolumeService } from './virtual-volume.service';

@Component({
    selector: 'jhi-virtual-volume-update',
    templateUrl: './virtual-volume-update.component.html'
})
export class VirtualVolumeUpdateComponent implements OnInit {
    private _virtualVolume: IVirtualVolume;
    isSaving: boolean;

    virtualvolumes: IVirtualVolume[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private virtualVolumeService: VirtualVolumeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ virtualVolume }) => {
            this.virtualVolume = virtualVolume;
        });
        this.virtualVolumeService.query({ filter: 'virtualvolume-is-null' }).subscribe(
            (res: HttpResponse<IVirtualVolume[]>) => {
                if (!this.virtualVolume.virtualvolume || !this.virtualVolume.virtualvolume.id) {
                    this.virtualvolumes = res.body;
                } else {
                    this.virtualVolumeService.find(this.virtualVolume.virtualvolume.id).subscribe(
                        (subRes: HttpResponse<IVirtualVolume>) => {
                            this.virtualvolumes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
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

    trackVirtualVolumeById(index: number, item: IVirtualVolume) {
        return item.id;
    }
    get virtualVolume() {
        return this._virtualVolume;
    }

    set virtualVolume(virtualVolume: IVirtualVolume) {
        this._virtualVolume = virtualVolume;
    }
}
