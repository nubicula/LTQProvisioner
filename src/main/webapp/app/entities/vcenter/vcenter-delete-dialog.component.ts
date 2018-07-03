import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVcenter } from 'app/shared/model/vcenter.model';
import { VcenterService } from './vcenter.service';

@Component({
    selector: 'jhi-vcenter-delete-dialog',
    templateUrl: './vcenter-delete-dialog.component.html'
})
export class VcenterDeleteDialogComponent {
    vcenter: IVcenter;

    constructor(private vcenterService: VcenterService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vcenterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'vcenterListModification',
                content: 'Deleted an vcenter'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vcenter-delete-popup',
    template: ''
})
export class VcenterDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vcenter }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VcenterDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.vcenter = vcenter;
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
