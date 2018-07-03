/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LtqProvisionerTestModule } from '../../../test.module';
import { PhysicalDatacenterComponent } from 'app/entities/physical-datacenter/physical-datacenter.component';
import { PhysicalDatacenterService } from 'app/entities/physical-datacenter/physical-datacenter.service';
import { PhysicalDatacenter } from 'app/shared/model/physical-datacenter.model';

describe('Component Tests', () => {
    describe('PhysicalDatacenter Management Component', () => {
        let comp: PhysicalDatacenterComponent;
        let fixture: ComponentFixture<PhysicalDatacenterComponent>;
        let service: PhysicalDatacenterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [PhysicalDatacenterComponent],
                providers: []
            })
                .overrideTemplate(PhysicalDatacenterComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PhysicalDatacenterComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PhysicalDatacenterService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PhysicalDatacenter(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.physicalDatacenters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
