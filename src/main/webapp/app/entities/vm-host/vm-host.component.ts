import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVMHost } from 'app/shared/model/vm-host.model';
import { Principal } from 'app/core';
import { VMHostService } from './vm-host.service';

@Component({
    selector: 'jhi-vm-host',
    templateUrl: './vm-host.component.html'
})
export class VMHostComponent implements OnInit, OnDestroy {
    vMHosts: IVMHost[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private vMHostService: VMHostService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.vMHostService.query().subscribe(
            (res: HttpResponse<IVMHost[]>) => {
                this.vMHosts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInVMHosts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IVMHost) {
        return item.id;
    }

    registerChangeInVMHosts() {
        this.eventSubscriber = this.eventManager.subscribe('vMHostListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
