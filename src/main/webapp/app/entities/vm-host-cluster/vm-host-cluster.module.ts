import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LtqProvisionerSharedModule } from 'app/shared';
import {
    VMHostClusterComponent,
    VMHostClusterDetailComponent,
    VMHostClusterUpdateComponent,
    VMHostClusterDeletePopupComponent,
    VMHostClusterDeleteDialogComponent,
    vMHostClusterRoute,
    vMHostClusterPopupRoute
} from './';

const ENTITY_STATES = [...vMHostClusterRoute, ...vMHostClusterPopupRoute];

@NgModule({
    imports: [LtqProvisionerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VMHostClusterComponent,
        VMHostClusterDetailComponent,
        VMHostClusterUpdateComponent,
        VMHostClusterDeleteDialogComponent,
        VMHostClusterDeletePopupComponent
    ],
    entryComponents: [
        VMHostClusterComponent,
        VMHostClusterUpdateComponent,
        VMHostClusterDeleteDialogComponent,
        VMHostClusterDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LtqProvisionerVMHostClusterModule {}
