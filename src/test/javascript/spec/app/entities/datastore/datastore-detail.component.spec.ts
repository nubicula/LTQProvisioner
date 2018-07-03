/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { DatastoreDetailComponent } from 'app/entities/datastore/datastore-detail.component';
import { Datastore } from 'app/shared/model/datastore.model';

describe('Component Tests', () => {
    describe('Datastore Management Detail Component', () => {
        let comp: DatastoreDetailComponent;
        let fixture: ComponentFixture<DatastoreDetailComponent>;
        const route = ({ data: of({ datastore: new Datastore(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [DatastoreDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DatastoreDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DatastoreDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.datastore).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
