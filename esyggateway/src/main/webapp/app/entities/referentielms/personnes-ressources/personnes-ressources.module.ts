import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PersonnesRessourcesComponent } from './list/personnes-ressources.component';
import { PersonnesRessourcesDetailComponent } from './detail/personnes-ressources-detail.component';
import { PersonnesRessourcesUpdateComponent } from './update/personnes-ressources-update.component';
import { PersonnesRessourcesDeleteDialogComponent } from './delete/personnes-ressources-delete-dialog.component';
import { PersonnesRessourcesRoutingModule } from './route/personnes-ressources-routing.module';

@NgModule({
  imports: [SharedModule, PersonnesRessourcesRoutingModule],
  declarations: [
    PersonnesRessourcesComponent,
    PersonnesRessourcesDetailComponent,
    PersonnesRessourcesUpdateComponent,
    PersonnesRessourcesDeleteDialogComponent,
  ],
})
export class ReferentielmsPersonnesRessourcesModule {}
