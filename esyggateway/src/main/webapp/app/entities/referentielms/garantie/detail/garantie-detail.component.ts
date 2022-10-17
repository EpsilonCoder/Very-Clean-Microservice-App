import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGarantie } from '../garantie.model';

@Component({
  selector: 'jhi-garantie-detail',
  templateUrl: './garantie-detail.component.html',
})
export class GarantieDetailComponent implements OnInit {
  garantie: IGarantie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ garantie }) => {
      this.garantie = garantie;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
