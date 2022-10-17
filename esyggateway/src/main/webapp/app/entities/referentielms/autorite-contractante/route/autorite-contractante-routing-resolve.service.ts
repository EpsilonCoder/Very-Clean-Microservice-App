import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAutoriteContractante } from '../autorite-contractante.model';
import { AutoriteContractanteService } from '../service/autorite-contractante.service';

@Injectable({ providedIn: 'root' })
export class AutoriteContractanteRoutingResolveService implements Resolve<IAutoriteContractante | null> {
  constructor(protected service: AutoriteContractanteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAutoriteContractante | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((autoriteContractante: HttpResponse<IAutoriteContractante>) => {
          if (autoriteContractante.body) {
            return of(autoriteContractante.body);
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
