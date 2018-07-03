import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVMHostCluster } from 'app/shared/model/vm-host-cluster.model';

@Component({
    selector: 'jhi-vm-host-cluster-detail',
    templateUrl: './vm-host-cluster-detail.component.html'
})
export class VMHostClusterDetailComponent implements OnInit {
    vMHostCluster: IVMHostCluster;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vMHostCluster }) => {
            this.vMHostCluster = vMHostCluster;
        });
    }

    previousState() {
        window.history.back();
    }
}
