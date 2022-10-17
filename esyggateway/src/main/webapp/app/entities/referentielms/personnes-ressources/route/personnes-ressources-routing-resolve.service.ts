import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPersonnesRessources } from '../personnes-ressources.model';
import { PersonnesRessourcesService } from '../service/personnes-ressources.service';

@Injectable({ providedIn: 'root' })
export class PersonnesRessourcesRoutingResolveService implements Resolve<IPersonnesRessources | null> {
  constructor(protected service: PersonnesRessourcesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonnesRessources | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((personnesRessources: HttpResponse<IPersonnesRessources>) => {
          if (personnesRessources.body) {
            return of(personnesRessources.body);
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
