import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INaturesGarantie } from '../natures-garantie.model';
import { NaturesGarantieService } from '../service/natures-garantie.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './natures-garantie-delete-dialog.component.html',
})
export class NaturesGarantieDeleteDialogComponent {
  naturesGarantie?: INaturesGarantie;

  constructor(protected naturesGarantieService: NaturesGarantieService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.naturesGarantieService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
