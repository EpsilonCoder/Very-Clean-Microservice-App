import { IFonction, NewFonction } from './fonction.model';

export const sampleWithRequiredData: IFonction = {
  id: 57026,
  libelle: 'deposit',
};

export const sampleWithPartialData: IFonction = {
  id: 64909,
  libelle: 'Wooden Generic',
};

export const sampleWithFullData: IFonction = {
  id: 90271,
  libelle: 'port',
  description: 'Beauty',
};

export const sampleWithNewData: NewFonction = {
  libelle: 'c',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
