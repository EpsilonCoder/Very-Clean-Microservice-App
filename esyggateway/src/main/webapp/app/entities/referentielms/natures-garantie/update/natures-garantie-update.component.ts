import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { NaturesGarantieFormService, NaturesGarantieFormGroup } from './natures-garantie-form.service';
import { INaturesGarantie } from '../natures-garantie.model';
import { NaturesGarantieService } from '../service/natures-garantie.service';

@Component({
  selector: 'jhi-natures-garantie-update',
  templateUrl: './natures-garantie-update.component.html',
})
export class NaturesGarantieUpdateComponent implements OnInit {
  isSaving = false;
  naturesGarantie: INaturesGarantie | null = null;

  editForm: NaturesGarantieFormGroup = this.naturesGarantieFormService.createNaturesGarantieFormGroup();

  constructor(
    protected naturesGarantieService: NaturesGarantieService,
    protected naturesGarantieFormService: NaturesGarantieFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ naturesGarantie }) => {
      this.naturesGarantie = naturesGarantie;
      if (naturesGarantie) {
        this.updateForm(naturesGarantie);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const naturesGarantie = this.naturesGarantieFormService.getNaturesGarantie(this.editForm);
    if (naturesGarantie.id !== null) {
      this.subscribeToSaveResponse(this.naturesGarantieService.update(naturesGarantie));
    } else {
      this.subscribeToSaveResponse(this.naturesGarantieService.create(naturesGarantie));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INaturesGarantie>>): void {
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

  protected updateForm(naturesGarantie: INaturesGarantie): void {
    this.naturesGarantie = naturesGarantie;
    this.naturesGarantieFormService.resetForm(this.editForm, naturesGarantie);
  }
}
