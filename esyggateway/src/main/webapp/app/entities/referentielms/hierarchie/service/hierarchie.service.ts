import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHierarchie, NewHierarchie } from '../hierarchie.model';

export type PartialUpdateHierarchie = Partial<IHierarchie> & Pick<IHierarchie, 'id'>;

export type EntityResponseType = HttpResponse<IHierarchie>;
export type EntityArrayResponseType = HttpResponse<IHierarchie[]>;

@Injectable({ providedIn: 'root' })
export class HierarchieService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/hierarchies', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(hierarchie: NewHierarchie): Observable<EntityResponseType> {
    return this.http.post<IHierarchie>(this.resourceUrl, hierarchie, { observe: 'response' });
  }

  update(hierarchie: IHierarchie): Observable<EntityResponseType> {
    return this.http.put<IHierarchie>(`${this.resourceUrl}/${this.getHierarchieIdentifier(hierarchie)}`, hierarchie, {
      observe: 'response',
    });
  }

  partialUpdate(hierarchie: PartialUpdateHierarchie): Observable<EntityResponseType> {
    return this.http.patch<IHierarchie>(`${this.resourceUrl}/${this.getHierarchieIdentifier(hierarchie)}`, hierarchie, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHierarchie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHierarchie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getHierarchieIdentifier(hierarchie: Pick<IHierarchie, 'id'>): number {
    return hierarchie.id;
  }

  compareHierarchie(o1: Pick<IHierarchie, 'id'> | null, o2: Pick<IHierarchie, 'id'> | null): boolean {
    return o1 && o2 ? this.getHierarchieIdentifier(o1) === this.getHierarchieIdentifier(o2) : o1 === o2;
  }

  addHierarchieToCollectionIfMissing<Type extends Pick<IHierarchie, 'id'>>(
    hierarchieCollection: Type[],
    ...hierarchiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const hierarchies: Type[] = hierarchiesToCheck.filter(isPresent);
    if (hierarchies.length > 0) {
      const hierarchieCollectionIdentifiers = hierarchieCollection.map(hierarchieItem => this.getHierarchieIdentifier(hierarchieItem)!);
      const hierarchiesToAdd = hierarchies.filter(hierarchieItem => {
        const hierarchieIdentifier = this.getHierarchieIdentifier(hierarchieItem);
        if (hierarchieCollectionIdentifiers.includes(hierarchieIdentifier)) {
          return false;
        }
        hierarchieCollectionIdentifiers.push(hierarchieIdentifier);
        return true;
      });
      return [...hierarchiesToAdd, ...hierarchieCollection];
    }
    return hierarchieCollection;
  }
}
