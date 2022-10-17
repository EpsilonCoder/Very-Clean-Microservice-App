import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDirection } from '../direction.model';
import { DirectionService } from '../service/direction.service';

@Injectable({ providedIn: 'root' })
export class DirectionRoutingResolveService implements Resolve<IDirection | null> {
  constructor(protected service: DirectionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDirection | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((direction: HttpResponse<IDirection>) => {
          if (direction.body) {
            return of(direction.body);
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
