import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVcenter } from 'app/shared/model/vcenter.model';

@Component({
    selector: 'jhi-vcenter-detail',
    templateUrl: './vcenter-detail.component.html'
})
export class VcenterDetailComponent implements OnInit {
    vcenter: IVcenter;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vcenter }) => {
            this.vcenter = vcenter;
        });
    }

    previousState() {
        window.history.back();
    }
}
