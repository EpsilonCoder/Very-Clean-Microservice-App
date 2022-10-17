import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SygAutoriteContractanteComponent } from './list/syg-autorite-contractante.component';
import { SygAutoriteContractanteDetailComponent } from './detail/syg-autorite-contractante-detail.component';
import { SygAutoriteContractanteUpdateComponent } from './update/syg-autorite-contractante-update.component';
import { SygAutoriteContractanteDeleteDialogComponent } from './delete/syg-autorite-contractante-delete-dialog.component';
import { SygAutoriteContractanteRoutingModule } from './route/syg-autorite-contractante-routing.module';

@NgModule({
  imports: [SharedModule, SygAutoriteContractanteRoutingModule],
  declarations: [
    SygAutoriteContractanteComponent,
    SygAutoriteContractanteDetailComponent,
    SygAutoriteContractanteUpdateComponent,
    SygAutoriteContractanteDeleteDialogComponent,
  ],
})
export class ReferentielmsSygAutoriteContractanteModule {}
