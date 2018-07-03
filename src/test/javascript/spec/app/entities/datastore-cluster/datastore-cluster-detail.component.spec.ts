/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { DatastoreClusterDetailComponent } from 'app/entities/datastore-cluster/datastore-cluster-detail.component';
import { DatastoreCluster } from 'app/shared/model/datastore-cluster.model';

describe('Component Tests', () => {
    describe('DatastoreCluster Management Detail Component', () => {
        let comp: DatastoreClusterDetailComponent;
        let fixture: ComponentFixture<DatastoreClusterDetailComponent>;
        const route = ({ data: of({ datastoreCluster: new DatastoreCluster(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [DatastoreClusterDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DatastoreClusterDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DatastoreClusterDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.datastoreCluster).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
