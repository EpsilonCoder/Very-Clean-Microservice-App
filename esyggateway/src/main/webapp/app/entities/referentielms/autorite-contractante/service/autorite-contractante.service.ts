import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAutoriteContractante, NewAutoriteContractante } from '../autorite-contractante.model';

export type PartialUpdateAutoriteContractante = Partial<IAutoriteContractante> & Pick<IAutoriteContractante, 'id'>;

export type EntityResponseType = HttpResponse<IAutoriteContractante>;
export type EntityArrayResponseType = HttpResponse<IAutoriteContractante[]>;

@Injectable({ providedIn: 'root' })
export class AutoriteContractanteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/autorite-contractantes', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAutoriteContractante>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAutoriteContractante[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getAutoriteContractanteIdentifier(autoriteContractante: Pick<IAutoriteContractante, 'id'>): number {
    return autoriteContractante.id;
  }

  compareAutoriteContractante(o1: Pick<IAutoriteContractante, 'id'> | null, o2: Pick<IAutoriteContractante, 'id'> | null): boolean {
    return o1 && o2 ? this.getAutoriteContractanteIdentifier(o1) === this.getAutoriteContractanteIdentifier(o2) : o1 === o2;
  }

  addAutoriteContractanteToCollectionIfMissing<Type extends Pick<IAutoriteContractante, 'id'>>(
    autoriteContractanteCollection: Type[],
    ...autoriteContractantesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const autoriteContractantes: Type[] = autoriteContractantesToCheck.filter(isPresent);
    if (autoriteContractantes.length > 0) {
      const autoriteContractanteCollectionIdentifiers = autoriteContractanteCollection.map(
        autoriteContractanteItem => this.getAutoriteContractanteIdentifier(autoriteContractanteItem)!
      );
      const autoriteContractantesToAdd = autoriteContractantes.filter(autoriteContractanteItem => {
        const autoriteContractanteIdentifier = this.getAutoriteContractanteIdentifier(autoriteContractanteItem);
        if (autoriteContractanteCollectionIdentifiers.includes(autoriteContractanteIdentifier)) {
          return false;
        }
        autoriteContractanteCollectionIdentifiers.push(autoriteContractanteIdentifier);
        return true;
      });
      return [...autoriteContractantesToAdd, ...autoriteContractanteCollection];
    }
    return autoriteContractanteCollection;
  }
}
