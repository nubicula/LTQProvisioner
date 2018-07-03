/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { StorageArrayDetailComponent } from 'app/entities/storage-array/storage-array-detail.component';
import { StorageArray } from 'app/shared/model/storage-array.model';

describe('Component Tests', () => {
    describe('StorageArray Management Detail Component', () => {
        let comp: StorageArrayDetailComponent;
        let fixture: ComponentFixture<StorageArrayDetailComponent>;
        const route = ({ data: of({ storageArray: new StorageArray(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [StorageArrayDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StorageArrayDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StorageArrayDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.storageArray).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
