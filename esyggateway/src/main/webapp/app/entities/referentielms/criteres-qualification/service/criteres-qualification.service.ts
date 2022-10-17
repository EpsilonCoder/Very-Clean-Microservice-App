import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICriteresQualification, NewCriteresQualification } from '../criteres-qualification.model';

export type PartialUpdateCriteresQualification = Partial<ICriteresQualification> & Pick<ICriteresQualification, 'id'>;

export type EntityResponseType = HttpResponse<ICriteresQualification>;
export type EntityArrayResponseType = HttpResponse<ICriteresQualification[]>;

@Injectable({ providedIn: 'root' })
export class CriteresQualificationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/criteres-qualifications', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(criteresQualification: NewCriteresQualification): Observable<EntityResponseType> {
    return this.http.post<ICriteresQualification>(this.resourceUrl, criteresQualification, { observe: 'response' });
  }

  update(criteresQualification: ICriteresQualification): Observable<EntityResponseType> {
    return this.http.put<ICriteresQualification>(
      `${this.resourceUrl}/${this.getCriteresQualificationIdentifier(criteresQualification)}`,
      criteresQualification,
      { observe: 'response' }
    );
  }

  partialUpdate(criteresQualification: PartialUpdateCriteresQualification): Observable<EntityResponseType> {
    return this.http.patch<ICriteresQualification>(
      `${this.resourceUrl}/${this.getCriteresQualificationIdentifier(criteresQualification)}`,
      criteresQualification,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICriteresQualification>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICriteresQualification[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCriteresQualificationIdentifier(criteresQualification: Pick<ICriteresQualification, 'id'>): number {
    return criteresQualification.id;
  }

  compareCriteresQualification(o1: Pick<ICriteresQualification, 'id'> | null, o2: Pick<ICriteresQualification, 'id'> | null): boolean {
    return o1 && o2 ? this.getCriteresQualificationIdentifier(o1) === this.getCriteresQualificationIdentifier(o2) : o1 === o2;
  }

  addCriteresQualificationToCollectionIfMissing<Type extends Pick<ICriteresQualification, 'id'>>(
    criteresQualificationCollection: Type[],
    ...criteresQualificationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const criteresQualifications: Type[] = criteresQualificationsToCheck.filter(isPresent);
    if (criteresQualifications.length > 0) {
      const criteresQualificationCollectionIdentifiers = criteresQualificationCollection.map(
        criteresQualificationItem => this.getCriteresQualificationIdentifier(criteresQualificationItem)!
      );
      const criteresQualificationsToAdd = criteresQualifications.filter(criteresQualificationItem => {
        const criteresQualificationIdentifier = this.getCriteresQualificationIdentifier(criteresQualificationItem);
        if (criteresQualificationCollectionIdentifiers.includes(criteresQualificationIdentifier)) {
          return false;
        }
        criteresQualificationCollectionIdentifiers.push(criteresQualificationIdentifier);
        return true;
      });
      return [...criteresQualificationsToAdd, ...criteresQualificationCollection];
    }
    return criteresQualificationCollection;
  }
}
