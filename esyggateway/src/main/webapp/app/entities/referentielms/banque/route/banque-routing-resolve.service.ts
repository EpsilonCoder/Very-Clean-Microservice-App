import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBanque } from '../banque.model';
import { BanqueService } from '../service/banque.service';

@Injectable({ providedIn: 'root' })
export class BanqueRoutingResolveService implements Resolve<IBanque | null> {
  constructor(protected service: BanqueService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBanque | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((banque: HttpResponse<IBanque>) => {
          if (banque.body) {
            return of(banque.body);
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
