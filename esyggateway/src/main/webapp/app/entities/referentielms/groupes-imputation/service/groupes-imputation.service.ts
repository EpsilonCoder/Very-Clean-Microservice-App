import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGroupesImputation, NewGroupesImputation } from '../groupes-imputation.model';

export type PartialUpdateGroupesImputation = Partial<IGroupesImputation> & Pick<IGroupesImputation, 'id'>;

export type EntityResponseType = HttpResponse<IGroupesImputation>;
export type EntityArrayResponseType = HttpResponse<IGroupesImputation[]>;

@Injectable({ providedIn: 'root' })
export class GroupesImputationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/groupes-imputations', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(groupesImputation: NewGroupesImputation): Observable<EntityResponseType> {
    return this.http.post<IGroupesImputation>(this.resourceUrl, groupesImputation, { observe: 'response' });
  }

  update(groupesImputation: IGroupesImputation): Observable<EntityResponseType> {
    return this.http.put<IGroupesImputation>(
      `${this.resourceUrl}/${this.getGroupesImputationIdentifier(groupesImputation)}`,
      groupesImputation,
      { observe: 'response' }
    );
  }

  partialUpdate(groupesImputation: PartialUpdateGroupesImputation): Observable<EntityResponseType> {
    return this.http.patch<IGroupesImputation>(
      `${this.resourceUrl}/${this.getGroupesImputationIdentifier(groupesImputation)}`,
      groupesImputation,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGroupesImputation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGroupesImputation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGroupesImputationIdentifier(groupesImputation: Pick<IGroupesImputation, 'id'>): number {
    return groupesImputation.id;
  }

  compareGroupesImputation(o1: Pick<IGroupesImputation, 'id'> | null, o2: Pick<IGroupesImputation, 'id'> | null): boolean {
    return o1 && o2 ? this.getGroupesImputationIdentifier(o1) === this.getGroupesImputationIdentifier(o2) : o1 === o2;
  }

  addGroupesImputationToCollectionIfMissing<Type extends Pick<IGroupesImputation, 'id'>>(
    groupesImputationCollection: Type[],
    ...groupesImputationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const groupesImputations: Type[] = groupesImputationsToCheck.filter(isPresent);
    if (groupesImputations.length > 0) {
      const groupesImputationCollectionIdentifiers = groupesImputationCollection.map(
        groupesImputationItem => this.getGroupesImputationIdentifier(groupesImputationItem)!
      );
      const groupesImputationsToAdd = groupesImputations.filter(groupesImputationItem => {
        const groupesImputationIdentifier = this.getGroupesImputationIdentifier(groupesImputationItem);
        if (groupesImputationCollectionIdentifiers.includes(groupesImputationIdentifier)) {
          return false;
        }
        groupesImputationCollectionIdentifiers.push(groupesImputationIdentifier);
        return true;
      });
      return [...groupesImputationsToAdd, ...groupesImputationCollection];
    }
    return groupesImputationCollection;
  }
}
