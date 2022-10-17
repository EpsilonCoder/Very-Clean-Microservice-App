import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonnesRessources } from '../personnes-ressources.model';

@Component({
  selector: 'jhi-personnes-ressources-detail',
  templateUrl: './personnes-ressources-detail.component.html',
})
export class PersonnesRessourcesDetailComponent implements OnInit {
  personnesRessources: IPersonnesRessources | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personnesRessources }) => {
      this.personnesRessources = personnesRessources;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
