import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LtqProvisionerSharedModule } from 'app/shared';
import {
    DatastoreClusterComponent,
    DatastoreClusterDetailComponent,
    DatastoreClusterUpdateComponent,
    DatastoreClusterDeletePopupComponent,
    DatastoreClusterDeleteDialogComponent,
    datastoreClusterRoute,
    datastoreClusterPopupRoute
} from './';

const ENTITY_STATES = [...datastoreClusterRoute, ...datastoreClusterPopupRoute];

@NgModule({
    imports: [LtqProvisionerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DatastoreClusterComponent,
        DatastoreClusterDetailComponent,
        DatastoreClusterUpdateComponent,
        DatastoreClusterDeleteDialogComponent,
        DatastoreClusterDeletePopupComponent
    ],
    entryComponents: [
        DatastoreClusterComponent,
        DatastoreClusterUpdateComponent,
        DatastoreClusterDeleteDialogComponent,
        DatastoreClusterDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LtqProvisionerDatastoreClusterModule {}
