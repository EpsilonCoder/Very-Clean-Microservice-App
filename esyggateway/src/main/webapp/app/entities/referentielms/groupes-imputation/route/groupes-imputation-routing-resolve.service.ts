import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGroupesImputation } from '../groupes-imputation.model';
import { GroupesImputationService } from '../service/groupes-imputation.service';

@Injectable({ providedIn: 'root' })
export class GroupesImputationRoutingResolveService implements Resolve<IGroupesImputation | null> {
  constructor(protected service: GroupesImputationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGroupesImputation | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((groupesImputation: HttpResponse<IGroupesImputation>) => {
          if (groupesImputation.body) {
            return of(groupesImputation.body);
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
