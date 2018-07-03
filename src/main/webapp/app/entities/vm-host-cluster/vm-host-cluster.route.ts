import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { VMHostCluster } from 'app/shared/model/vm-host-cluster.model';
import { VMHostClusterService } from './vm-host-cluster.service';
import { VMHostClusterComponent } from './vm-host-cluster.component';
import { VMHostClusterDetailComponent } from './vm-host-cluster-detail.component';
import { VMHostClusterUpdateComponent } from './vm-host-cluster-update.component';
import { VMHostClusterDeletePopupComponent } from './vm-host-cluster-delete-dialog.component';
import { IVMHostCluster } from 'app/shared/model/vm-host-cluster.model';

@Injectable({ providedIn: 'root' })
export class VMHostClusterResolve implements Resolve<IVMHostCluster> {
    constructor(private service: VMHostClusterService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((vMHostCluster: HttpResponse<VMHostCluster>) => vMHostCluster.body);
        }
        return Observable.of(new VMHostCluster());
    }
}

export const vMHostClusterRoute: Routes = [
    {
        path: 'vm-host-cluster',
        component: VMHostClusterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VMHostClusters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vm-host-cluster/:id/view',
        component: VMHostClusterDetailComponent,
        resolve: {
            vMHostCluster: VMHostClusterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VMHostClusters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vm-host-cluster/new',
        component: VMHostClusterUpdateComponent,
        resolve: {
            vMHostCluster: VMHostClusterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VMHostClusters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vm-host-cluster/:id/edit',
        component: VMHostClusterUpdateComponent,
        resolve: {
            vMHostCluster: VMHostClusterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VMHostClusters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vMHostClusterPopupRoute: Routes = [
    {
        path: 'vm-host-cluster/:id/delete',
        component: VMHostClusterDeletePopupComponent,
        resolve: {
            vMHostCluster: VMHostClusterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VMHostClusters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
