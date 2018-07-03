/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VirtualVolumeUpdateComponent } from 'app/entities/virtual-volume/virtual-volume-update.component';
import { VirtualVolumeService } from 'app/entities/virtual-volume/virtual-volume.service';
import { VirtualVolume } from 'app/shared/model/virtual-volume.model';

describe('Component Tests', () => {
    describe('VirtualVolume Management Update Component', () => {
        let comp: VirtualVolumeUpdateComponent;
        let fixture: ComponentFixture<VirtualVolumeUpdateComponent>;
        let service: VirtualVolumeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VirtualVolumeUpdateComponent]
            })
                .overrideTemplate(VirtualVolumeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VirtualVolumeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VirtualVolumeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new VirtualVolume(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.virtualVolume = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new VirtualVolume();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.virtualVolume = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
