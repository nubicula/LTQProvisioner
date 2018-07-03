import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LtqProvisionerSharedModule } from 'app/shared';
import {
    PhysicalDatacenterComponent,
    PhysicalDatacenterDetailComponent,
    PhysicalDatacenterUpdateComponent,
    PhysicalDatacenterDeletePopupComponent,
    PhysicalDatacenterDeleteDialogComponent,
    physicalDatacenterRoute,
    physicalDatacenterPopupRoute
} from './';

const ENTITY_STATES = [...physicalDatacenterRoute, ...physicalDatacenterPopupRoute];

@NgModule({
    imports: [LtqProvisionerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PhysicalDatacenterComponent,
        PhysicalDatacenterDetailComponent,
        PhysicalDatacenterUpdateComponent,
        PhysicalDatacenterDeleteDialogComponent,
        PhysicalDatacenterDeletePopupComponent
    ],
    entryComponents: [
        PhysicalDatacenterComponent,
        PhysicalDatacenterUpdateComponent,
        PhysicalDatacenterDeleteDialogComponent,
        PhysicalDatacenterDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LtqProvisionerPhysicalDatacenterModule {}
