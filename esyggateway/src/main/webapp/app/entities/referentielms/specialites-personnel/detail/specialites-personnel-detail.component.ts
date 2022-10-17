import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpecialitesPersonnel } from '../specialites-personnel.model';

@Component({
  selector: 'jhi-specialites-personnel-detail',
  templateUrl: './specialites-personnel-detail.component.html',
})
export class SpecialitesPersonnelDetailComponent implements OnInit {
  specialitesPersonnel: ISpecialitesPersonnel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ specialitesPersonnel }) => {
      this.specialitesPersonnel = specialitesPersonnel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
