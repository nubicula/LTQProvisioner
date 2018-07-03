import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { DatastoreCluster } from 'app/shared/model/datastore-cluster.model';
import { DatastoreClusterService } from './datastore-cluster.service';
import { DatastoreClusterComponent } from './datastore-cluster.component';
import { DatastoreClusterDetailComponent } from './datastore-cluster-detail.component';
import { DatastoreClusterUpdateComponent } from './datastore-cluster-update.component';
import { DatastoreClusterDeletePopupComponent } from './datastore-cluster-delete-dialog.component';
import { IDatastoreCluster } from 'app/shared/model/datastore-cluster.model';

@Injectable({ providedIn: 'root' })
export class DatastoreClusterResolve implements Resolve<IDatastoreCluster> {
    constructor(private service: DatastoreClusterService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((datastoreCluster: HttpResponse<DatastoreCluster>) => datastoreCluster.body);
        }
        return Observable.of(new DatastoreCluster());
    }
}

export const datastoreClusterRoute: Routes = [
    {
        path: 'datastore-cluster',
        component: DatastoreClusterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DatastoreClusters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'datastore-cluster/:id/view',
        component: DatastoreClusterDetailComponent,
        resolve: {
            datastoreCluster: DatastoreClusterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DatastoreClusters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'datastore-cluster/new',
        component: DatastoreClusterUpdateComponent,
        resolve: {
            datastoreCluster: DatastoreClusterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DatastoreClusters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'datastore-cluster/:id/edit',
        component: DatastoreClusterUpdateComponent,
        resolve: {
            datastoreCluster: DatastoreClusterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DatastoreClusters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const datastoreClusterPopupRoute: Routes = [
    {
        path: 'datastore-cluster/:id/delete',
        component: DatastoreClusterDeletePopupComponent,
        resolve: {
            datastoreCluster: DatastoreClusterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DatastoreClusters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
