/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VcenterUpdateComponent } from 'app/entities/vcenter/vcenter-update.component';
import { VcenterService } from 'app/entities/vcenter/vcenter.service';
import { Vcenter } from 'app/shared/model/vcenter.model';

describe('Component Tests', () => {
    describe('Vcenter Management Update Component', () => {
        let comp: VcenterUpdateComponent;
        let fixture: ComponentFixture<VcenterUpdateComponent>;
        let service: VcenterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VcenterUpdateComponent]
            })
                .overrideTemplate(VcenterUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VcenterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VcenterService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Vcenter(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vcenter = entity;
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
                    const entity = new Vcenter();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vcenter = entity;
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
