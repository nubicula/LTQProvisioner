import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStorageArray } from 'app/shared/model/storage-array.model';
import { StorageArrayService } from './storage-array.service';

@Component({
    selector: 'jhi-storage-array-delete-dialog',
    templateUrl: './storage-array-delete-dialog.component.html'
})
export class StorageArrayDeleteDialogComponent {
    storageArray: IStorageArray;

    constructor(
        private storageArrayService: StorageArrayService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.storageArrayService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'storageArrayListModification',
                content: 'Deleted an storageArray'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-storage-array-delete-popup',
    template: ''
})
export class StorageArrayDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ storageArray }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StorageArrayDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.storageArray = storageArray;
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
