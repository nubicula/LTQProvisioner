/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VirtualVolumeDeleteDialogComponent } from 'app/entities/virtual-volume/virtual-volume-delete-dialog.component';
import { VirtualVolumeService } from 'app/entities/virtual-volume/virtual-volume.service';

describe('Component Tests', () => {
    describe('VirtualVolume Management Delete Component', () => {
        let comp: VirtualVolumeDeleteDialogComponent;
        let fixture: ComponentFixture<VirtualVolumeDeleteDialogComponent>;
        let service: VirtualVolumeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VirtualVolumeDeleteDialogComponent]
            })
                .overrideTemplate(VirtualVolumeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VirtualVolumeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VirtualVolumeService);
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
