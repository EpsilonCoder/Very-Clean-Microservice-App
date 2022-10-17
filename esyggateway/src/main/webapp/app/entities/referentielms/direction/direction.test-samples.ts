import { IDirection, NewDirection } from './direction.model';

export const sampleWithRequiredData: IDirection = {
  id: 27867,
  sigle: 'deposit Shirt multi-byte',
  libelle: 'New Nepalese',
};

export const sampleWithPartialData: IDirection = {
  id: 55270,
  sigle: 'monetize',
  libelle: 'Haute-Normandie Small',
  description: 'open',
};

export const sampleWithFullData: IDirection = {
  id: 70972,
  sigle: 'Mozambique',
  libelle: 'Quality-focused multi-byte Fantastic',
  description: 'Awesome Ergonomic',
};

export const sampleWithNewData: NewDirection = {
  sigle: 'overriding',
  libelle: 'orchestration',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
