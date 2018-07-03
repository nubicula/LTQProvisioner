/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VMHostUpdateComponent } from 'app/entities/vm-host/vm-host-update.component';
import { VMHostService } from 'app/entities/vm-host/vm-host.service';
import { VMHost } from 'app/shared/model/vm-host.model';

describe('Component Tests', () => {
    describe('VMHost Management Update Component', () => {
        let comp: VMHostUpdateComponent;
        let fixture: ComponentFixture<VMHostUpdateComponent>;
        let service: VMHostService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VMHostUpdateComponent]
            })
                .overrideTemplate(VMHostUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VMHostUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VMHostService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new VMHost(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vMHost = entity;
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
                    const entity = new VMHost();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vMHost = entity;
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
