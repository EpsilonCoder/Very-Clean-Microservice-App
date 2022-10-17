import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IConfigurationTaux } from '../configuration-taux.model';
import { ConfigurationTauxService } from '../service/configuration-taux.service';

@Injectable({ providedIn: 'root' })
export class ConfigurationTauxRoutingResolveService implements Resolve<IConfigurationTaux | null> {
  constructor(protected service: ConfigurationTauxService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConfigurationTaux | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((configurationTaux: HttpResponse<IConfigurationTaux>) => {
          if (configurationTaux.body) {
            return of(configurationTaux.body);
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
