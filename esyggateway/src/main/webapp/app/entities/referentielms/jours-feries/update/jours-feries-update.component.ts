import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { JoursFeriesFormService, JoursFeriesFormGroup } from './jours-feries-form.service';
import { IJoursFeries } from '../jours-feries.model';
import { JoursFeriesService } from '../service/jours-feries.service';

@Component({
  selector: 'jhi-jours-feries-update',
  templateUrl: './jours-feries-update.component.html',
})
export class JoursFeriesUpdateComponent implements OnInit {
  isSaving = false;
  joursFeries: IJoursFeries | null = null;

  editForm: JoursFeriesFormGroup = this.joursFeriesFormService.createJoursFeriesFormGroup();

  constructor(
    protected joursFeriesService: JoursFeriesService,
    protected joursFeriesFormService: JoursFeriesFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ joursFeries }) => {
      this.joursFeries = joursFeries;
      if (joursFeries) {
        this.updateForm(joursFeries);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const joursFeries = this.joursFeriesFormService.getJoursFeries(this.editForm);
    if (joursFeries.id !== null) {
      this.subscribeToSaveResponse(this.joursFeriesService.update(joursFeries));
    } else {
      this.subscribeToSaveResponse(this.joursFeriesService.create(joursFeries));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJoursFeries>>): void {
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

  protected updateForm(joursFeries: IJoursFeries): void {
    this.joursFeries = joursFeries;
    this.joursFeriesFormService.resetForm(this.editForm, joursFeries);
  }
}
