import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FournisseurFormService, FournisseurFormGroup } from './fournisseur-form.service';
import { IFournisseur } from '../fournisseur.model';
import { FournisseurService } from '../service/fournisseur.service';
import { ICategorieFournisseur } from 'app/entities/referentielms/categorie-fournisseur/categorie-fournisseur.model';
import { CategorieFournisseurService } from 'app/entities/referentielms/categorie-fournisseur/service/categorie-fournisseur.service';
import { IPays } from 'app/entities/referentielms/pays/pays.model';
import { PaysService } from 'app/entities/referentielms/pays/service/pays.service';

@Component({
  selector: 'jhi-fournisseur-update',
  templateUrl: './fournisseur-update.component.html',
})
export class FournisseurUpdateComponent implements OnInit {
  isSaving = false;
  fournisseur: IFournisseur | null = null;

  categorieFournisseursSharedCollection: ICategorieFournisseur[] = [];
  paysSharedCollection: IPays[] = [];

  editForm: FournisseurFormGroup = this.fournisseurFormService.createFournisseurFormGroup();

  constructor(
    protected fournisseurService: FournisseurService,
    protected fournisseurFormService: FournisseurFormService,
    protected categorieFournisseurService: CategorieFournisseurService,
    protected paysService: PaysService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCategorieFournisseur = (o1: ICategorieFournisseur | null, o2: ICategorieFournisseur | null): boolean =>
    this.categorieFournisseurService.compareCategorieFournisseur(o1, o2);

  comparePays = (o1: IPays | null, o2: IPays | null): boolean => this.paysService.comparePays(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fournisseur }) => {
      this.fournisseur = fournisseur;
      if (fournisseur) {
        this.updateForm(fournisseur);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fournisseur = this.fournisseurFormService.getFournisseur(this.editForm);
    if (fournisseur.id !== null) {
      this.subscribeToSaveResponse(this.fournisseurService.update(fournisseur));
    } else {
      this.subscribeToSaveResponse(this.fournisseurService.create(fournisseur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFournisseur>>): void {
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

  protected updateForm(fournisseur: IFournisseur): void {
    this.fournisseur = fournisseur;
    this.fournisseurFormService.resetForm(this.editForm, fournisseur);

    this.categorieFournisseursSharedCollection =
      this.categorieFournisseurService.addCategorieFournisseurToCollectionIfMissing<ICategorieFournisseur>(
        this.categorieFournisseursSharedCollection,
        fournisseur.categorieFournisseur
      );
    this.paysSharedCollection = this.paysService.addPaysToCollectionIfMissing<IPays>(this.paysSharedCollection, fournisseur.pays);
  }

  protected loadRelationshipsOptions(): void {
    this.categorieFournisseurService
      .query()
      .pipe(map((res: HttpResponse<ICategorieFournisseur[]>) => res.body ?? []))
      .pipe(
        map((categorieFournisseurs: ICategorieFournisseur[]) =>
          this.categorieFournisseurService.addCategorieFournisseurToCollectionIfMissing<ICategorieFournisseur>(
            categorieFournisseurs,
            this.fournisseur?.categorieFournisseur
          )
        )
      )
      .subscribe((categorieFournisseurs: ICategorieFournisseur[]) => (this.categorieFournisseursSharedCollection = categorieFournisseurs));

    this.paysService
      .query()
      .pipe(map((res: HttpResponse<IPays[]>) => res.body ?? []))
      .pipe(map((pays: IPays[]) => this.paysService.addPaysToCollectionIfMissing<IPays>(pays, this.fournisseur?.pays)))
      .subscribe((pays: IPays[]) => (this.paysSharedCollection = pays));
  }
}
