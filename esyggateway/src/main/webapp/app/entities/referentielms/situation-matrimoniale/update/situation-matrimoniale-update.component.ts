import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { SituationMatrimonialeFormService, SituationMatrimonialeFormGroup } from './situation-matrimoniale-form.service';
import { ISituationMatrimoniale } from '../situation-matrimoniale.model';
import { SituationMatrimonialeService } from '../service/situation-matrimoniale.service';

@Component({
  selector: 'jhi-situation-matrimoniale-update',
  templateUrl: './situation-matrimoniale-update.component.html',
})
export class SituationMatrimonialeUpdateComponent implements OnInit {
  isSaving = false;
  situationMatrimoniale: ISituationMatrimoniale | null = null;

  editForm: SituationMatrimonialeFormGroup = this.situationMatrimonialeFormService.createSituationMatrimonialeFormGroup();

  constructor(
    protected situationMatrimonialeService: SituationMatrimonialeService,
    protected situationMatrimonialeFormService: SituationMatrimonialeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ situationMatrimoniale }) => {
      this.situationMatrimoniale = situationMatrimoniale;
      if (situationMatrimoniale) {
        this.updateForm(situationMatrimoniale);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const situationMatrimoniale = this.situationMatrimonialeFormService.getSituationMatrimoniale(this.editForm);
    if (situationMatrimoniale.id !== null) {
      this.subscribeToSaveResponse(this.situationMatrimonialeService.update(situationMatrimoniale));
    } else {
      this.subscribeToSaveResponse(this.situationMatrimonialeService.create(situationMatrimoniale));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISituationMatrimoniale>>): void {
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

  protected updateForm(situationMatrimoniale: ISituationMatrimoniale): void {
    this.situationMatrimoniale = situationMatrimoniale;
    this.situationMatrimonialeFormService.resetForm(this.editForm, situationMatrimoniale);
  }
}
