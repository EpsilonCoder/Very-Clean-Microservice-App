import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SourcesFinancementComponent } from './list/sources-financement.component';
import { SourcesFinancementDetailComponent } from './detail/sources-financement-detail.component';
import { SourcesFinancementUpdateComponent } from './update/sources-financement-update.component';
import { SourcesFinancementDeleteDialogComponent } from './delete/sources-financement-delete-dialog.component';
import { SourcesFinancementRoutingModule } from './route/sources-financement-routing.module';

@NgModule({
  imports: [SharedModule, SourcesFinancementRoutingModule],
  declarations: [
    SourcesFinancementComponent,
    SourcesFinancementDetailComponent,
    SourcesFinancementUpdateComponent,
    SourcesFinancementDeleteDialogComponent,
  ],
})
export class ReferentielmsSourcesFinancementModule {}
