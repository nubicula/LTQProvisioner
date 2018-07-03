/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VcenterComponent } from 'app/entities/vcenter/vcenter.component';
import { VcenterService } from 'app/entities/vcenter/vcenter.service';
import { Vcenter } from 'app/shared/model/vcenter.model';

describe('Component Tests', () => {
    describe('Vcenter Management Component', () => {
        let comp: VcenterComponent;
        let fixture: ComponentFixture<VcenterComponent>;
        let service: VcenterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VcenterComponent],
                providers: []
            })
                .overrideTemplate(VcenterComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VcenterComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VcenterService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Vcenter(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.vcenters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
