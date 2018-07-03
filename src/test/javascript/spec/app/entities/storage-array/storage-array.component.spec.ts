/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LtqProvisionerTestModule } from '../../../test.module';
import { StorageArrayComponent } from 'app/entities/storage-array/storage-array.component';
import { StorageArrayService } from 'app/entities/storage-array/storage-array.service';
import { StorageArray } from 'app/shared/model/storage-array.model';

describe('Component Tests', () => {
    describe('StorageArray Management Component', () => {
        let comp: StorageArrayComponent;
        let fixture: ComponentFixture<StorageArrayComponent>;
        let service: StorageArrayService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [StorageArrayComponent],
                providers: []
            })
                .overrideTemplate(StorageArrayComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StorageArrayComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StorageArrayService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new StorageArray(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.storageArrays[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
