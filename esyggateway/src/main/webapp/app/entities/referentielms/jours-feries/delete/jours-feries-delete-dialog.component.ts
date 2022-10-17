import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IJoursFeries } from '../jours-feries.model';
import { JoursFeriesService } from '../service/jours-feries.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './jours-feries-delete-dialog.component.html',
})
export class JoursFeriesDeleteDialogComponent {
  joursFeries?: IJoursFeries;

  constructor(protected joursFeriesService: JoursFeriesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.joursFeriesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
