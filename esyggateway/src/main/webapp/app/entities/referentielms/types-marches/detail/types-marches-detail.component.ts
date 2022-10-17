import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypesMarches } from '../types-marches.model';

@Component({
  selector: 'jhi-types-marches-detail',
  templateUrl: './types-marches-detail.component.html',
})
export class TypesMarchesDetailComponent implements OnInit {
  typesMarches: ITypesMarches | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typesMarches }) => {
      this.typesMarches = typesMarches;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
