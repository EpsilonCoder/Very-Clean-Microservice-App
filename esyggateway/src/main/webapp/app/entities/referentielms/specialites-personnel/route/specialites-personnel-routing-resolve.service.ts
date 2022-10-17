import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISpecialitesPersonnel } from '../specialites-personnel.model';
import { SpecialitesPersonnelService } from '../service/specialites-personnel.service';

@Injectable({ providedIn: 'root' })
export class SpecialitesPersonnelRoutingResolveService implements Resolve<ISpecialitesPersonnel | null> {
  constructor(protected service: SpecialitesPersonnelService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISpecialitesPersonnel | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((specialitesPersonnel: HttpResponse<ISpecialitesPersonnel>) => {
          if (specialitesPersonnel.body) {
            return of(specialitesPersonnel.body);
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
