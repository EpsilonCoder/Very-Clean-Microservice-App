import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDelais } from '../delais.model';
import { DelaisService } from '../service/delais.service';

@Injectable({ providedIn: 'root' })
export class DelaisRoutingResolveService implements Resolve<IDelais | null> {
  constructor(protected service: DelaisService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDelais | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((delais: HttpResponse<IDelais>) => {
          if (delais.body) {
            return of(delais.body);
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
