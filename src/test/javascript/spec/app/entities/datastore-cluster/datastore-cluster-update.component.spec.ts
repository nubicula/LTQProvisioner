/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { DatastoreClusterUpdateComponent } from 'app/entities/datastore-cluster/datastore-cluster-update.component';
import { DatastoreClusterService } from 'app/entities/datastore-cluster/datastore-cluster.service';
import { DatastoreCluster } from 'app/shared/model/datastore-cluster.model';

describe('Component Tests', () => {
    describe('DatastoreCluster Management Update Component', () => {
        let comp: DatastoreClusterUpdateComponent;
        let fixture: ComponentFixture<DatastoreClusterUpdateComponent>;
        let service: DatastoreClusterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [DatastoreClusterUpdateComponent]
            })
                .overrideTemplate(DatastoreClusterUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DatastoreClusterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DatastoreClusterService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DatastoreCluster(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.datastoreCluster = entity;
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
                    const entity = new DatastoreCluster();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.datastoreCluster = entity;
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
