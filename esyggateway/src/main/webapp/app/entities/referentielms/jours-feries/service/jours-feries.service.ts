import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IJoursFeries, NewJoursFeries } from '../jours-feries.model';

export type PartialUpdateJoursFeries = Partial<IJoursFeries> & Pick<IJoursFeries, 'id'>;

type RestOf<T extends IJoursFeries | NewJoursFeries> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestJoursFeries = RestOf<IJoursFeries>;

export type NewRestJoursFeries = RestOf<NewJoursFeries>;

export type PartialUpdateRestJoursFeries = RestOf<PartialUpdateJoursFeries>;

export type EntityResponseType = HttpResponse<IJoursFeries>;
export type EntityArrayResponseType = HttpResponse<IJoursFeries[]>;

@Injectable({ providedIn: 'root' })
export class JoursFeriesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/jours-feries', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(joursFeries: NewJoursFeries): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(joursFeries);
    return this.http
      .post<RestJoursFeries>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(joursFeries: IJoursFeries): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(joursFeries);
    return this.http
      .put<RestJoursFeries>(`${this.resourceUrl}/${this.getJoursFeriesIdentifier(joursFeries)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(joursFeries: PartialUpdateJoursFeries): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(joursFeries);
    return this.http
      .patch<RestJoursFeries>(`${this.resourceUrl}/${this.getJoursFeriesIdentifier(joursFeries)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestJoursFeries>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestJoursFeries[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getJoursFeriesIdentifier(joursFeries: Pick<IJoursFeries, 'id'>): number {
    return joursFeries.id;
  }

  compareJoursFeries(o1: Pick<IJoursFeries, 'id'> | null, o2: Pick<IJoursFeries, 'id'> | null): boolean {
    return o1 && o2 ? this.getJoursFeriesIdentifier(o1) === this.getJoursFeriesIdentifier(o2) : o1 === o2;
  }

  addJoursFeriesToCollectionIfMissing<Type extends Pick<IJoursFeries, 'id'>>(
    joursFeriesCollection: Type[],
    ...joursFeriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const joursFeries: Type[] = joursFeriesToCheck.filter(isPresent);
    if (joursFeries.length > 0) {
      const joursFeriesCollectionIdentifiers = joursFeriesCollection.map(
        joursFeriesItem => this.getJoursFeriesIdentifier(joursFeriesItem)!
      );
      const joursFeriesToAdd = joursFeries.filter(joursFeriesItem => {
        const joursFeriesIdentifier = this.getJoursFeriesIdentifier(joursFeriesItem);
        if (joursFeriesCollectionIdentifiers.includes(joursFeriesIdentifier)) {
          return false;
        }
        joursFeriesCollectionIdentifiers.push(joursFeriesIdentifier);
        return true;
      });
      return [...joursFeriesToAdd, ...joursFeriesCollection];
    }
    return joursFeriesCollection;
  }

  protected convertDateFromClient<T extends IJoursFeries | NewJoursFeries | PartialUpdateJoursFeries>(joursFeries: T): RestOf<T> {
    return {
      ...joursFeries,
      date: joursFeries.date?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restJoursFeries: RestJoursFeries): IJoursFeries {
    return {
      ...restJoursFeries,
      date: restJoursFeries.date ? dayjs(restJoursFeries.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestJoursFeries>): HttpResponse<IJoursFeries> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestJoursFeries[]>): HttpResponse<IJoursFeries[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
