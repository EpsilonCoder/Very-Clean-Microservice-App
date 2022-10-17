import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISpecialitesPersonnel, NewSpecialitesPersonnel } from '../specialites-personnel.model';

export type PartialUpdateSpecialitesPersonnel = Partial<ISpecialitesPersonnel> & Pick<ISpecialitesPersonnel, 'id'>;

export type EntityResponseType = HttpResponse<ISpecialitesPersonnel>;
export type EntityArrayResponseType = HttpResponse<ISpecialitesPersonnel[]>;

@Injectable({ providedIn: 'root' })
export class SpecialitesPersonnelService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/specialites-personnels', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(specialitesPersonnel: NewSpecialitesPersonnel): Observable<EntityResponseType> {
    return this.http.post<ISpecialitesPersonnel>(this.resourceUrl, specialitesPersonnel, { observe: 'response' });
  }

  update(specialitesPersonnel: ISpecialitesPersonnel): Observable<EntityResponseType> {
    return this.http.put<ISpecialitesPersonnel>(
      `${this.resourceUrl}/${this.getSpecialitesPersonnelIdentifier(specialitesPersonnel)}`,
      specialitesPersonnel,
      { observe: 'response' }
    );
  }

  partialUpdate(specialitesPersonnel: PartialUpdateSpecialitesPersonnel): Observable<EntityResponseType> {
    return this.http.patch<ISpecialitesPersonnel>(
      `${this.resourceUrl}/${this.getSpecialitesPersonnelIdentifier(specialitesPersonnel)}`,
      specialitesPersonnel,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpecialitesPersonnel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpecialitesPersonnel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSpecialitesPersonnelIdentifier(specialitesPersonnel: Pick<ISpecialitesPersonnel, 'id'>): number {
    return specialitesPersonnel.id;
  }

  compareSpecialitesPersonnel(o1: Pick<ISpecialitesPersonnel, 'id'> | null, o2: Pick<ISpecialitesPersonnel, 'id'> | null): boolean {
    return o1 && o2 ? this.getSpecialitesPersonnelIdentifier(o1) === this.getSpecialitesPersonnelIdentifier(o2) : o1 === o2;
  }

  addSpecialitesPersonnelToCollectionIfMissing<Type extends Pick<ISpecialitesPersonnel, 'id'>>(
    specialitesPersonnelCollection: Type[],
    ...specialitesPersonnelsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const specialitesPersonnels: Type[] = specialitesPersonnelsToCheck.filter(isPresent);
    if (specialitesPersonnels.length > 0) {
      const specialitesPersonnelCollectionIdentifiers = specialitesPersonnelCollection.map(
        specialitesPersonnelItem => this.getSpecialitesPersonnelIdentifier(specialitesPersonnelItem)!
      );
      const specialitesPersonnelsToAdd = specialitesPersonnels.filter(specialitesPersonnelItem => {
        const specialitesPersonnelIdentifier = this.getSpecialitesPersonnelIdentifier(specialitesPersonnelItem);
        if (specialitesPersonnelCollectionIdentifiers.includes(specialitesPersonnelIdentifier)) {
          return false;
        }
        specialitesPersonnelCollectionIdentifiers.push(specialitesPersonnelIdentifier);
        return true;
      });
      return [...specialitesPersonnelsToAdd, ...specialitesPersonnelCollection];
    }
    return specialitesPersonnelCollection;
  }
}
