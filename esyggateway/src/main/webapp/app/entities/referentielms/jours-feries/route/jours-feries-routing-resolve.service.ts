import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IJoursFeries } from '../jours-feries.model';
import { JoursFeriesService } from '../service/jours-feries.service';

@Injectable({ providedIn: 'root' })
export class JoursFeriesRoutingResolveService implements Resolve<IJoursFeries | null> {
  constructor(protected service: JoursFeriesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJoursFeries | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((joursFeries: HttpResponse<IJoursFeries>) => {
          if (joursFeries.body) {
            return of(joursFeries.body);
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
