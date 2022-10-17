import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISourcesFinancement, NewSourcesFinancement } from '../sources-financement.model';

export type PartialUpdateSourcesFinancement = Partial<ISourcesFinancement> & Pick<ISourcesFinancement, 'id'>;

export type EntityResponseType = HttpResponse<ISourcesFinancement>;
export type EntityArrayResponseType = HttpResponse<ISourcesFinancement[]>;

@Injectable({ providedIn: 'root' })
export class SourcesFinancementService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sources-financements', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sourcesFinancement: NewSourcesFinancement): Observable<EntityResponseType> {
    return this.http.post<ISourcesFinancement>(this.resourceUrl, sourcesFinancement, { observe: 'response' });
  }

  update(sourcesFinancement: ISourcesFinancement): Observable<EntityResponseType> {
    return this.http.put<ISourcesFinancement>(
      `${this.resourceUrl}/${this.getSourcesFinancementIdentifier(sourcesFinancement)}`,
      sourcesFinancement,
      { observe: 'response' }
    );
  }

  partialUpdate(sourcesFinancement: PartialUpdateSourcesFinancement): Observable<EntityResponseType> {
    return this.http.patch<ISourcesFinancement>(
      `${this.resourceUrl}/${this.getSourcesFinancementIdentifier(sourcesFinancement)}`,
      sourcesFinancement,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISourcesFinancement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISourcesFinancement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSourcesFinancementIdentifier(sourcesFinancement: Pick<ISourcesFinancement, 'id'>): number {
    return sourcesFinancement.id;
  }

  compareSourcesFinancement(o1: Pick<ISourcesFinancement, 'id'> | null, o2: Pick<ISourcesFinancement, 'id'> | null): boolean {
    return o1 && o2 ? this.getSourcesFinancementIdentifier(o1) === this.getSourcesFinancementIdentifier(o2) : o1 === o2;
  }

  addSourcesFinancementToCollectionIfMissing<Type extends Pick<ISourcesFinancement, 'id'>>(
    sourcesFinancementCollection: Type[],
    ...sourcesFinancementsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sourcesFinancements: Type[] = sourcesFinancementsToCheck.filter(isPresent);
    if (sourcesFinancements.length > 0) {
      const sourcesFinancementCollectionIdentifiers = sourcesFinancementCollection.map(
        sourcesFinancementItem => this.getSourcesFinancementIdentifier(sourcesFinancementItem)!
      );
      const sourcesFinancementsToAdd = sourcesFinancements.filter(sourcesFinancementItem => {
        const sourcesFinancementIdentifier = this.getSourcesFinancementIdentifier(sourcesFinancementItem);
        if (sourcesFinancementCollectionIdentifiers.includes(sourcesFinancementIdentifier)) {
          return false;
        }
        sourcesFinancementCollectionIdentifiers.push(sourcesFinancementIdentifier);
        return true;
      });
      return [...sourcesFinancementsToAdd, ...sourcesFinancementCollection];
    }
    return sourcesFinancementCollection;
  }
}
