import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GarantieComponent } from './list/garantie.component';
import { GarantieDetailComponent } from './detail/garantie-detail.component';
import { GarantieUpdateComponent } from './update/garantie-update.component';
import { GarantieDeleteDialogComponent } from './delete/garantie-delete-dialog.component';
import { GarantieRoutingModule } from './route/garantie-routing.module';

@NgModule({
  imports: [SharedModule, GarantieRoutingModule],
  declarations: [GarantieComponent, GarantieDetailComponent, GarantieUpdateComponent, GarantieDeleteDialogComponent],
})
export class ReferentielmsGarantieModule {}
