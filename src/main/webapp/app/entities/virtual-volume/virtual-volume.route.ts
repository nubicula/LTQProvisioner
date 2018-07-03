import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { VirtualVolume } from 'app/shared/model/virtual-volume.model';
import { VirtualVolumeService } from './virtual-volume.service';
import { VirtualVolumeComponent } from './virtual-volume.component';
import { VirtualVolumeDetailComponent } from './virtual-volume-detail.component';
import { VirtualVolumeUpdateComponent } from './virtual-volume-update.component';
import { VirtualVolumeDeletePopupComponent } from './virtual-volume-delete-dialog.component';
import { IVirtualVolume } from 'app/shared/model/virtual-volume.model';

@Injectable({ providedIn: 'root' })
export class VirtualVolumeResolve implements Resolve<IVirtualVolume> {
    constructor(private service: VirtualVolumeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((virtualVolume: HttpResponse<VirtualVolume>) => virtualVolume.body);
        }
        return Observable.of(new VirtualVolume());
    }
}

export const virtualVolumeRoute: Routes = [
    {
        path: 'virtual-volume',
        component: VirtualVolumeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VirtualVolumes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'virtual-volume/:id/view',
        component: VirtualVolumeDetailComponent,
        resolve: {
            virtualVolume: VirtualVolumeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VirtualVolumes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'virtual-volume/new',
        component: VirtualVolumeUpdateComponent,
        resolve: {
            virtualVolume: VirtualVolumeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VirtualVolumes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'virtual-volume/:id/edit',
        component: VirtualVolumeUpdateComponent,
        resolve: {
            virtualVolume: VirtualVolumeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VirtualVolumes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const virtualVolumePopupRoute: Routes = [
    {
        path: 'virtual-volume/:id/delete',
        component: VirtualVolumeDeletePopupComponent,
        resolve: {
            virtualVolume: VirtualVolumeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VirtualVolumes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
