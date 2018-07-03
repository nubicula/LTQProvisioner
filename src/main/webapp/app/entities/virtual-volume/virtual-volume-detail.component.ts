import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVirtualVolume } from 'app/shared/model/virtual-volume.model';

@Component({
    selector: 'jhi-virtual-volume-detail',
    templateUrl: './virtual-volume-detail.component.html'
})
export class VirtualVolumeDetailComponent implements OnInit {
    virtualVolume: IVirtualVolume;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ virtualVolume }) => {
            this.virtualVolume = virtualVolume;
        });
    }

    previousState() {
        window.history.back();
    }
}
