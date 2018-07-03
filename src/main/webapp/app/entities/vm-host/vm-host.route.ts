import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { VMHost } from 'app/shared/model/vm-host.model';
import { VMHostService } from './vm-host.service';
import { VMHostComponent } from './vm-host.component';
import { VMHostDetailComponent } from './vm-host-detail.component';
import { VMHostUpdateComponent } from './vm-host-update.component';
import { VMHostDeletePopupComponent } from './vm-host-delete-dialog.component';
import { IVMHost } from 'app/shared/model/vm-host.model';

@Injectable({ providedIn: 'root' })
export class VMHostResolve implements Resolve<IVMHost> {
    constructor(private service: VMHostService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((vMHost: HttpResponse<VMHost>) => vMHost.body);
        }
        return Observable.of(new VMHost());
    }
}

export const vMHostRoute: Routes = [
    {
        path: 'vm-host',
        component: VMHostComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VMHosts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vm-host/:id/view',
        component: VMHostDetailComponent,
        resolve: {
            vMHost: VMHostResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VMHosts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vm-host/new',
        component: VMHostUpdateComponent,
        resolve: {
            vMHost: VMHostResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VMHosts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vm-host/:id/edit',
        component: VMHostUpdateComponent,
        resolve: {
            vMHost: VMHostResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VMHosts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vMHostPopupRoute: Routes = [
    {
        path: 'vm-host/:id/delete',
        component: VMHostDeletePopupComponent,
        resolve: {
            vMHost: VMHostResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VMHosts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
