import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVMHostCluster } from 'app/shared/model/vm-host-cluster.model';
import { VMHostClusterService } from './vm-host-cluster.service';

@Component({
    selector: 'jhi-vm-host-cluster-delete-dialog',
    templateUrl: './vm-host-cluster-delete-dialog.component.html'
})
export class VMHostClusterDeleteDialogComponent {
    vMHostCluster: IVMHostCluster;

    constructor(
        private vMHostClusterService: VMHostClusterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vMHostClusterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'vMHostClusterListModification',
                content: 'Deleted an vMHostCluster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vm-host-cluster-delete-popup',
    template: ''
})
export class VMHostClusterDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vMHostCluster }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VMHostClusterDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.vMHostCluster = vMHostCluster;
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
