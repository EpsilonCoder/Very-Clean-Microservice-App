import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPersonnesRessources, NewPersonnesRessources } from '../personnes-ressources.model';

export type PartialUpdatePersonnesRessources = Partial<IPersonnesRessources> & Pick<IPersonnesRessources, 'id'>;

export type EntityResponseType = HttpResponse<IPersonnesRessources>;
export type EntityArrayResponseType = HttpResponse<IPersonnesRessources[]>;

@Injectable({ providedIn: 'root' })
export class PersonnesRessourcesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/personnes-ressources', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(personnesRessources: NewPersonnesRessources): Observable<EntityResponseType> {
    return this.http.post<IPersonnesRessources>(this.resourceUrl, personnesRessources, { observe: 'response' });
  }

  update(personnesRessources: IPersonnesRessources): Observable<EntityResponseType> {
    return this.http.put<IPersonnesRessources>(
      `${this.resourceUrl}/${this.getPersonnesRessourcesIdentifier(personnesRessources)}`,
      personnesRessources,
      { observe: 'response' }
    );
  }

  partialUpdate(personnesRessources: PartialUpdatePersonnesRessources): Observable<EntityResponseType> {
    return this.http.patch<IPersonnesRessources>(
      `${this.resourceUrl}/${this.getPersonnesRessourcesIdentifier(personnesRessources)}`,
      personnesRessources,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPersonnesRessources>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPersonnesRessources[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPersonnesRessourcesIdentifier(personnesRessources: Pick<IPersonnesRessources, 'id'>): number {
    return personnesRessources.id;
  }

  comparePersonnesRessources(o1: Pick<IPersonnesRessources, 'id'> | null, o2: Pick<IPersonnesRessources, 'id'> | null): boolean {
    return o1 && o2 ? this.getPersonnesRessourcesIdentifier(o1) === this.getPersonnesRessourcesIdentifier(o2) : o1 === o2;
  }

  addPersonnesRessourcesToCollectionIfMissing<Type extends Pick<IPersonnesRessources, 'id'>>(
    personnesRessourcesCollection: Type[],
    ...personnesRessourcesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const personnesRessources: Type[] = personnesRessourcesToCheck.filter(isPresent);
    if (personnesRessources.length > 0) {
      const personnesRessourcesCollectionIdentifiers = personnesRessourcesCollection.map(
        personnesRessourcesItem => this.getPersonnesRessourcesIdentifier(personnesRessourcesItem)!
      );
      const personnesRessourcesToAdd = personnesRessources.filter(personnesRessourcesItem => {
        const personnesRessourcesIdentifier = this.getPersonnesRessourcesIdentifier(personnesRessourcesItem);
        if (personnesRessourcesCollectionIdentifiers.includes(personnesRessourcesIdentifier)) {
          return false;
        }
        personnesRessourcesCollectionIdentifiers.push(personnesRessourcesIdentifier);
        return true;
      });
      return [...personnesRessourcesToAdd, ...personnesRessourcesCollection];
    }
    return personnesRessourcesCollection;
  }
}
