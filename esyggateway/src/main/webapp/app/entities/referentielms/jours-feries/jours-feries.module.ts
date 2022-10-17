import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { JoursFeriesComponent } from './list/jours-feries.component';
import { JoursFeriesDetailComponent } from './detail/jours-feries-detail.component';
import { JoursFeriesUpdateComponent } from './update/jours-feries-update.component';
import { JoursFeriesDeleteDialogComponent } from './delete/jours-feries-delete-dialog.component';
import { JoursFeriesRoutingModule } from './route/jours-feries-routing.module';

@NgModule({
  imports: [SharedModule, JoursFeriesRoutingModule],
  declarations: [JoursFeriesComponent, JoursFeriesDetailComponent, JoursFeriesUpdateComponent, JoursFeriesDeleteDialogComponent],
})
export class ReferentielmsJoursFeriesModule {}
