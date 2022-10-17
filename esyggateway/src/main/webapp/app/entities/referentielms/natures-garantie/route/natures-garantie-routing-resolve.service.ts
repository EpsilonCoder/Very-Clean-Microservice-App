import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INaturesGarantie } from '../natures-garantie.model';
import { NaturesGarantieService } from '../service/natures-garantie.service';

@Injectable({ providedIn: 'root' })
export class NaturesGarantieRoutingResolveService implements Resolve<INaturesGarantie | null> {
  constructor(protected service: NaturesGarantieService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INaturesGarantie | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((naturesGarantie: HttpResponse<INaturesGarantie>) => {
          if (naturesGarantie.body) {
            return of(naturesGarantie.body);
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
