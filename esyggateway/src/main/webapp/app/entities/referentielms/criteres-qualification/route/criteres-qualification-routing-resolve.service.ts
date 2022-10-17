import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICriteresQualification } from '../criteres-qualification.model';
import { CriteresQualificationService } from '../service/criteres-qualification.service';

@Injectable({ providedIn: 'root' })
export class CriteresQualificationRoutingResolveService implements Resolve<ICriteresQualification | null> {
  constructor(protected service: CriteresQualificationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICriteresQualification | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((criteresQualification: HttpResponse<ICriteresQualification>) => {
          if (criteresQualification.body) {
            return of(criteresQualification.body);
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
