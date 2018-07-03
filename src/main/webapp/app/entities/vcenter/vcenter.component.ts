import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVcenter } from 'app/shared/model/vcenter.model';
import { Principal } from 'app/core';
import { VcenterService } from './vcenter.service';

@Component({
    selector: 'jhi-vcenter',
    templateUrl: './vcenter.component.html'
})
export class VcenterComponent implements OnInit, OnDestroy {
    vcenters: IVcenter[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private vcenterService: VcenterService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.vcenterService.query().subscribe(
            (res: HttpResponse<IVcenter[]>) => {
                this.vcenters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInVcenters();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IVcenter) {
        return item.id;
    }

    registerChangeInVcenters() {
        this.eventSubscriber = this.eventManager.subscribe('vcenterListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
