/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VMHostDeleteDialogComponent } from 'app/entities/vm-host/vm-host-delete-dialog.component';
import { VMHostService } from 'app/entities/vm-host/vm-host.service';

describe('Component Tests', () => {
    describe('VMHost Management Delete Component', () => {
        let comp: VMHostDeleteDialogComponent;
        let fixture: ComponentFixture<VMHostDeleteDialogComponent>;
        let service: VMHostService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VMHostDeleteDialogComponent]
            })
                .overrideTemplate(VMHostDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VMHostDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VMHostService);
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
