import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SpecialitesPersonnelComponent } from './list/specialites-personnel.component';
import { SpecialitesPersonnelDetailComponent } from './detail/specialites-personnel-detail.component';
import { SpecialitesPersonnelUpdateComponent } from './update/specialites-personnel-update.component';
import { SpecialitesPersonnelDeleteDialogComponent } from './delete/specialites-personnel-delete-dialog.component';
import { SpecialitesPersonnelRoutingModule } from './route/specialites-personnel-routing.module';

@NgModule({
  imports: [SharedModule, SpecialitesPersonnelRoutingModule],
  declarations: [
    SpecialitesPersonnelComponent,
    SpecialitesPersonnelDetailComponent,
    SpecialitesPersonnelUpdateComponent,
    SpecialitesPersonnelDeleteDialogComponent,
  ],
})
export class ReferentielmsSpecialitesPersonnelModule {}
