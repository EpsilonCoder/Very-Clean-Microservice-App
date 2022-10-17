import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITypesMarches } from '../types-marches.model';
import { TypesMarchesService } from '../service/types-marches.service';

@Injectable({ providedIn: 'root' })
export class TypesMarchesRoutingResolveService implements Resolve<ITypesMarches | null> {
  constructor(protected service: TypesMarchesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypesMarches | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((typesMarches: HttpResponse<ITypesMarches>) => {
          if (typesMarches.body) {
            return of(typesMarches.body);
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
