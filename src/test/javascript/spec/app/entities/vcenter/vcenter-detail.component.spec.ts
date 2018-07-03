/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VcenterDetailComponent } from 'app/entities/vcenter/vcenter-detail.component';
import { Vcenter } from 'app/shared/model/vcenter.model';

describe('Component Tests', () => {
    describe('Vcenter Management Detail Component', () => {
        let comp: VcenterDetailComponent;
        let fixture: ComponentFixture<VcenterDetailComponent>;
        const route = ({ data: of({ vcenter: new Vcenter(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VcenterDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VcenterDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VcenterDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.vcenter).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
