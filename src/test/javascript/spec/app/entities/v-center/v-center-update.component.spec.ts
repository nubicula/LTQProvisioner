/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VCenterUpdateComponent } from 'app/entities/v-center/v-center-update.component';
import { VCenterService } from 'app/entities/v-center/v-center.service';
import { VCenter } from 'app/shared/model/v-center.model';

describe('Component Tests', () => {
    describe('VCenter Management Update Component', () => {
        let comp: VCenterUpdateComponent;
        let fixture: ComponentFixture<VCenterUpdateComponent>;
        let service: VCenterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VCenterUpdateComponent]
            })
                .overrideTemplate(VCenterUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VCenterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VCenterService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new VCenter(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vCenter = entity;
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
                    const entity = new VCenter();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vCenter = entity;
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
