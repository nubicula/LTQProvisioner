import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Vcenter } from 'app/shared/model/vcenter.model';
import { VcenterService } from './vcenter.service';
import { VcenterComponent } from './vcenter.component';
import { VcenterDetailComponent } from './vcenter-detail.component';
import { VcenterUpdateComponent } from './vcenter-update.component';
import { VcenterDeletePopupComponent } from './vcenter-delete-dialog.component';
import { IVcenter } from 'app/shared/model/vcenter.model';

@Injectable({ providedIn: 'root' })
export class VcenterResolve implements Resolve<IVcenter> {
    constructor(private service: VcenterService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((vcenter: HttpResponse<Vcenter>) => vcenter.body);
        }
        return Observable.of(new Vcenter());
    }
}

export const vcenterRoute: Routes = [
    {
        path: 'vcenter',
        component: VcenterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vcenters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vcenter/:id/view',
        component: VcenterDetailComponent,
        resolve: {
            vcenter: VcenterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vcenters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vcenter/new',
        component: VcenterUpdateComponent,
        resolve: {
            vcenter: VcenterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vcenters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vcenter/:id/edit',
        component: VcenterUpdateComponent,
        resolve: {
            vcenter: VcenterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vcenters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vcenterPopupRoute: Routes = [
    {
        path: 'vcenter/:id/delete',
        component: VcenterDeletePopupComponent,
        resolve: {
            vcenter: VcenterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vcenters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
