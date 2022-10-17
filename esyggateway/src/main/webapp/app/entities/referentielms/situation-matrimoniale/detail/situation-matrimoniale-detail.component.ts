import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISituationMatrimoniale } from '../situation-matrimoniale.model';

@Component({
  selector: 'jhi-situation-matrimoniale-detail',
  templateUrl: './situation-matrimoniale-detail.component.html',
})
export class SituationMatrimonialeDetailComponent implements OnInit {
  situationMatrimoniale: ISituationMatrimoniale | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ situationMatrimoniale }) => {
      this.situationMatrimoniale = situationMatrimoniale;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
