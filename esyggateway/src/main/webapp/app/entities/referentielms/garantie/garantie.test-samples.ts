import { IGarantie, NewGarantie } from './garantie.model';

export const sampleWithRequiredData: IGarantie = {
  id: 80794,
  libelle: 'open',
  typeGarantie: 'primary',
};

export const sampleWithPartialData: IGarantie = {
  id: 61950,
  libelle: 'strategy Account',
  typeGarantie: 'Fresh Bacon',
};

export const sampleWithFullData: IGarantie = {
  id: 48776,
  libelle: 'enable Programmable',
  typeGarantie: 'transmitting c portal',
  description: 'optical b',
};

export const sampleWithNewData: NewGarantie = {
  libelle: 'Nouvelle-Zélande',
  typeGarantie: 'deposit',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
