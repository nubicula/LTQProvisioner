/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LtqProvisionerTestModule } from '../../../test.module';
import { DatastoreClusterDeleteDialogComponent } from 'app/entities/datastore-cluster/datastore-cluster-delete-dialog.component';
import { DatastoreClusterService } from 'app/entities/datastore-cluster/datastore-cluster.service';

describe('Component Tests', () => {
    describe('DatastoreCluster Management Delete Component', () => {
        let comp: DatastoreClusterDeleteDialogComponent;
        let fixture: ComponentFixture<DatastoreClusterDeleteDialogComponent>;
        let service: DatastoreClusterService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [DatastoreClusterDeleteDialogComponent]
            })
                .overrideTemplate(DatastoreClusterDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DatastoreClusterDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DatastoreClusterService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
