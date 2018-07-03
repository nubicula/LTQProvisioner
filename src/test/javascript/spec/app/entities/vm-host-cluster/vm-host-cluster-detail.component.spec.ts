/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VMHostClusterDetailComponent } from 'app/entities/vm-host-cluster/vm-host-cluster-detail.component';
import { VMHostCluster } from 'app/shared/model/vm-host-cluster.model';

describe('Component Tests', () => {
    describe('VMHostCluster Management Detail Component', () => {
        let comp: VMHostClusterDetailComponent;
        let fixture: ComponentFixture<VMHostClusterDetailComponent>;
        const route = ({ data: of({ vMHostCluster: new VMHostCluster(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VMHostClusterDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VMHostClusterDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VMHostClusterDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.vMHostCluster).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
