import { IHierarchie, NewHierarchie } from './hierarchie.model';

export const sampleWithRequiredData: IHierarchie = {
  id: 80615,
  libelle: '3rd Movies interfaces',
};

export const sampleWithPartialData: IHierarchie = {
  id: 67656,
  libelle: 'executive Cambridgeshire attitude-oriented',
};

export const sampleWithFullData: IHierarchie = {
  id: 57922,
  libelle: 'Car',
};

export const sampleWithNewData: NewHierarchie = {
  libelle: 'Rhône-Alpes SQL',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
