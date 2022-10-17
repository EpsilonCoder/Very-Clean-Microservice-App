import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICriteresQualification } from '../criteres-qualification.model';

@Component({
  selector: 'jhi-criteres-qualification-detail',
  templateUrl: './criteres-qualification-detail.component.html',
})
export class CriteresQualificationDetailComponent implements OnInit {
  criteresQualification: ICriteresQualification | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ criteresQualification }) => {
      this.criteresQualification = criteresQualification;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
