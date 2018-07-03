/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LtqProvisionerTestModule } from '../../../test.module';
import { DatastoreClusterComponent } from 'app/entities/datastore-cluster/datastore-cluster.component';
import { DatastoreClusterService } from 'app/entities/datastore-cluster/datastore-cluster.service';
import { DatastoreCluster } from 'app/shared/model/datastore-cluster.model';

describe('Component Tests', () => {
    describe('DatastoreCluster Management Component', () => {
        let comp: DatastoreClusterComponent;
        let fixture: ComponentFixture<DatastoreClusterComponent>;
        let service: DatastoreClusterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [DatastoreClusterComponent],
                providers: []
            })
                .overrideTemplate(DatastoreClusterComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DatastoreClusterComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DatastoreClusterService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DatastoreCluster(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.datastoreClusters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
