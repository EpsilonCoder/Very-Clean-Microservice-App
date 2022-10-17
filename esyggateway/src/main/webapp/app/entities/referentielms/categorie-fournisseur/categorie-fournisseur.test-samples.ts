import { ICategorieFournisseur, NewCategorieFournisseur } from './categorie-fournisseur.model';

export const sampleWithRequiredData: ICategorieFournisseur = {
  id: 79385,
  libelle: 'Buckinghamshire digital Multi-lateral',
  description: 'Lituanie',
};

export const sampleWithPartialData: ICategorieFournisseur = {
  id: 31170,
  libelle: 'Shoes',
  description: 'Chair c',
};

export const sampleWithFullData: ICategorieFournisseur = {
  id: 191,
  libelle: 'optimize c next',
  description: 'morph',
};

export const sampleWithNewData: NewCategorieFournisseur = {
  libelle: 'Fresh',
  description: 'Car',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
