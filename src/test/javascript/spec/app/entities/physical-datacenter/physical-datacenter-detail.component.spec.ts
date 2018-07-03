/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { PhysicalDatacenterDetailComponent } from 'app/entities/physical-datacenter/physical-datacenter-detail.component';
import { PhysicalDatacenter } from 'app/shared/model/physical-datacenter.model';

describe('Component Tests', () => {
    describe('PhysicalDatacenter Management Detail Component', () => {
        let comp: PhysicalDatacenterDetailComponent;
        let fixture: ComponentFixture<PhysicalDatacenterDetailComponent>;
        const route = ({ data: of({ physicalDatacenter: new PhysicalDatacenter(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [PhysicalDatacenterDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PhysicalDatacenterDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PhysicalDatacenterDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.physicalDatacenter).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
