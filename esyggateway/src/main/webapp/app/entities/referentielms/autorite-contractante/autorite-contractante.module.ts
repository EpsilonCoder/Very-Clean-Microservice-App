import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AutoriteContractanteComponent } from './list/autorite-contractante.component';
import { AutoriteContractanteDetailComponent } from './detail/autorite-contractante-detail.component';
import { AutoriteContractanteRoutingModule } from './route/autorite-contractante-routing.module';

@NgModule({
  imports: [SharedModule, AutoriteContractanteRoutingModule],
  declarations: [AutoriteContractanteComponent, AutoriteContractanteDetailComponent],
})
export class ReferentielmsAutoriteContractanteModule {}
