import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DelaisFormService, DelaisFormGroup } from './delais-form.service';
import { IDelais } from '../delais.model';
import { DelaisService } from '../service/delais.service';

@Component({
  selector: 'jhi-delais-update',
  templateUrl: './delais-update.component.html',
})
export class DelaisUpdateComponent implements OnInit {
  isSaving = false;
  delais: IDelais | null = null;

  editForm: DelaisFormGroup = this.delaisFormService.createDelaisFormGroup();

  constructor(
    protected delaisService: DelaisService,
    protected delaisFormService: DelaisFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ delais }) => {
      this.delais = delais;
      if (delais) {
        this.updateForm(delais);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const delais = this.delaisFormService.getDelais(this.editForm);
    if (delais.id !== null) {
      this.subscribeToSaveResponse(this.delaisService.update(delais));
    } else {
      this.subscribeToSaveResponse(this.delaisService.create(delais));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDelais>>): void {
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

  protected updateForm(delais: IDelais): void {
    this.delais = delais;
    this.delaisFormService.resetForm(this.editForm, delais);
  }
}
