import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHierarchie } from '../hierarchie.model';
import { HierarchieService } from '../service/hierarchie.service';

@Injectable({ providedIn: 'root' })
export class HierarchieRoutingResolveService implements Resolve<IHierarchie | null> {
  constructor(protected service: HierarchieService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHierarchie | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((hierarchie: HttpResponse<IHierarchie>) => {
          if (hierarchie.body) {
            return of(hierarchie.body);
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
