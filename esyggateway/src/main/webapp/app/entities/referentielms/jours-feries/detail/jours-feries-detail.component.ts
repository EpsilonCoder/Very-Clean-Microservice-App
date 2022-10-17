import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJoursFeries } from '../jours-feries.model';

@Component({
  selector: 'jhi-jours-feries-detail',
  templateUrl: './jours-feries-detail.component.html',
})
export class JoursFeriesDetailComponent implements OnInit {
  joursFeries: IJoursFeries | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ joursFeries }) => {
      this.joursFeries = joursFeries;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
