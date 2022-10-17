import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPiecesAdministratives } from '../pieces-administratives.model';
import { PiecesAdministrativesService } from '../service/pieces-administratives.service';

@Injectable({ providedIn: 'root' })
export class PiecesAdministrativesRoutingResolveService implements Resolve<IPiecesAdministratives | null> {
  constructor(protected service: PiecesAdministrativesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPiecesAdministratives | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((piecesAdministratives: HttpResponse<IPiecesAdministratives>) => {
          if (piecesAdministratives.body) {
            return of(piecesAdministratives.body);
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
