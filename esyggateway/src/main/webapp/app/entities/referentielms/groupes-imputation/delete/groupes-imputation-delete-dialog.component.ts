import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGroupesImputation } from '../groupes-imputation.model';
import { GroupesImputationService } from '../service/groupes-imputation.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './groupes-imputation-delete-dialog.component.html',
})
export class GroupesImputationDeleteDialogComponent {
  groupesImputation?: IGroupesImputation;

  constructor(protected groupesImputationService: GroupesImputationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.groupesImputationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
