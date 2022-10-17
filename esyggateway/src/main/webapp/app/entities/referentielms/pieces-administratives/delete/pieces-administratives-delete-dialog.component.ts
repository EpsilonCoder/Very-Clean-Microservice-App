import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPiecesAdministratives } from '../pieces-administratives.model';
import { PiecesAdministrativesService } from '../service/pieces-administratives.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './pieces-administratives-delete-dialog.component.html',
})
export class PiecesAdministrativesDeleteDialogComponent {
  piecesAdministratives?: IPiecesAdministratives;

  constructor(protected piecesAdministrativesService: PiecesAdministrativesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.piecesAdministrativesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
