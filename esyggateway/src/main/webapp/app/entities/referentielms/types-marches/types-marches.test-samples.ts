import { ITypesMarches, NewTypesMarches } from './types-marches.model';

export const sampleWithRequiredData: ITypesMarches = {
  id: 40595,
  code: 'innovative AI',
  libelle: 'drive',
  description: 'engineer Horizontal',
};

export const sampleWithPartialData: ITypesMarches = {
  id: 19080,
  code: 'Lepic',
  libelle: 'a withdrawal integrated',
  description: 'attitude Money reboot',
};

export const sampleWithFullData: ITypesMarches = {
  id: 28254,
  code: 'Mouse virtual',
  libelle: 'withdrawal Berkshire Lorraine',
  description: 'maximize benchmark Ergonomic',
};

export const sampleWithNewData: NewTypesMarches = {
  code: 'seize blue',
  libelle: 'c Liban Universal',
  description: 'granular override',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
