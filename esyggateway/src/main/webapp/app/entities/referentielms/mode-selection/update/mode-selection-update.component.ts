import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ModeSelectionFormService, ModeSelectionFormGroup } from './mode-selection-form.service';
import { IModeSelection } from '../mode-selection.model';
import { ModeSelectionService } from '../service/mode-selection.service';

@Component({
  selector: 'jhi-mode-selection-update',
  templateUrl: './mode-selection-update.component.html',
})
export class ModeSelectionUpdateComponent implements OnInit {
  isSaving = false;
  modeSelection: IModeSelection | null = null;

  editForm: ModeSelectionFormGroup = this.modeSelectionFormService.createModeSelectionFormGroup();

  constructor(
    protected modeSelectionService: ModeSelectionService,
    protected modeSelectionFormService: ModeSelectionFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modeSelection }) => {
      this.modeSelection = modeSelection;
      if (modeSelection) {
        this.updateForm(modeSelection);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const modeSelection = this.modeSelectionFormService.getModeSelection(this.editForm);
    if (modeSelection.id !== null) {
      this.subscribeToSaveResponse(this.modeSelectionService.update(modeSelection));
    } else {
      this.subscribeToSaveResponse(this.modeSelectionService.create(modeSelection));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModeSelection>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(modeSelection: IModeSelection): void {
    this.modeSelection = modeSelection;
    this.modeSelectionFormService.resetForm(this.editForm, modeSelection);
  }
}
