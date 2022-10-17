import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AvisGenerauxComponent } from './list/avis-generaux.component';
import { AvisGenerauxDetailComponent } from './detail/avis-generaux-detail.component';
import { AvisGenerauxUpdateComponent } from './update/avis-generaux-update.component';
import { AvisGenerauxDeleteDialogComponent } from './delete/avis-generaux-delete-dialog.component';
import { AvisGenerauxRoutingModule } from './route/avis-generaux-routing.module';

@NgModule({
  imports: [SharedModule, AvisGenerauxRoutingModule],
  declarations: [AvisGenerauxComponent, AvisGenerauxDetailComponent, AvisGenerauxUpdateComponent, AvisGenerauxDeleteDialogComponent],
})
export class ReferentielmsAvisGenerauxModule {}
