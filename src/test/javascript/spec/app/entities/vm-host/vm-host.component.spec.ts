/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VMHostComponent } from 'app/entities/vm-host/vm-host.component';
import { VMHostService } from 'app/entities/vm-host/vm-host.service';
import { VMHost } from 'app/shared/model/vm-host.model';

describe('Component Tests', () => {
    describe('VMHost Management Component', () => {
        let comp: VMHostComponent;
        let fixture: ComponentFixture<VMHostComponent>;
        let service: VMHostService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VMHostComponent],
                providers: []
            })
                .overrideTemplate(VMHostComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VMHostComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VMHostService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new VMHost(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.vMHosts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
