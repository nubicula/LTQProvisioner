/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { DatastoreUpdateComponent } from 'app/entities/datastore/datastore-update.component';
import { DatastoreService } from 'app/entities/datastore/datastore.service';
import { Datastore } from 'app/shared/model/datastore.model';

describe('Component Tests', () => {
    describe('Datastore Management Update Component', () => {
        let comp: DatastoreUpdateComponent;
        let fixture: ComponentFixture<DatastoreUpdateComponent>;
        let service: DatastoreService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [DatastoreUpdateComponent]
            })
                .overrideTemplate(DatastoreUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DatastoreUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DatastoreService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Datastore(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.datastore = entity;
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
                    const entity = new Datastore();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.datastore = entity;
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
