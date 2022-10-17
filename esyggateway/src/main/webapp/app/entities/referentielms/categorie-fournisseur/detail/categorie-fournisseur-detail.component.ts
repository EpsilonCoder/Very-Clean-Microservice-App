import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategorieFournisseur } from '../categorie-fournisseur.model';

@Component({
  selector: 'jhi-categorie-fournisseur-detail',
  templateUrl: './categorie-fournisseur-detail.component.html',
})
export class CategorieFournisseurDetailComponent implements OnInit {
  categorieFournisseur: ICategorieFournisseur | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieFournisseur }) => {
      this.categorieFournisseur = categorieFournisseur;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
