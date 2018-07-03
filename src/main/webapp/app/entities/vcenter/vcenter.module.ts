import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LtqProvisionerSharedModule } from 'app/shared';
import {
    VcenterComponent,
    VcenterDetailComponent,
    VcenterUpdateComponent,
    VcenterDeletePopupComponent,
    VcenterDeleteDialogComponent,
    vcenterRoute,
    vcenterPopupRoute
} from './';

const ENTITY_STATES = [...vcenterRoute, ...vcenterPopupRoute];

@NgModule({
    imports: [LtqProvisionerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VcenterComponent,
        VcenterDetailComponent,
        VcenterUpdateComponent,
        VcenterDeleteDialogComponent,
        VcenterDeletePopupComponent
    ],
    entryComponents: [VcenterComponent, VcenterUpdateComponent, VcenterDeleteDialogComponent, VcenterDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LtqProvisionerVcenterModule {}
