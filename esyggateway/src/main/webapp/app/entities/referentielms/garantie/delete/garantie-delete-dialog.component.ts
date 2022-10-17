import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGarantie } from '../garantie.model';
import { GarantieService } from '../service/garantie.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './garantie-delete-dialog.component.html',
})
export class GarantieDeleteDialogComponent {
  garantie?: IGarantie;

  constructor(protected garantieService: GarantieService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.garantieService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
