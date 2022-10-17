import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAvisGeneraux, NewAvisGeneraux } from '../avis-generaux.model';

export type PartialUpdateAvisGeneraux = Partial<IAvisGeneraux> & Pick<IAvisGeneraux, 'id'>;

type RestOf<T extends IAvisGeneraux | NewAvisGeneraux> = Omit<T, 'datePublication'> & {
  datePublication?: string | null;
};

export type RestAvisGeneraux = RestOf<IAvisGeneraux>;

export type NewRestAvisGeneraux = RestOf<NewAvisGeneraux>;

export type PartialUpdateRestAvisGeneraux = RestOf<PartialUpdateAvisGeneraux>;

export type EntityResponseType = HttpResponse<IAvisGeneraux>;
export type EntityArrayResponseType = HttpResponse<IAvisGeneraux[]>;

@Injectable({ providedIn: 'root' })
export class AvisGenerauxService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/avis-generauxes', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(avisGeneraux: NewAvisGeneraux): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(avisGeneraux);
    return this.http
      .post<RestAvisGeneraux>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(avisGeneraux: IAvisGeneraux): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(avisGeneraux);
    return this.http
      .put<RestAvisGeneraux>(`${this.resourceUrl}/${this.getAvisGenerauxIdentifier(avisGeneraux)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(avisGeneraux: PartialUpdateAvisGeneraux): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(avisGeneraux);
    return this.http
      .patch<RestAvisGeneraux>(`${this.resourceUrl}/${this.getAvisGenerauxIdentifier(avisGeneraux)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAvisGeneraux>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAvisGeneraux[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAvisGenerauxIdentifier(avisGeneraux: Pick<IAvisGeneraux, 'id'>): number {
    return avisGeneraux.id;
  }

  compareAvisGeneraux(o1: Pick<IAvisGeneraux, 'id'> | null, o2: Pick<IAvisGeneraux, 'id'> | null): boolean {
    return o1 && o2 ? this.getAvisGenerauxIdentifier(o1) === this.getAvisGenerauxIdentifier(o2) : o1 === o2;
  }

  addAvisGenerauxToCollectionIfMissing<Type extends Pick<IAvisGeneraux, 'id'>>(
    avisGenerauxCollection: Type[],
    ...avisGenerauxesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const avisGenerauxes: Type[] = avisGenerauxesToCheck.filter(isPresent);
    if (avisGenerauxes.length > 0) {
      const avisGenerauxCollectionIdentifiers = avisGenerauxCollection.map(
        avisGenerauxItem => this.getAvisGenerauxIdentifier(avisGenerauxItem)!
      );
      const avisGenerauxesToAdd = avisGenerauxes.filter(avisGenerauxItem => {
        const avisGenerauxIdentifier = this.getAvisGenerauxIdentifier(avisGenerauxItem);
        if (avisGenerauxCollectionIdentifiers.includes(avisGenerauxIdentifier)) {
          return false;
        }
        avisGenerauxCollectionIdentifiers.push(avisGenerauxIdentifier);
        return true;
      });
      return [...avisGenerauxesToAdd, ...avisGenerauxCollection];
    }
    return avisGenerauxCollection;
  }

  protected convertDateFromClient<T extends IAvisGeneraux | NewAvisGeneraux | PartialUpdateAvisGeneraux>(avisGeneraux: T): RestOf<T> {
    return {
      ...avisGeneraux,
      datePublication: avisGeneraux.datePublication?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAvisGeneraux: RestAvisGeneraux): IAvisGeneraux {
    return {
      ...restAvisGeneraux,
      datePublication: restAvisGeneraux.datePublication ? dayjs(restAvisGeneraux.datePublication) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAvisGeneraux>): HttpResponse<IAvisGeneraux> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAvisGeneraux[]>): HttpResponse<IAvisGeneraux[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
