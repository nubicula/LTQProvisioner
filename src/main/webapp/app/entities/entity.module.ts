import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LtqProvisionerVCenterModule } from './v-center/v-center.module';
import { LtqProvisionerPhysicalDatacenterModule } from './physical-datacenter/physical-datacenter.module';
import { LtqProvisionerVMHostClusterModule } from './vm-host-cluster/vm-host-cluster.module';
import { LtqProvisionerDatastoreClusterModule } from './datastore-cluster/datastore-cluster.module';
import { LtqProvisionerVMHostModule } from './vm-host/vm-host.module';
import { LtqProvisionerDatastoreModule } from './datastore/datastore.module';
import { LtqProvisionerStorageArrayModule } from './storage-array/storage-array.module';
import { LtqProvisionerVirtualVolumeModule } from './virtual-volume/virtual-volume.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        LtqProvisionerVCenterModule,
        LtqProvisionerPhysicalDatacenterModule,
        LtqProvisionerVMHostClusterModule,
        LtqProvisionerDatastoreClusterModule,
        LtqProvisionerVMHostModule,
        LtqProvisionerDatastoreModule,
        LtqProvisionerStorageArrayModule,
        LtqProvisionerVirtualVolumeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LtqProvisionerEntityModule {}
