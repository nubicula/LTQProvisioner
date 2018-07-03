/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LtqProvisionerTestModule } from '../../../test.module';
import { DatastoreComponent } from 'app/entities/datastore/datastore.component';
import { DatastoreService } from 'app/entities/datastore/datastore.service';
import { Datastore } from 'app/shared/model/datastore.model';

describe('Component Tests', () => {
    describe('Datastore Management Component', () => {
        let comp: DatastoreComponent;
        let fixture: ComponentFixture<DatastoreComponent>;
        let service: DatastoreService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [DatastoreComponent],
                providers: []
            })
                .overrideTemplate(DatastoreComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DatastoreComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DatastoreService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Datastore(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.datastores[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
