import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDelais, NewDelais } from '../delais.model';

export type PartialUpdateDelais = Partial<IDelais> & Pick<IDelais, 'id'>;

type RestOf<T extends IDelais | NewDelais> = Omit<T, 'debutValidite' | 'finValidite'> & {
  debutValidite?: string | null;
  finValidite?: string | null;
};

export type RestDelais = RestOf<IDelais>;

export type NewRestDelais = RestOf<NewDelais>;

export type PartialUpdateRestDelais = RestOf<PartialUpdateDelais>;

export type EntityResponseType = HttpResponse<IDelais>;
export type EntityArrayResponseType = HttpResponse<IDelais[]>;

@Injectable({ providedIn: 'root' })
export class DelaisService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/delais', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(delais: NewDelais): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(delais);
    return this.http
      .post<RestDelais>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(delais: IDelais): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(delais);
    return this.http
      .put<RestDelais>(`${this.resourceUrl}/${this.getDelaisIdentifier(delais)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(delais: PartialUpdateDelais): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(delais);
    return this.http
      .patch<RestDelais>(`${this.resourceUrl}/${this.getDelaisIdentifier(delais)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDelais>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDelais[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDelaisIdentifier(delais: Pick<IDelais, 'id'>): number {
    return delais.id;
  }

  compareDelais(o1: Pick<IDelais, 'id'> | null, o2: Pick<IDelais, 'id'> | null): boolean {
    return o1 && o2 ? this.getDelaisIdentifier(o1) === this.getDelaisIdentifier(o2) : o1 === o2;
  }

  addDelaisToCollectionIfMissing<Type extends Pick<IDelais, 'id'>>(
    delaisCollection: Type[],
    ...delaisToCheck: (Type | null | undefined)[]
  ): Type[] {
    const delais: Type[] = delaisToCheck.filter(isPresent);
    if (delais.length > 0) {
      const delaisCollectionIdentifiers = delaisCollection.map(delaisItem => this.getDelaisIdentifier(delaisItem)!);
      const delaisToAdd = delais.filter(delaisItem => {
        const delaisIdentifier = this.getDelaisIdentifier(delaisItem);
        if (delaisCollectionIdentifiers.includes(delaisIdentifier)) {
          return false;
        }
        delaisCollectionIdentifiers.push(delaisIdentifier);
        return true;
      });
      return [...delaisToAdd, ...delaisCollection];
    }
    return delaisCollection;
  }

  protected convertDateFromClient<T extends IDelais | NewDelais | PartialUpdateDelais>(delais: T): RestOf<T> {
    return {
      ...delais,
      debutValidite: delais.debutValidite?.toJSON() ?? null,
      finValidite: delais.finValidite?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDelais: RestDelais): IDelais {
    return {
      ...restDelais,
      debutValidite: restDelais.debutValidite ? dayjs(restDelais.debutValidite) : undefined,
      finValidite: restDelais.finValidite ? dayjs(restDelais.finValidite) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDelais>): HttpResponse<IDelais> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDelais[]>): HttpResponse<IDelais[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
