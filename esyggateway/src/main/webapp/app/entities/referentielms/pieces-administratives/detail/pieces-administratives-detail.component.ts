import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPiecesAdministratives } from '../pieces-administratives.model';

@Component({
  selector: 'jhi-pieces-administratives-detail',
  templateUrl: './pieces-administratives-detail.component.html',
})
export class PiecesAdministrativesDetailComponent implements OnInit {
  piecesAdministratives: IPiecesAdministratives | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ piecesAdministratives }) => {
      this.piecesAdministratives = piecesAdministratives;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
