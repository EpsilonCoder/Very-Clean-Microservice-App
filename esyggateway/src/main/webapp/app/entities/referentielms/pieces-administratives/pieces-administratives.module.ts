import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PiecesAdministrativesComponent } from './list/pieces-administratives.component';
import { PiecesAdministrativesDetailComponent } from './detail/pieces-administratives-detail.component';
import { PiecesAdministrativesUpdateComponent } from './update/pieces-administratives-update.component';
import { PiecesAdministrativesDeleteDialogComponent } from './delete/pieces-administratives-delete-dialog.component';
import { PiecesAdministrativesRoutingModule } from './route/pieces-administratives-routing.module';

@NgModule({
  imports: [SharedModule, PiecesAdministrativesRoutingModule],
  declarations: [
    PiecesAdministrativesComponent,
    PiecesAdministrativesDetailComponent,
    PiecesAdministrativesUpdateComponent,
    PiecesAdministrativesDeleteDialogComponent,
  ],
})
export class ReferentielmsPiecesAdministrativesModule {}
