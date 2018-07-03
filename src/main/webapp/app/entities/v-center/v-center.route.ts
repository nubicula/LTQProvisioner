import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { VCenter } from 'app/shared/model/v-center.model';
import { VCenterService } from './v-center.service';
import { VCenterComponent } from './v-center.component';
import { VCenterDetailComponent } from './v-center-detail.component';
import { VCenterUpdateComponent } from './v-center-update.component';
import { VCenterDeletePopupComponent } from './v-center-delete-dialog.component';
import { IVCenter } from 'app/shared/model/v-center.model';

@Injectable({ providedIn: 'root' })
export class VCenterResolve implements Resolve<IVCenter> {
    constructor(private service: VCenterService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((vCenter: HttpResponse<VCenter>) => vCenter.body);
        }
        return Observable.of(new VCenter());
    }
}

export const vCenterRoute: Routes = [
    {
        path: 'v-center',
        component: VCenterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VCenters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'v-center/:id/view',
        component: VCenterDetailComponent,
        resolve: {
            vCenter: VCenterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VCenters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'v-center/new',
        component: VCenterUpdateComponent,
        resolve: {
            vCenter: VCenterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VCenters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'v-center/:id/edit',
        component: VCenterUpdateComponent,
        resolve: {
            vCenter: VCenterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VCenters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vCenterPopupRoute: Routes = [
    {
        path: 'v-center/:id/delete',
        component: VCenterDeletePopupComponent,
        resolve: {
            vCenter: VCenterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VCenters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
