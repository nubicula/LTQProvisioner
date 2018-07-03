import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LtqProvisionerSharedModule } from 'app/shared';
import {
    VCenterComponent,
    VCenterDetailComponent,
    VCenterUpdateComponent,
    VCenterDeletePopupComponent,
    VCenterDeleteDialogComponent,
    vCenterRoute,
    vCenterPopupRoute
} from './';

const ENTITY_STATES = [...vCenterRoute, ...vCenterPopupRoute];

@NgModule({
    imports: [LtqProvisionerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VCenterComponent,
        VCenterDetailComponent,
        VCenterUpdateComponent,
        VCenterDeleteDialogComponent,
        VCenterDeletePopupComponent
    ],
    entryComponents: [VCenterComponent, VCenterUpdateComponent, VCenterDeleteDialogComponent, VCenterDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LtqProvisionerVCenterModule {}
