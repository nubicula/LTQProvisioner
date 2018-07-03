/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VMHostClusterComponent } from 'app/entities/vm-host-cluster/vm-host-cluster.component';
import { VMHostClusterService } from 'app/entities/vm-host-cluster/vm-host-cluster.service';
import { VMHostCluster } from 'app/shared/model/vm-host-cluster.model';

describe('Component Tests', () => {
    describe('VMHostCluster Management Component', () => {
        let comp: VMHostClusterComponent;
        let fixture: ComponentFixture<VMHostClusterComponent>;
        let service: VMHostClusterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VMHostClusterComponent],
                providers: []
            })
                .overrideTemplate(VMHostClusterComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VMHostClusterComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VMHostClusterService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new VMHostCluster(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.vMHostClusters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
