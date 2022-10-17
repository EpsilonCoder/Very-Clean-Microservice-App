import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICriteresQualification } from '../criteres-qualification.model';
import { CriteresQualificationService } from '../service/criteres-qualification.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './criteres-qualification-delete-dialog.component.html',
})
export class CriteresQualificationDeleteDialogComponent {
  criteresQualification?: ICriteresQualification;

  constructor(protected criteresQualificationService: CriteresQualificationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.criteresQualificationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
