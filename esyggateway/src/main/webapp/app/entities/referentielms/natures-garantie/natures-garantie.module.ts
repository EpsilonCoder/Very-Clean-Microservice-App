import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NaturesGarantieComponent } from './list/natures-garantie.component';
import { NaturesGarantieDetailComponent } from './detail/natures-garantie-detail.component';
import { NaturesGarantieUpdateComponent } from './update/natures-garantie-update.component';
import { NaturesGarantieDeleteDialogComponent } from './delete/natures-garantie-delete-dialog.component';
import { NaturesGarantieRoutingModule } from './route/natures-garantie-routing.module';

@NgModule({
  imports: [SharedModule, NaturesGarantieRoutingModule],
  declarations: [
    NaturesGarantieComponent,
    NaturesGarantieDetailComponent,
    NaturesGarantieUpdateComponent,
    NaturesGarantieDeleteDialogComponent,
  ],
})
export class ReferentielmsNaturesGarantieModule {}
