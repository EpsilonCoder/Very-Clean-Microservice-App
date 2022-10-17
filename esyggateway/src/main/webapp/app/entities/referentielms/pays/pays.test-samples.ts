import { IPays, NewPays } from './pays.model';

export const sampleWithRequiredData: IPays = {
  id: 5072,
  libelle: 'Slovaquie',
  codePays: 'bandwidth Coordinateur',
};

export const sampleWithPartialData: IPays = {
  id: 96633,
  libelle: 'Peso Manager',
  codePays: 'bluetooth',
};

export const sampleWithFullData: IPays = {
  id: 94182,
  libelle: 'HDD Savings',
  codePays: 'Triple-buffered c c',
};

export const sampleWithNewData: NewPays = {
  libelle: 'Pastourelle c',
  codePays: 'wireless',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
