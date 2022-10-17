import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModeSelection } from '../mode-selection.model';

@Component({
  selector: 'jhi-mode-selection-detail',
  templateUrl: './mode-selection-detail.component.html',
})
export class ModeSelectionDetailComponent implements OnInit {
  modeSelection: IModeSelection | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modeSelection }) => {
      this.modeSelection = modeSelection;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
