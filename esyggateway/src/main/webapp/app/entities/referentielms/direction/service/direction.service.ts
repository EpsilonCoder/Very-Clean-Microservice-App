import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDirection, NewDirection } from '../direction.model';

export type PartialUpdateDirection = Partial<IDirection> & Pick<IDirection, 'id'>;

export type EntityResponseType = HttpResponse<IDirection>;
export type EntityArrayResponseType = HttpResponse<IDirection[]>;

@Injectable({ providedIn: 'root' })
export class DirectionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/directions', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(direction: NewDirection): Observable<EntityResponseType> {
    return this.http.post<IDirection>(this.resourceUrl, direction, { observe: 'response' });
  }

  update(direction: IDirection): Observable<EntityResponseType> {
    return this.http.put<IDirection>(`${this.resourceUrl}/${this.getDirectionIdentifier(direction)}`, direction, { observe: 'response' });
  }

  partialUpdate(direction: PartialUpdateDirection): Observable<EntityResponseType> {
    return this.http.patch<IDirection>(`${this.resourceUrl}/${this.getDirectionIdentifier(direction)}`, direction, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDirection>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDirection[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDirectionIdentifier(direction: Pick<IDirection, 'id'>): number {
    return direction.id;
  }

  compareDirection(o1: Pick<IDirection, 'id'> | null, o2: Pick<IDirection, 'id'> | null): boolean {
    return o1 && o2 ? this.getDirectionIdentifier(o1) === this.getDirectionIdentifier(o2) : o1 === o2;
  }

  addDirectionToCollectionIfMissing<Type extends Pick<IDirection, 'id'>>(
    directionCollection: Type[],
    ...directionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const directions: Type[] = directionsToCheck.filter(isPresent);
    if (directions.length > 0) {
      const directionCollectionIdentifiers = directionCollection.map(directionItem => this.getDirectionIdentifier(directionItem)!);
      const directionsToAdd = directions.filter(directionItem => {
        const directionIdentifier = this.getDirectionIdentifier(directionItem);
        if (directionCollectionIdentifiers.includes(directionIdentifier)) {
          return false;
        }
        directionCollectionIdentifiers.push(directionIdentifier);
        return true;
      });
      return [...directionsToAdd, ...directionCollection];
    }
    return directionCollection;
  }
}
