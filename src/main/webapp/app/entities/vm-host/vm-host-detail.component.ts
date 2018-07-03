import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVMHost } from 'app/shared/model/vm-host.model';

@Component({
    selector: 'jhi-vm-host-detail',
    templateUrl: './vm-host-detail.component.html'
})
export class VMHostDetailComponent implements OnInit {
    vMHost: IVMHost;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vMHost }) => {
            this.vMHost = vMHost;
        });
    }

    previousState() {
        window.history.back();
    }
}
