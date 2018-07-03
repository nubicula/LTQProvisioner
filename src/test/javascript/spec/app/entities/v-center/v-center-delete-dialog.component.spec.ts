/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VCenterDeleteDialogComponent } from 'app/entities/v-center/v-center-delete-dialog.component';
import { VCenterService } from 'app/entities/v-center/v-center.service';

describe('Component Tests', () => {
    describe('VCenter Management Delete Component', () => {
        let comp: VCenterDeleteDialogComponent;
        let fixture: ComponentFixture<VCenterDeleteDialogComponent>;
        let service: VCenterService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VCenterDeleteDialogComponent]
            })
                .overrideTemplate(VCenterDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VCenterDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VCenterService);
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
