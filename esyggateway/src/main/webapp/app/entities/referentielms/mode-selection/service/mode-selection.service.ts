import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IModeSelection, NewModeSelection } from '../mode-selection.model';

export type PartialUpdateModeSelection = Partial<IModeSelection> & Pick<IModeSelection, 'id'>;

export type EntityResponseType = HttpResponse<IModeSelection>;
export type EntityArrayResponseType = HttpResponse<IModeSelection[]>;

@Injectable({ providedIn: 'root' })
export class ModeSelectionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mode-selections', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(modeSelection: NewModeSelection): Observable<EntityResponseType> {
    return this.http.post<IModeSelection>(this.resourceUrl, modeSelection, { observe: 'response' });
  }

  update(modeSelection: IModeSelection): Observable<EntityResponseType> {
    return this.http.put<IModeSelection>(`${this.resourceUrl}/${this.getModeSelectionIdentifier(modeSelection)}`, modeSelection, {
      observe: 'response',
    });
  }

  partialUpdate(modeSelection: PartialUpdateModeSelection): Observable<EntityResponseType> {
    return this.http.patch<IModeSelection>(`${this.resourceUrl}/${this.getModeSelectionIdentifier(modeSelection)}`, modeSelection, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IModeSelection>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IModeSelection[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getModeSelectionIdentifier(modeSelection: Pick<IModeSelection, 'id'>): number {
    return modeSelection.id;
  }

  compareModeSelection(o1: Pick<IModeSelection, 'id'> | null, o2: Pick<IModeSelection, 'id'> | null): boolean {
    return o1 && o2 ? this.getModeSelectionIdentifier(o1) === this.getModeSelectionIdentifier(o2) : o1 === o2;
  }

  addModeSelectionToCollectionIfMissing<Type extends Pick<IModeSelection, 'id'>>(
    modeSelectionCollection: Type[],
    ...modeSelectionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const modeSelections: Type[] = modeSelectionsToCheck.filter(isPresent);
    if (modeSelections.length > 0) {
      const modeSelectionCollectionIdentifiers = modeSelectionCollection.map(
        modeSelectionItem => this.getModeSelectionIdentifier(modeSelectionItem)!
      );
      const modeSelectionsToAdd = modeSelections.filter(modeSelectionItem => {
        const modeSelectionIdentifier = this.getModeSelectionIdentifier(modeSelectionItem);
        if (modeSelectionCollectionIdentifiers.includes(modeSelectionIdentifier)) {
          return false;
        }
        modeSelectionCollectionIdentifiers.push(modeSelectionIdentifier);
        return true;
      });
      return [...modeSelectionsToAdd, ...modeSelectionCollection];
    }
    return modeSelectionCollection;
  }
}
