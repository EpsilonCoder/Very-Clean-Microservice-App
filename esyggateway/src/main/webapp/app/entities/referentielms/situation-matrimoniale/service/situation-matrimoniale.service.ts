import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISituationMatrimoniale, NewSituationMatrimoniale } from '../situation-matrimoniale.model';

export type PartialUpdateSituationMatrimoniale = Partial<ISituationMatrimoniale> & Pick<ISituationMatrimoniale, 'id'>;

export type EntityResponseType = HttpResponse<ISituationMatrimoniale>;
export type EntityArrayResponseType = HttpResponse<ISituationMatrimoniale[]>;

@Injectable({ providedIn: 'root' })
export class SituationMatrimonialeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/situation-matrimoniales', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(situationMatrimoniale: NewSituationMatrimoniale): Observable<EntityResponseType> {
    return this.http.post<ISituationMatrimoniale>(this.resourceUrl, situationMatrimoniale, { observe: 'response' });
  }

  update(situationMatrimoniale: ISituationMatrimoniale): Observable<EntityResponseType> {
    return this.http.put<ISituationMatrimoniale>(
      `${this.resourceUrl}/${this.getSituationMatrimonialeIdentifier(situationMatrimoniale)}`,
      situationMatrimoniale,
      { observe: 'response' }
    );
  }

  partialUpdate(situationMatrimoniale: PartialUpdateSituationMatrimoniale): Observable<EntityResponseType> {
    return this.http.patch<ISituationMatrimoniale>(
      `${this.resourceUrl}/${this.getSituationMatrimonialeIdentifier(situationMatrimoniale)}`,
      situationMatrimoniale,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISituationMatrimoniale>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISituationMatrimoniale[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSituationMatrimonialeIdentifier(situationMatrimoniale: Pick<ISituationMatrimoniale, 'id'>): number {
    return situationMatrimoniale.id;
  }

  compareSituationMatrimoniale(o1: Pick<ISituationMatrimoniale, 'id'> | null, o2: Pick<ISituationMatrimoniale, 'id'> | null): boolean {
    return o1 && o2 ? this.getSituationMatrimonialeIdentifier(o1) === this.getSituationMatrimonialeIdentifier(o2) : o1 === o2;
  }

  addSituationMatrimonialeToCollectionIfMissing<Type extends Pick<ISituationMatrimoniale, 'id'>>(
    situationMatrimonialeCollection: Type[],
    ...situationMatrimonialesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const situationMatrimoniales: Type[] = situationMatrimonialesToCheck.filter(isPresent);
    if (situationMatrimoniales.length > 0) {
      const situationMatrimonialeCollectionIdentifiers = situationMatrimonialeCollection.map(
        situationMatrimonialeItem => this.getSituationMatrimonialeIdentifier(situationMatrimonialeItem)!
      );
      const situationMatrimonialesToAdd = situationMatrimoniales.filter(situationMatrimonialeItem => {
        const situationMatrimonialeIdentifier = this.getSituationMatrimonialeIdentifier(situationMatrimonialeItem);
        if (situationMatrimonialeCollectionIdentifiers.includes(situationMatrimonialeIdentifier)) {
          return false;
        }
        situationMatrimonialeCollectionIdentifiers.push(situationMatrimonialeIdentifier);
        return true;
      });
      return [...situationMatrimonialesToAdd, ...situationMatrimonialeCollection];
    }
    return situationMatrimonialeCollection;
  }
}
