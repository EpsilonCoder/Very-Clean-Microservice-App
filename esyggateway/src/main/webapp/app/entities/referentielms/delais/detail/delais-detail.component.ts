import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDelais } from '../delais.model';

@Component({
  selector: 'jhi-delais-detail',
  templateUrl: './delais-detail.component.html',
})
export class DelaisDetailComponent implements OnInit {
  delais: IDelais | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ delais }) => {
      this.delais = delais;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
