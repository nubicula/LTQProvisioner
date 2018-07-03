import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { StorageArray } from 'app/shared/model/storage-array.model';
import { StorageArrayService } from './storage-array.service';
import { StorageArrayComponent } from './storage-array.component';
import { StorageArrayDetailComponent } from './storage-array-detail.component';
import { StorageArrayUpdateComponent } from './storage-array-update.component';
import { StorageArrayDeletePopupComponent } from './storage-array-delete-dialog.component';
import { IStorageArray } from 'app/shared/model/storage-array.model';

@Injectable({ providedIn: 'root' })
export class StorageArrayResolve implements Resolve<IStorageArray> {
    constructor(private service: StorageArrayService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((storageArray: HttpResponse<StorageArray>) => storageArray.body);
        }
        return Observable.of(new StorageArray());
    }
}

export const storageArrayRoute: Routes = [
    {
        path: 'storage-array',
        component: StorageArrayComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StorageArrays'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'storage-array/:id/view',
        component: StorageArrayDetailComponent,
        resolve: {
            storageArray: StorageArrayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StorageArrays'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'storage-array/new',
        component: StorageArrayUpdateComponent,
        resolve: {
            storageArray: StorageArrayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StorageArrays'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'storage-array/:id/edit',
        component: StorageArrayUpdateComponent,
        resolve: {
            storageArray: StorageArrayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StorageArrays'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const storageArrayPopupRoute: Routes = [
    {
        path: 'storage-array/:id/delete',
        component: StorageArrayDeletePopupComponent,
        resolve: {
            storageArray: StorageArrayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StorageArrays'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
