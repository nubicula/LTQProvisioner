import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDatastoreCluster } from 'app/shared/model/datastore-cluster.model';
import { DatastoreClusterService } from './datastore-cluster.service';

@Component({
    selector: 'jhi-datastore-cluster-delete-dialog',
    templateUrl: './datastore-cluster-delete-dialog.component.html'
})
export class DatastoreClusterDeleteDialogComponent {
    datastoreCluster: IDatastoreCluster;

    constructor(
        private datastoreClusterService: DatastoreClusterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.datastoreClusterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'datastoreClusterListModification',
                content: 'Deleted an datastoreCluster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-datastore-cluster-delete-popup',
    template: ''
})
export class DatastoreClusterDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ datastoreCluster }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DatastoreClusterDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.datastoreCluster = datastoreCluster;
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
