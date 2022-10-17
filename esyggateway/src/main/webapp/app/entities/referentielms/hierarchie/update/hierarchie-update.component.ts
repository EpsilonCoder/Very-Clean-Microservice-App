import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { HierarchieFormService, HierarchieFormGroup } from './hierarchie-form.service';
import { IHierarchie } from '../hierarchie.model';
import { HierarchieService } from '../service/hierarchie.service';

@Component({
  selector: 'jhi-hierarchie-update',
  templateUrl: './hierarchie-update.component.html',
})
export class HierarchieUpdateComponent implements OnInit {
  isSaving = false;
  hierarchie: IHierarchie | null = null;

  editForm: HierarchieFormGroup = this.hierarchieFormService.createHierarchieFormGroup();

  constructor(
    protected hierarchieService: HierarchieService,
    protected hierarchieFormService: HierarchieFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hierarchie }) => {
      this.hierarchie = hierarchie;
      if (hierarchie) {
        this.updateForm(hierarchie);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hierarchie = this.hierarchieFormService.getHierarchie(this.editForm);
    if (hierarchie.id !== null) {
      this.subscribeToSaveResponse(this.hierarchieService.update(hierarchie));
    } else {
      this.subscribeToSaveResponse(this.hierarchieService.create(hierarchie));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHierarchie>>): void {
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

  protected updateForm(hierarchie: IHierarchie): void {
    this.hierarchie = hierarchie;
    this.hierarchieFormService.resetForm(this.editForm, hierarchie);
  }
}
