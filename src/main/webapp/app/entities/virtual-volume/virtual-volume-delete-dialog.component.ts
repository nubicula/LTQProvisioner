import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVirtualVolume } from 'app/shared/model/virtual-volume.model';
import { VirtualVolumeService } from './virtual-volume.service';

@Component({
    selector: 'jhi-virtual-volume-delete-dialog',
    templateUrl: './virtual-volume-delete-dialog.component.html'
})
export class VirtualVolumeDeleteDialogComponent {
    virtualVolume: IVirtualVolume;

    constructor(
        private virtualVolumeService: VirtualVolumeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.virtualVolumeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'virtualVolumeListModification',
                content: 'Deleted an virtualVolume'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-virtual-volume-delete-popup',
    template: ''
})
export class VirtualVolumeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ virtualVolume }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VirtualVolumeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.virtualVolume = virtualVolume;
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
