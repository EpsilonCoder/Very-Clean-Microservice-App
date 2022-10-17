import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISourcesFinancement } from '../sources-financement.model';

@Component({
  selector: 'jhi-sources-financement-detail',
  templateUrl: './sources-financement-detail.component.html',
})
export class SourcesFinancementDetailComponent implements OnInit {
  sourcesFinancement: ISourcesFinancement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sourcesFinancement }) => {
      this.sourcesFinancement = sourcesFinancement;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
