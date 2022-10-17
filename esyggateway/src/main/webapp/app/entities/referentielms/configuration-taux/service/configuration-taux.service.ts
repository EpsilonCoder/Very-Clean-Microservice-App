import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IConfigurationTaux, NewConfigurationTaux } from '../configuration-taux.model';

export type PartialUpdateConfigurationTaux = Partial<IConfigurationTaux> & Pick<IConfigurationTaux, 'id'>;

type RestOf<T extends IConfigurationTaux | NewConfigurationTaux> = Omit<T, 'dateDebut' | 'dateFin'> & {
  dateDebut?: string | null;
  dateFin?: string | null;
};

export type RestConfigurationTaux = RestOf<IConfigurationTaux>;

export type NewRestConfigurationTaux = RestOf<NewConfigurationTaux>;

export type PartialUpdateRestConfigurationTaux = RestOf<PartialUpdateConfigurationTaux>;

export type EntityResponseType = HttpResponse<IConfigurationTaux>;
export type EntityArrayResponseType = HttpResponse<IConfigurationTaux[]>;

@Injectable({ providedIn: 'root' })
export class ConfigurationTauxService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/configuration-tauxes', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(configurationTaux: NewConfigurationTaux): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(configurationTaux);
    return this.http
      .post<RestConfigurationTaux>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(configurationTaux: IConfigurationTaux): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(configurationTaux);
    return this.http
      .put<RestConfigurationTaux>(`${this.resourceUrl}/${this.getConfigurationTauxIdentifier(configurationTaux)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(configurationTaux: PartialUpdateConfigurationTaux): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(configurationTaux);
    return this.http
      .patch<RestConfigurationTaux>(`${this.resourceUrl}/${this.getConfigurationTauxIdentifier(configurationTaux)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestConfigurationTaux>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestConfigurationTaux[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getConfigurationTauxIdentifier(configurationTaux: Pick<IConfigurationTaux, 'id'>): number {
    return configurationTaux.id;
  }

  compareConfigurationTaux(o1: Pick<IConfigurationTaux, 'id'> | null, o2: Pick<IConfigurationTaux, 'id'> | null): boolean {
    return o1 && o2 ? this.getConfigurationTauxIdentifier(o1) === this.getConfigurationTauxIdentifier(o2) : o1 === o2;
  }

  addConfigurationTauxToCollectionIfMissing<Type extends Pick<IConfigurationTaux, 'id'>>(
    configurationTauxCollection: Type[],
    ...configurationTauxesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const configurationTauxes: Type[] = configurationTauxesToCheck.filter(isPresent);
    if (configurationTauxes.length > 0) {
      const configurationTauxCollectionIdentifiers = configurationTauxCollection.map(
        configurationTauxItem => this.getConfigurationTauxIdentifier(configurationTauxItem)!
      );
      const configurationTauxesToAdd = configurationTauxes.filter(configurationTauxItem => {
        const configurationTauxIdentifier = this.getConfigurationTauxIdentifier(configurationTauxItem);
        if (configurationTauxCollectionIdentifiers.includes(configurationTauxIdentifier)) {
          return false;
        }
        configurationTauxCollectionIdentifiers.push(configurationTauxIdentifier);
        return true;
      });
      return [...configurationTauxesToAdd, ...configurationTauxCollection];
    }
    return configurationTauxCollection;
  }

  protected convertDateFromClient<T extends IConfigurationTaux | NewConfigurationTaux | PartialUpdateConfigurationTaux>(
    configurationTaux: T
  ): RestOf<T> {
    return {
      ...configurationTaux,
      dateDebut: configurationTaux.dateDebut?.toJSON() ?? null,
      dateFin: configurationTaux.dateFin?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restConfigurationTaux: RestConfigurationTaux): IConfigurationTaux {
    return {
      ...restConfigurationTaux,
      dateDebut: restConfigurationTaux.dateDebut ? dayjs(restConfigurationTaux.dateDebut) : undefined,
      dateFin: restConfigurationTaux.dateFin ? dayjs(restConfigurationTaux.dateFin) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestConfigurationTaux>): HttpResponse<IConfigurationTaux> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestConfigurationTaux[]>): HttpResponse<IConfigurationTaux[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
