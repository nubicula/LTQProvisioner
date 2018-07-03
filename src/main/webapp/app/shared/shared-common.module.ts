import { NgModule } from '@angular/core';

import { LtqProvisionerSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [LtqProvisionerSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [LtqProvisionerSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class LtqProvisionerSharedCommonModule {}
