import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IModeSelection } from '../mode-selection.model';
import { ModeSelectionService } from '../service/mode-selection.service';

@Injectable({ providedIn: 'root' })
export class ModeSelectionRoutingResolveService implements Resolve<IModeSelection | null> {
  constructor(protected service: ModeSelectionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IModeSelection | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((modeSelection: HttpResponse<IModeSelection>) => {
          if (modeSelection.body) {
            return of(modeSelection.body);
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
