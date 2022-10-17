import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INaturesGarantie, NewNaturesGarantie } from '../natures-garantie.model';

export type PartialUpdateNaturesGarantie = Partial<INaturesGarantie> & Pick<INaturesGarantie, 'id'>;

export type EntityResponseType = HttpResponse<INaturesGarantie>;
export type EntityArrayResponseType = HttpResponse<INaturesGarantie[]>;

@Injectable({ providedIn: 'root' })
export class NaturesGarantieService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/natures-garanties', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(naturesGarantie: NewNaturesGarantie): Observable<EntityResponseType> {
    return this.http.post<INaturesGarantie>(this.resourceUrl, naturesGarantie, { observe: 'response' });
  }

  update(naturesGarantie: INaturesGarantie): Observable<EntityResponseType> {
    return this.http.put<INaturesGarantie>(`${this.resourceUrl}/${this.getNaturesGarantieIdentifier(naturesGarantie)}`, naturesGarantie, {
      observe: 'response',
    });
  }

  partialUpdate(naturesGarantie: PartialUpdateNaturesGarantie): Observable<EntityResponseType> {
    return this.http.patch<INaturesGarantie>(`${this.resourceUrl}/${this.getNaturesGarantieIdentifier(naturesGarantie)}`, naturesGarantie, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INaturesGarantie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INaturesGarantie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNaturesGarantieIdentifier(naturesGarantie: Pick<INaturesGarantie, 'id'>): number {
    return naturesGarantie.id;
  }

  compareNaturesGarantie(o1: Pick<INaturesGarantie, 'id'> | null, o2: Pick<INaturesGarantie, 'id'> | null): boolean {
    return o1 && o2 ? this.getNaturesGarantieIdentifier(o1) === this.getNaturesGarantieIdentifier(o2) : o1 === o2;
  }

  addNaturesGarantieToCollectionIfMissing<Type extends Pick<INaturesGarantie, 'id'>>(
    naturesGarantieCollection: Type[],
    ...naturesGarantiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const naturesGaranties: Type[] = naturesGarantiesToCheck.filter(isPresent);
    if (naturesGaranties.length > 0) {
      const naturesGarantieCollectionIdentifiers = naturesGarantieCollection.map(
        naturesGarantieItem => this.getNaturesGarantieIdentifier(naturesGarantieItem)!
      );
      const naturesGarantiesToAdd = naturesGaranties.filter(naturesGarantieItem => {
        const naturesGarantieIdentifier = this.getNaturesGarantieIdentifier(naturesGarantieItem);
        if (naturesGarantieCollectionIdentifiers.includes(naturesGarantieIdentifier)) {
          return false;
        }
        naturesGarantieCollectionIdentifiers.push(naturesGarantieIdentifier);
        return true;
      });
      return [...naturesGarantiesToAdd, ...naturesGarantieCollection];
    }
    return naturesGarantieCollection;
  }
}
