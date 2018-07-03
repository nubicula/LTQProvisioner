import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPhysicalDatacenter } from 'app/shared/model/physical-datacenter.model';

@Component({
    selector: 'jhi-physical-datacenter-detail',
    templateUrl: './physical-datacenter-detail.component.html'
})
export class PhysicalDatacenterDetailComponent implements OnInit {
    physicalDatacenter: IPhysicalDatacenter;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ physicalDatacenter }) => {
            this.physicalDatacenter = physicalDatacenter;
        });
    }

    previousState() {
        window.history.back();
    }
}
