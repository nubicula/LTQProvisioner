/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { StorageArrayUpdateComponent } from 'app/entities/storage-array/storage-array-update.component';
import { StorageArrayService } from 'app/entities/storage-array/storage-array.service';
import { StorageArray } from 'app/shared/model/storage-array.model';

describe('Component Tests', () => {
    describe('StorageArray Management Update Component', () => {
        let comp: StorageArrayUpdateComponent;
        let fixture: ComponentFixture<StorageArrayUpdateComponent>;
        let service: StorageArrayService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [StorageArrayUpdateComponent]
            })
                .overrideTemplate(StorageArrayUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StorageArrayUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StorageArrayService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StorageArray(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.storageArray = entity;
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
                    const entity = new StorageArray();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.storageArray = entity;
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
