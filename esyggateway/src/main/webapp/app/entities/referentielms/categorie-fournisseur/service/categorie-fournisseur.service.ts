import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategorieFournisseur, NewCategorieFournisseur } from '../categorie-fournisseur.model';

export type PartialUpdateCategorieFournisseur = Partial<ICategorieFournisseur> & Pick<ICategorieFournisseur, 'id'>;

export type EntityResponseType = HttpResponse<ICategorieFournisseur>;
export type EntityArrayResponseType = HttpResponse<ICategorieFournisseur[]>;

@Injectable({ providedIn: 'root' })
export class CategorieFournisseurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/categorie-fournisseurs', 'referentielms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(categorieFournisseur: NewCategorieFournisseur): Observable<EntityResponseType> {
    return this.http.post<ICategorieFournisseur>(this.resourceUrl, categorieFournisseur, { observe: 'response' });
  }

  update(categorieFournisseur: ICategorieFournisseur): Observable<EntityResponseType> {
    return this.http.put<ICategorieFournisseur>(
      `${this.resourceUrl}/${this.getCategorieFournisseurIdentifier(categorieFournisseur)}`,
      categorieFournisseur,
      { observe: 'response' }
    );
  }

  partialUpdate(categorieFournisseur: PartialUpdateCategorieFournisseur): Observable<EntityResponseType> {
    return this.http.patch<ICategorieFournisseur>(
      `${this.resourceUrl}/${this.getCategorieFournisseurIdentifier(categorieFournisseur)}`,
      categorieFournisseur,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategorieFournisseur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategorieFournisseur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCategorieFournisseurIdentifier(categorieFournisseur: Pick<ICategorieFournisseur, 'id'>): number {
    return categorieFournisseur.id;
  }

  compareCategorieFournisseur(o1: Pick<ICategorieFournisseur, 'id'> | null, o2: Pick<ICategorieFournisseur, 'id'> | null): boolean {
    return o1 && o2 ? this.getCategorieFournisseurIdentifier(o1) === this.getCategorieFournisseurIdentifier(o2) : o1 === o2;
  }

  addCategorieFournisseurToCollectionIfMissing<Type extends Pick<ICategorieFournisseur, 'id'>>(
    categorieFournisseurCollection: Type[],
    ...categorieFournisseursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const categorieFournisseurs: Type[] = categorieFournisseursToCheck.filter(isPresent);
    if (categorieFournisseurs.length > 0) {
      const categorieFournisseurCollectionIdentifiers = categorieFournisseurCollection.map(
        categorieFournisseurItem => this.getCategorieFournisseurIdentifier(categorieFournisseurItem)!
      );
      const categorieFournisseursToAdd = categorieFournisseurs.filter(categorieFournisseurItem => {
        const categorieFournisseurIdentifier = this.getCategorieFournisseurIdentifier(categorieFournisseurItem);
        if (categorieFournisseurCollectionIdentifiers.includes(categorieFournisseurIdentifier)) {
          return false;
        }
        categorieFournisseurCollectionIdentifiers.push(categorieFournisseurIdentifier);
        return true;
      });
      return [...categorieFournisseursToAdd, ...categorieFournisseurCollection];
    }
    return categorieFournisseurCollection;
  }
}
