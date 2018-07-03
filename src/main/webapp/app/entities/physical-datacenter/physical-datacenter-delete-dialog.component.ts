import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPhysicalDatacenter } from 'app/shared/model/physical-datacenter.model';
import { PhysicalDatacenterService } from './physical-datacenter.service';

@Component({
    selector: 'jhi-physical-datacenter-delete-dialog',
    templateUrl: './physical-datacenter-delete-dialog.component.html'
})
export class PhysicalDatacenterDeleteDialogComponent {
    physicalDatacenter: IPhysicalDatacenter;

    constructor(
        private physicalDatacenterService: PhysicalDatacenterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.physicalDatacenterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'physicalDatacenterListModification',
                content: 'Deleted an physicalDatacenter'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-physical-datacenter-delete-popup',
    template: ''
})
export class PhysicalDatacenterDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ physicalDatacenter }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PhysicalDatacenterDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.physicalDatacenter = physicalDatacenter;
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
