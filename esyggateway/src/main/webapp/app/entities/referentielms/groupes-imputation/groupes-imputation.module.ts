import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GroupesImputationComponent } from './list/groupes-imputation.component';
import { GroupesImputationDetailComponent } from './detail/groupes-imputation-detail.component';
import { GroupesImputationUpdateComponent } from './update/groupes-imputation-update.component';
import { GroupesImputationDeleteDialogComponent } from './delete/groupes-imputation-delete-dialog.component';
import { GroupesImputationRoutingModule } from './route/groupes-imputation-routing.module';

@NgModule({
  imports: [SharedModule, GroupesImputationRoutingModule],
  declarations: [
    GroupesImputationComponent,
    GroupesImputationDetailComponent,
    GroupesImputationUpdateComponent,
    GroupesImputationDeleteDialogComponent,
  ],
})
export class ReferentielmsGroupesImputationModule {}
