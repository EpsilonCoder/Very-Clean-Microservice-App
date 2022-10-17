import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAvisGeneraux } from '../avis-generaux.model';
import { AvisGenerauxService } from '../service/avis-generaux.service';

@Injectable({ providedIn: 'root' })
export class AvisGenerauxRoutingResolveService implements Resolve<IAvisGeneraux | null> {
  constructor(protected service: AvisGenerauxService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAvisGeneraux | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((avisGeneraux: HttpResponse<IAvisGeneraux>) => {
          if (avisGeneraux.body) {
            return of(avisGeneraux.body);
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
