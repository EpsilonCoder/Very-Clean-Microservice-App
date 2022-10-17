import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SituationMatrimonialeComponent } from './list/situation-matrimoniale.component';
import { SituationMatrimonialeDetailComponent } from './detail/situation-matrimoniale-detail.component';
import { SituationMatrimonialeUpdateComponent } from './update/situation-matrimoniale-update.component';
import { SituationMatrimonialeDeleteDialogComponent } from './delete/situation-matrimoniale-delete-dialog.component';
import { SituationMatrimonialeRoutingModule } from './route/situation-matrimoniale-routing.module';

@NgModule({
  imports: [SharedModule, SituationMatrimonialeRoutingModule],
  declarations: [
    SituationMatrimonialeComponent,
    SituationMatrimonialeDetailComponent,
    SituationMatrimonialeUpdateComponent,
    SituationMatrimonialeDeleteDialogComponent,
  ],
})
export class ReferentielmsSituationMatrimonialeModule {}
