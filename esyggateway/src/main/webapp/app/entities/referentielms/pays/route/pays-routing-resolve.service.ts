import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPays } from '../pays.model';
import { PaysService } from '../service/pays.service';

@Injectable({ providedIn: 'root' })
export class PaysRoutingResolveService implements Resolve<IPays | null> {
  constructor(protected service: PaysService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPays | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pays: HttpResponse<IPays>) => {
          if (pays.body) {
            return of(pays.body);
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
