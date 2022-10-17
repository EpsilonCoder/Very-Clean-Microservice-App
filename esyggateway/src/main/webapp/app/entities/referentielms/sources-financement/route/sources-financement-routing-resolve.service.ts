import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISourcesFinancement } from '../sources-financement.model';
import { SourcesFinancementService } from '../service/sources-financement.service';

@Injectable({ providedIn: 'root' })
export class SourcesFinancementRoutingResolveService implements Resolve<ISourcesFinancement | null> {
  constructor(protected service: SourcesFinancementService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISourcesFinancement | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sourcesFinancement: HttpResponse<ISourcesFinancement>) => {
          if (sourcesFinancement.body) {
            return of(sourcesFinancement.body);
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
