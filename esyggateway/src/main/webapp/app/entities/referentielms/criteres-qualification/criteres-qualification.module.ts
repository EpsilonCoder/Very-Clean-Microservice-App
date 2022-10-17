import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CriteresQualificationComponent } from './list/criteres-qualification.component';
import { CriteresQualificationDetailComponent } from './detail/criteres-qualification-detail.component';
import { CriteresQualificationUpdateComponent } from './update/criteres-qualification-update.component';
import { CriteresQualificationDeleteDialogComponent } from './delete/criteres-qualification-delete-dialog.component';
import { CriteresQualificationRoutingModule } from './route/criteres-qualification-routing.module';

@NgModule({
  imports: [SharedModule, CriteresQualificationRoutingModule],
  declarations: [
    CriteresQualificationComponent,
    CriteresQualificationDetailComponent,
    CriteresQualificationUpdateComponent,
    CriteresQualificationDeleteDialogComponent,
  ],
})
export class ReferentielmsCriteresQualificationModule {}
