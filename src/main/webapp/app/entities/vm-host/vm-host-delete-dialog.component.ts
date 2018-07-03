import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVMHost } from 'app/shared/model/vm-host.model';
import { VMHostService } from './vm-host.service';

@Component({
    selector: 'jhi-vm-host-delete-dialog',
    templateUrl: './vm-host-delete-dialog.component.html'
})
export class VMHostDeleteDialogComponent {
    vMHost: IVMHost;

    constructor(private vMHostService: VMHostService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vMHostService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'vMHostListModification',
                content: 'Deleted an vMHost'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vm-host-delete-popup',
    template: ''
})
export class VMHostDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vMHost }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VMHostDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.vMHost = vMHost;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
