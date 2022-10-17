import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConfigurationTaux } from '../configuration-taux.model';

@Component({
  selector: 'jhi-configuration-taux-detail',
  templateUrl: './configuration-taux-detail.component.html',
})
export class ConfigurationTauxDetailComponent implements OnInit {
  configurationTaux: IConfigurationTaux | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ configurationTaux }) => {
      this.configurationTaux = configurationTaux;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
