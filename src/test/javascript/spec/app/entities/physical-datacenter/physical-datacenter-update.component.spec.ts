/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { PhysicalDatacenterUpdateComponent } from 'app/entities/physical-datacenter/physical-datacenter-update.component';
import { PhysicalDatacenterService } from 'app/entities/physical-datacenter/physical-datacenter.service';
import { PhysicalDatacenter } from 'app/shared/model/physical-datacenter.model';

describe('Component Tests', () => {
    describe('PhysicalDatacenter Management Update Component', () => {
        let comp: PhysicalDatacenterUpdateComponent;
        let fixture: ComponentFixture<PhysicalDatacenterUpdateComponent>;
        let service: PhysicalDatacenterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [PhysicalDatacenterUpdateComponent]
            })
                .overrideTemplate(PhysicalDatacenterUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PhysicalDatacenterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PhysicalDatacenterService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PhysicalDatacenter(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.physicalDatacenter = entity;
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
                    const entity = new PhysicalDatacenter();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.physicalDatacenter = entity;
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
