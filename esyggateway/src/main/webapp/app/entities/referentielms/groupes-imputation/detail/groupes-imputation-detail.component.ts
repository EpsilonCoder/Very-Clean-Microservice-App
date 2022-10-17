import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGroupesImputation } from '../groupes-imputation.model';

@Component({
  selector: 'jhi-groupes-imputation-detail',
  templateUrl: './groupes-imputation-detail.component.html',
})
export class GroupesImputationDetailComponent implements OnInit {
  groupesImputation: IGroupesImputation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ groupesImputation }) => {
      this.groupesImputation = groupesImputation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
