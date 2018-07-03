/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VirtualVolumeDetailComponent } from 'app/entities/virtual-volume/virtual-volume-detail.component';
import { VirtualVolume } from 'app/shared/model/virtual-volume.model';

describe('Component Tests', () => {
    describe('VirtualVolume Management Detail Component', () => {
        let comp: VirtualVolumeDetailComponent;
        let fixture: ComponentFixture<VirtualVolumeDetailComponent>;
        const route = ({ data: of({ virtualVolume: new VirtualVolume(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VirtualVolumeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VirtualVolumeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VirtualVolumeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.virtualVolume).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
