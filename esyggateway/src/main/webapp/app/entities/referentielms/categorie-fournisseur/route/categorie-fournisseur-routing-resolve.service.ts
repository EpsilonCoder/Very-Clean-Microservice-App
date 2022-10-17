import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategorieFournisseur } from '../categorie-fournisseur.model';
import { CategorieFournisseurService } from '../service/categorie-fournisseur.service';

@Injectable({ providedIn: 'root' })
export class CategorieFournisseurRoutingResolveService implements Resolve<ICategorieFournisseur | null> {
  constructor(protected service: CategorieFournisseurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategorieFournisseur | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((categorieFournisseur: HttpResponse<ICategorieFournisseur>) => {
          if (categorieFournisseur.body) {
            return of(categorieFournisseur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
