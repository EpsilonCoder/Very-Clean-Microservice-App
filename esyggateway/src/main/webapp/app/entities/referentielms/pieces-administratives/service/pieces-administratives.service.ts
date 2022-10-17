import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPiecesAdministratives, NewPiecesAdministratives } from '../pieces-administratives.model';

export type PartialUpdatePiecesAdministratives = Partial<IPiecesAdministratives> & Pick<IPiecesAdministratives, 'id'>;

export type EntityResponseType = HttpResponse<IPiecesAdministratives>;
export type EntityArrayResponseType = HttpResponse<IPiecesAdministratives[]>;

@Injectable({ providedIn: 'root' })
export class PiecesAdministrativesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pieces-administratives', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(piecesAdministratives: NewPiecesAdministratives): Observable<EntityResponseType> {
    return this.http.post<IPiecesAdministratives>(this.resourceUrl, piecesAdministratives, { observe: 'response' });
  }

  update(piecesAdministratives: IPiecesAdministratives): Observable<EntityResponseType> {
    return this.http.put<IPiecesAdministratives>(
      `${this.resourceUrl}/${this.getPiecesAdministrativesIdentifier(piecesAdministratives)}`,
      piecesAdministratives,
      { observe: 'response' }
    );
  }

  partialUpdate(piecesAdministratives: PartialUpdatePiecesAdministratives): Observable<EntityResponseType> {
    return this.http.patch<IPiecesAdministratives>(
      `${this.resourceUrl}/${this.getPiecesAdministrativesIdentifier(piecesAdministratives)}`,
      piecesAdministratives,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPiecesAdministratives>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPiecesAdministratives[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPiecesAdministrativesIdentifier(piecesAdministratives: Pick<IPiecesAdministratives, 'id'>): number {
    return piecesAdministratives.id;
  }

  comparePiecesAdministratives(o1: Pick<IPiecesAdministratives, 'id'> | null, o2: Pick<IPiecesAdministratives, 'id'> | null): boolean {
    return o1 && o2 ? this.getPiecesAdministrativesIdentifier(o1) === this.getPiecesAdministrativesIdentifier(o2) : o1 === o2;
  }

  addPiecesAdministrativesToCollectionIfMissing<Type extends Pick<IPiecesAdministratives, 'id'>>(
    piecesAdministrativesCollection: Type[],
    ...piecesAdministrativesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const piecesAdministratives: Type[] = piecesAdministrativesToCheck.filter(isPresent);
    if (piecesAdministratives.length > 0) {
      const piecesAdministrativesCollectionIdentifiers = piecesAdministrativesCollection.map(
        piecesAdministrativesItem => this.getPiecesAdministrativesIdentifier(piecesAdministrativesItem)!
      );
      const piecesAdministrativesToAdd = piecesAdministratives.filter(piecesAdministrativesItem => {
        const piecesAdministrativesIdentifier = this.getPiecesAdministrativesIdentifier(piecesAdministrativesItem);
        if (piecesAdministrativesCollectionIdentifiers.includes(piecesAdministrativesIdentifier)) {
          return false;
        }
        piecesAdministrativesCollectionIdentifiers.push(piecesAdministrativesIdentifier);
        return true;
      });
      return [...piecesAdministrativesToAdd, ...piecesAdministrativesCollection];
    }
    return piecesAdministrativesCollection;
  }
}
