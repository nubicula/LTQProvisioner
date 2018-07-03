import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPhysicalDatacenter } from 'app/shared/model/physical-datacenter.model';
import { Principal } from 'app/core';
import { PhysicalDatacenterService } from './physical-datacenter.service';

@Component({
    selector: 'jhi-physical-datacenter',
    templateUrl: './physical-datacenter.component.html'
})
export class PhysicalDatacenterComponent implements OnInit, OnDestroy {
    physicalDatacenters: IPhysicalDatacenter[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private physicalDatacenterService: PhysicalDatacenterService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.physicalDatacenterService.query().subscribe(
            (res: HttpResponse<IPhysicalDatacenter[]>) => {
                this.physicalDatacenters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPhysicalDatacenters();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPhysicalDatacenter) {
        return item.id;
    }

    registerChangeInPhysicalDatacenters() {
        this.eventSubscriber = this.eventManager.subscribe('physicalDatacenterListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
