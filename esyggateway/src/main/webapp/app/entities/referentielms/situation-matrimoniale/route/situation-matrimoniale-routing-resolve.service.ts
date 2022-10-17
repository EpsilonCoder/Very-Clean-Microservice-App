import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISituationMatrimoniale } from '../situation-matrimoniale.model';
import { SituationMatrimonialeService } from '../service/situation-matrimoniale.service';

@Injectable({ providedIn: 'root' })
export class SituationMatrimonialeRoutingResolveService implements Resolve<ISituationMatrimoniale | null> {
  constructor(protected service: SituationMatrimonialeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISituationMatrimoniale | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((situationMatrimoniale: HttpResponse<ISituationMatrimoniale>) => {
          if (situationMatrimoniale.body) {
            return of(situationMatrimoniale.body);
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
