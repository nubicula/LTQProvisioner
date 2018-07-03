/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VMHostClusterUpdateComponent } from 'app/entities/vm-host-cluster/vm-host-cluster-update.component';
import { VMHostClusterService } from 'app/entities/vm-host-cluster/vm-host-cluster.service';
import { VMHostCluster } from 'app/shared/model/vm-host-cluster.model';

describe('Component Tests', () => {
    describe('VMHostCluster Management Update Component', () => {
        let comp: VMHostClusterUpdateComponent;
        let fixture: ComponentFixture<VMHostClusterUpdateComponent>;
        let service: VMHostClusterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VMHostClusterUpdateComponent]
            })
                .overrideTemplate(VMHostClusterUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VMHostClusterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VMHostClusterService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new VMHostCluster(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vMHostCluster = entity;
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
                    const entity = new VMHostCluster();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vMHostCluster = entity;
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
