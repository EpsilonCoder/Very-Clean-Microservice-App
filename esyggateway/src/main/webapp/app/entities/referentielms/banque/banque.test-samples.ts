import { IBanque, NewBanque } from './banque.model';

export const sampleWithRequiredData: IBanque = {
  id: 83469,
  libelle: 'Laos',
  sigle: 'Rubber',
};

export const sampleWithPartialData: IBanque = {
  id: 78703,
  libelle: 'Île-de-France Developpeur',
  sigle: 'c Chair portals',
};

export const sampleWithFullData: IBanque = {
  id: 90625,
  libelle: 'b Technicien Afghani',
  sigle: 'content payment',
};

export const sampleWithNewData: NewBanque = {
  libelle: 'Account',
  sigle: 'intuitive Intelligent Buckinghamshire',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
