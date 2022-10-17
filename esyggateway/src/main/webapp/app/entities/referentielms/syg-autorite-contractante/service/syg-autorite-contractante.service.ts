import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISygAutoriteContractante, NewSygAutoriteContractante } from '../syg-autorite-contractante.model';

export type PartialUpdateSygAutoriteContractante = Partial<ISygAutoriteContractante> & Pick<ISygAutoriteContractante, 'id'>;

export type EntityResponseType = HttpResponse<ISygAutoriteContractante>;
export type EntityArrayResponseType = HttpResponse<ISygAutoriteContractante[]>;

@Injectable({ providedIn: 'root' })
export class SygAutoriteContractanteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/syg-autorite-contractantes', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sygAutoriteContractante: NewSygAutoriteContractante): Observable<EntityResponseType> {
    return this.http.post<ISygAutoriteContractante>(this.resourceUrl, sygAutoriteContractante, { observe: 'response' });
  }

  update(sygAutoriteContractante: ISygAutoriteContractante): Observable<EntityResponseType> {
    return this.http.put<ISygAutoriteContractante>(
      `${this.resourceUrl}/${this.getSygAutoriteContractanteIdentifier(sygAutoriteContractante)}`,
      sygAutoriteContractante,
      { observe: 'response' }
    );
  }

  partialUpdate(sygAutoriteContractante: PartialUpdateSygAutoriteContractante): Observable<EntityResponseType> {
    return this.http.patch<ISygAutoriteContractante>(
      `${this.resourceUrl}/${this.getSygAutoriteContractanteIdentifier(sygAutoriteContractante)}`,
      sygAutoriteContractante,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISygAutoriteContractante>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISygAutoriteContractante[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSygAutoriteContractanteIdentifier(sygAutoriteContractante: Pick<ISygAutoriteContractante, 'id'>): number {
    return sygAutoriteContractante.id;
  }

  compareSygAutoriteContractante(
    o1: Pick<ISygAutoriteContractante, 'id'> | null,
    o2: Pick<ISygAutoriteContractante, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getSygAutoriteContractanteIdentifier(o1) === this.getSygAutoriteContractanteIdentifier(o2) : o1 === o2;
  }

  addSygAutoriteContractanteToCollectionIfMissing<Type extends Pick<ISygAutoriteContractante, 'id'>>(
    sygAutoriteContractanteCollection: Type[],
    ...sygAutoriteContractantesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sygAutoriteContractantes: Type[] = sygAutoriteContractantesToCheck.filter(isPresent);
    if (sygAutoriteContractantes.length > 0) {
      const sygAutoriteContractanteCollectionIdentifiers = sygAutoriteContractanteCollection.map(
        sygAutoriteContractanteItem => this.getSygAutoriteContractanteIdentifier(sygAutoriteContractanteItem)!
      );
      const sygAutoriteContractantesToAdd = sygAutoriteContractantes.filter(sygAutoriteContractanteItem => {
        const sygAutoriteContractanteIdentifier = this.getSygAutoriteContractanteIdentifier(sygAutoriteContractanteItem);
        if (sygAutoriteContractanteCollectionIdentifiers.includes(sygAutoriteContractanteIdentifier)) {
          return false;
        }
        sygAutoriteContractanteCollectionIdentifiers.push(sygAutoriteContractanteIdentifier);
        return true;
      });
      return [...sygAutoriteContractantesToAdd, ...sygAutoriteContractanteCollection];
    }
    return sygAutoriteContractanteCollection;
  }
}
