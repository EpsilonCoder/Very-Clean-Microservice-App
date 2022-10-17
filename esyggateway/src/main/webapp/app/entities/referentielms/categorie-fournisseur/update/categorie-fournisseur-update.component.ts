import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CategorieFournisseurFormService, CategorieFournisseurFormGroup } from './categorie-fournisseur-form.service';
import { ICategorieFournisseur } from '../categorie-fournisseur.model';
import { CategorieFournisseurService } from '../service/categorie-fournisseur.service';

@Component({
  selector: 'jhi-categorie-fournisseur-update',
  templateUrl: './categorie-fournisseur-update.component.html',
})
export class CategorieFournisseurUpdateComponent implements OnInit {
  isSaving = false;
  categorieFournisseur: ICategorieFournisseur | null = null;

  editForm: CategorieFournisseurFormGroup = this.categorieFournisseurFormService.createCategorieFournisseurFormGroup();

  constructor(
    protected categorieFournisseurService: CategorieFournisseurService,
    protected categorieFournisseurFormService: CategorieFournisseurFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieFournisseur }) => {
      this.categorieFournisseur = categorieFournisseur;
      if (categorieFournisseur) {
        this.updateForm(categorieFournisseur);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categorieFournisseur = this.categorieFournisseurFormService.getCategorieFournisseur(this.editForm);
    if (categorieFournisseur.id !== null) {
      this.subscribeToSaveResponse(this.categorieFournisseurService.update(categorieFournisseur));
    } else {
      this.subscribeToSaveResponse(this.categorieFournisseurService.create(categorieFournisseur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorieFournisseur>>): void {
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

  protected updateForm(categorieFournisseur: ICategorieFournisseur): void {
    this.categorieFournisseur = categorieFournisseur;
    this.categorieFournisseurFormService.resetForm(this.editForm, categorieFournisseur);
  }
}
