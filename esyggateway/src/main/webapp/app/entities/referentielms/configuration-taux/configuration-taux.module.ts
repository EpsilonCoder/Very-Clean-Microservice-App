import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ConfigurationTauxComponent } from './list/configuration-taux.component';
import { ConfigurationTauxDetailComponent } from './detail/configuration-taux-detail.component';
import { ConfigurationTauxUpdateComponent } from './update/configuration-taux-update.component';
import { ConfigurationTauxDeleteDialogComponent } from './delete/configuration-taux-delete-dialog.component';
import { ConfigurationTauxRoutingModule } from './route/configuration-taux-routing.module';

@NgModule({
  imports: [SharedModule, ConfigurationTauxRoutingModule],
  declarations: [
    ConfigurationTauxComponent,
    ConfigurationTauxDetailComponent,
    ConfigurationTauxUpdateComponent,
    ConfigurationTauxDeleteDialogComponent,
  ],
})
export class ReferentielmsConfigurationTauxModule {}
