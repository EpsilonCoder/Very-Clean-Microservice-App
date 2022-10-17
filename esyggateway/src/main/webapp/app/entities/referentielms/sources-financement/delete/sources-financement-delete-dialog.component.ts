import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISourcesFinancement } from '../sources-financement.model';
import { SourcesFinancementService } from '../service/sources-financement.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './sources-financement-delete-dialog.component.html',
})
export class SourcesFinancementDeleteDialogComponent {
  sourcesFinancement?: ISourcesFinancement;

  constructor(protected sourcesFinancementService: SourcesFinancementService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sourcesFinancementService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
