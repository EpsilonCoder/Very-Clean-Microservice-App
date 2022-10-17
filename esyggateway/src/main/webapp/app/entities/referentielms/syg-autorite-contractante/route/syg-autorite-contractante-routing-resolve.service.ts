import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISygAutoriteContractante } from '../syg-autorite-contractante.model';
import { SygAutoriteContractanteService } from '../service/syg-autorite-contractante.service';

@Injectable({ providedIn: 'root' })
export class SygAutoriteContractanteRoutingResolveService implements Resolve<ISygAutoriteContractante | null> {
  constructor(protected service: SygAutoriteContractanteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISygAutoriteContractante | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sygAutoriteContractante: HttpResponse<ISygAutoriteContractante>) => {
          if (sygAutoriteContractante.body) {
            return of(sygAutoriteContractante.body);
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
