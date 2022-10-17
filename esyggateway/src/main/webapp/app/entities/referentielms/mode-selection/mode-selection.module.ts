import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ModeSelectionComponent } from './list/mode-selection.component';
import { ModeSelectionDetailComponent } from './detail/mode-selection-detail.component';
import { ModeSelectionUpdateComponent } from './update/mode-selection-update.component';
import { ModeSelectionDeleteDialogComponent } from './delete/mode-selection-delete-dialog.component';
import { ModeSelectionRoutingModule } from './route/mode-selection-routing.module';

@NgModule({
  imports: [SharedModule, ModeSelectionRoutingModule],
  declarations: [ModeSelectionComponent, ModeSelectionDetailComponent, ModeSelectionUpdateComponent, ModeSelectionDeleteDialogComponent],
})
export class ReferentielmsModeSelectionModule {}
