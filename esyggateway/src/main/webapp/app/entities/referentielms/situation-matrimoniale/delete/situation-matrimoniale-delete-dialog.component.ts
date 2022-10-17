import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISituationMatrimoniale } from '../situation-matrimoniale.model';
import { SituationMatrimonialeService } from '../service/situation-matrimoniale.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './situation-matrimoniale-delete-dialog.component.html',
})
export class SituationMatrimonialeDeleteDialogComponent {
  situationMatrimoniale?: ISituationMatrimoniale;

  constructor(protected situationMatrimonialeService: SituationMatrimonialeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.situationMatrimonialeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
