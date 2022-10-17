import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PiecesAdministrativesFormService, PiecesAdministrativesFormGroup } from './pieces-administratives-form.service';
import { IPiecesAdministratives } from '../pieces-administratives.model';
import { PiecesAdministrativesService } from '../service/pieces-administratives.service';
import { enumLocalisation } from 'app/entities/enumerations/enum-localisation.model';

@Component({
  selector: 'jhi-pieces-administratives-update',
  templateUrl: './pieces-administratives-update.component.html',
})
export class PiecesAdministrativesUpdateComponent implements OnInit {
  isSaving = false;
  piecesAdministratives: IPiecesAdministratives | null = null;
  enumLocalisationValues = Object.keys(enumLocalisation);

  editForm: PiecesAdministrativesFormGroup = this.piecesAdministrativesFormService.createPiecesAdministrativesFormGroup();

  constructor(
    protected piecesAdministrativesService: PiecesAdministrativesService,
    protected piecesAdministrativesFormService: PiecesAdministrativesFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ piecesAdministratives }) => {
      this.piecesAdministratives = piecesAdministratives;
      if (piecesAdministratives) {
        this.updateForm(piecesAdministratives);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const piecesAdministratives = this.piecesAdministrativesFormService.getPiecesAdministratives(this.editForm);
    if (piecesAdministratives.id !== null) {
      this.subscribeToSaveResponse(this.piecesAdministrativesService.update(piecesAdministratives));
    } else {
      this.subscribeToSaveResponse(this.piecesAdministrativesService.create(piecesAdministratives));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPiecesAdministratives>>): void {
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

  protected updateForm(piecesAdministratives: IPiecesAdministratives): void {
    this.piecesAdministratives = piecesAdministratives;
    this.piecesAdministrativesFormService.resetForm(this.editForm, piecesAdministratives);
  }
}
