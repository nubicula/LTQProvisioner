/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LtqProvisionerTestModule } from '../../../test.module';
import { VirtualVolumeComponent } from 'app/entities/virtual-volume/virtual-volume.component';
import { VirtualVolumeService } from 'app/entities/virtual-volume/virtual-volume.service';
import { VirtualVolume } from 'app/shared/model/virtual-volume.model';

describe('Component Tests', () => {
    describe('VirtualVolume Management Component', () => {
        let comp: VirtualVolumeComponent;
        let fixture: ComponentFixture<VirtualVolumeComponent>;
        let service: VirtualVolumeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LtqProvisionerTestModule],
                declarations: [VirtualVolumeComponent],
                providers: []
            })
                .overrideTemplate(VirtualVolumeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VirtualVolumeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VirtualVolumeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new VirtualVolume(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.virtualVolumes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
