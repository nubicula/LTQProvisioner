import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LtqProvisionerSharedModule } from 'app/shared';
import {
    DatastoreComponent,
    DatastoreDetailComponent,
    DatastoreUpdateComponent,
    DatastoreDeletePopupComponent,
    DatastoreDeleteDialogComponent,
    datastoreRoute,
    datastorePopupRoute
} from './';

const ENTITY_STATES = [...datastoreRoute, ...datastorePopupRoute];

@NgModule({
    imports: [LtqProvisionerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DatastoreComponent,
        DatastoreDetailComponent,
        DatastoreUpdateComponent,
        DatastoreDeleteDialogComponent,
        DatastoreDeletePopupComponent
    ],
    entryComponents: [DatastoreComponent, DatastoreUpdateComponent, DatastoreDeleteDialogComponent, DatastoreDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LtqProvisionerDatastoreModule {}
