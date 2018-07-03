/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VMHostDetailComponent } from 'app/entities/vm-host/vm-host-detail.component';
import { VMHost } from 'app/shared/model/vm-host.model';

describe('Component Tests', () => {
    describe('VMHost Management Detail Component', () => {
        let comp: VMHostDetailComponent;
        let fixture: ComponentFixture<VMHostDetailComponent>;
        const route = ({ data: of({ vMHost: new VMHost(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VMHostDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VMHostDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VMHostDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.vMHost).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
