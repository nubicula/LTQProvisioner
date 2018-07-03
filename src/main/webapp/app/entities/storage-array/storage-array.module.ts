import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LtqProvisionerSharedModule } from 'app/shared';
import {
    StorageArrayComponent,
    StorageArrayDetailComponent,
    StorageArrayUpdateComponent,
    StorageArrayDeletePopupComponent,
    StorageArrayDeleteDialogComponent,
    storageArrayRoute,
    storageArrayPopupRoute
} from './';

const ENTITY_STATES = [...storageArrayRoute, ...storageArrayPopupRoute];

@NgModule({
    imports: [LtqProvisionerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StorageArrayComponent,
        StorageArrayDetailComponent,
        StorageArrayUpdateComponent,
        StorageArrayDeleteDialogComponent,
        StorageArrayDeletePopupComponent
    ],
    entryComponents: [
        StorageArrayComponent,
        StorageArrayUpdateComponent,
        StorageArrayDeleteDialogComponent,
        StorageArrayDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LtqProvisionerStorageArrayModule {}
