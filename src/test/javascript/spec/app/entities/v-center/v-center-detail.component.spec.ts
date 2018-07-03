/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VCenterDetailComponent } from 'app/entities/v-center/v-center-detail.component';
import { VCenter } from 'app/shared/model/v-center.model';

describe('Component Tests', () => {
    describe('VCenter Management Detail Component', () => {
        let comp: VCenterDetailComponent;
        let fixture: ComponentFixture<VCenterDetailComponent>;
        const route = ({ data: of({ vCenter: new VCenter(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VCenterDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VCenterDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VCenterDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.vCenter).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
