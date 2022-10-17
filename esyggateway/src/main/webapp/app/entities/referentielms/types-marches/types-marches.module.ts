import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TypesMarchesComponent } from './list/types-marches.component';
import { TypesMarchesDetailComponent } from './detail/types-marches-detail.component';
import { TypesMarchesUpdateComponent } from './update/types-marches-update.component';
import { TypesMarchesDeleteDialogComponent } from './delete/types-marches-delete-dialog.component';
import { TypesMarchesRoutingModule } from './route/types-marches-routing.module';

@NgModule({
  imports: [SharedModule, TypesMarchesRoutingModule],
  declarations: [TypesMarchesComponent, TypesMarchesDetailComponent, TypesMarchesUpdateComponent, TypesMarchesDeleteDialogComponent],
})
export class ReferentielmsTypesMarchesModule {}
