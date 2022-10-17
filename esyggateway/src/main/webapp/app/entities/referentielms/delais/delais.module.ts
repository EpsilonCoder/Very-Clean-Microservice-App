import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DelaisComponent } from './list/delais.component';
import { DelaisDetailComponent } from './detail/delais-detail.component';
import { DelaisUpdateComponent } from './update/delais-update.component';
import { DelaisDeleteDialogComponent } from './delete/delais-delete-dialog.component';
import { DelaisRoutingModule } from './route/delais-routing.module';

@NgModule({
  imports: [SharedModule, DelaisRoutingModule],
  declarations: [DelaisComponent, DelaisDetailComponent, DelaisUpdateComponent, DelaisDeleteDialogComponent],
})
export class ReferentielmsDelaisModule {}
