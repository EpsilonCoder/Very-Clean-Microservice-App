import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAvisGeneraux } from '../avis-generaux.model';
import { AvisGenerauxService } from '../service/avis-generaux.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './avis-generaux-delete-dialog.component.html',
})
export class AvisGenerauxDeleteDialogComponent {
  avisGeneraux?: IAvisGeneraux;

  constructor(protected avisGenerauxService: AvisGenerauxService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avisGenerauxService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
