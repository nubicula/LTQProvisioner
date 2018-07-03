/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VCenterComponent } from 'app/entities/v-center/v-center.component';
import { VCenterService } from 'app/entities/v-center/v-center.service';
import { VCenter } from 'app/shared/model/v-center.model';

describe('Component Tests', () => {
    describe('VCenter Management Component', () => {
        let comp: VCenterComponent;
        let fixture: ComponentFixture<VCenterComponent>;
        let service: VCenterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VCenterComponent],
                providers: []
            })
                .overrideTemplate(VCenterComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VCenterComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VCenterService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new VCenter(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.vCenters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
