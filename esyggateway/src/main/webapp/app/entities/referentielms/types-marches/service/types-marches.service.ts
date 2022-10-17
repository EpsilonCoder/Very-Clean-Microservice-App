import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITypesMarches, NewTypesMarches } from '../types-marches.model';

export type PartialUpdateTypesMarches = Partial<ITypesMarches> & Pick<ITypesMarches, 'id'>;

export type EntityResponseType = HttpResponse<ITypesMarches>;
export type EntityArrayResponseType = HttpResponse<ITypesMarches[]>;

@Injectable({ providedIn: 'root' })
export class TypesMarchesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/types-marches', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(typesMarches: NewTypesMarches): Observable<EntityResponseType> {
    return this.http.post<ITypesMarches>(this.resourceUrl, typesMarches, { observe: 'response' });
  }

  update(typesMarches: ITypesMarches): Observable<EntityResponseType> {
    return this.http.put<ITypesMarches>(`${this.resourceUrl}/${this.getTypesMarchesIdentifier(typesMarches)}`, typesMarches, {
      observe: 'response',
    });
  }

  partialUpdate(typesMarches: PartialUpdateTypesMarches): Observable<EntityResponseType> {
    return this.http.patch<ITypesMarches>(`${this.resourceUrl}/${this.getTypesMarchesIdentifier(typesMarches)}`, typesMarches, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypesMarches>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypesMarches[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTypesMarchesIdentifier(typesMarches: Pick<ITypesMarches, 'id'>): number {
    return typesMarches.id;
  }

  compareTypesMarches(o1: Pick<ITypesMarches, 'id'> | null, o2: Pick<ITypesMarches, 'id'> | null): boolean {
    return o1 && o2 ? this.getTypesMarchesIdentifier(o1) === this.getTypesMarchesIdentifier(o2) : o1 === o2;
  }

  addTypesMarchesToCollectionIfMissing<Type extends Pick<ITypesMarches, 'id'>>(
    typesMarchesCollection: Type[],
    ...typesMarchesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const typesMarches: Type[] = typesMarchesToCheck.filter(isPresent);
    if (typesMarches.length > 0) {
      const typesMarchesCollectionIdentifiers = typesMarchesCollection.map(
        typesMarchesItem => this.getTypesMarchesIdentifier(typesMarchesItem)!
      );
      const typesMarchesToAdd = typesMarches.filter(typesMarchesItem => {
        const typesMarchesIdentifier = this.getTypesMarchesIdentifier(typesMarchesItem);
        if (typesMarchesCollectionIdentifiers.includes(typesMarchesIdentifier)) {
          return false;
        }
        typesMarchesCollectionIdentifiers.push(typesMarchesIdentifier);
        return true;
      });
      return [...typesMarchesToAdd, ...typesMarchesCollection];
    }
    return typesMarchesCollection;
  }
}
