import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INaturesGarantie } from '../natures-garantie.model';

@Component({
  selector: 'jhi-natures-garantie-detail',
  templateUrl: './natures-garantie-detail.component.html',
})
export class NaturesGarantieDetailComponent implements OnInit {
  naturesGarantie: INaturesGarantie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ naturesGarantie }) => {
      this.naturesGarantie = naturesGarantie;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
