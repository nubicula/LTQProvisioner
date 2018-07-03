/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LtqProvisionerTestModule } from '../../../test.module';
import { PhysicalDatacenterDeleteDialogComponent } from 'app/entities/physical-datacenter/physical-datacenter-delete-dialog.component';
import { PhysicalDatacenterService } from 'app/entities/physical-datacenter/physical-datacenter.service';

describe('Component Tests', () => {
    describe('PhysicalDatacenter Management Delete Component', () => {
        let comp: PhysicalDatacenterDeleteDialogComponent;
        let fixture: ComponentFixture<PhysicalDatacenterDeleteDialogComponent>;
        let service: PhysicalDatacenterService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [PhysicalDatacenterDeleteDialogComponent]
            })
                .overrideTemplate(PhysicalDatacenterDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PhysicalDatacenterDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PhysicalDatacenterService);
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
