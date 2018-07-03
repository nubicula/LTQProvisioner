import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVCenter } from 'app/shared/model/v-center.model';

@Component({
    selector: 'jhi-v-center-detail',
    templateUrl: './v-center-detail.component.html'
})
export class VCenterDetailComponent implements OnInit {
    vCenter: IVCenter;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vCenter }) => {
            this.vCenter = vCenter;
        });
    }

    previousState() {
        window.history.back();
    }
}
