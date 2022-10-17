import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IModeSelection } from '../mode-selection.model';
import { ModeSelectionService } from '../service/mode-selection.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './mode-selection-delete-dialog.component.html',
})
export class ModeSelectionDeleteDialogComponent {
  modeSelection?: IModeSelection;

  constructor(protected modeSelectionService: ModeSelectionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.modeSelectionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
