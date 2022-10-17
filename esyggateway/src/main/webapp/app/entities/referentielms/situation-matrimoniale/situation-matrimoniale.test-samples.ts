import { ISituationMatrimoniale, NewSituationMatrimoniale } from './situation-matrimoniale.model';

export const sampleWithRequiredData: ISituationMatrimoniale = {
  id: 69407,
  libelle: 'des',
};

export const sampleWithPartialData: ISituationMatrimoniale = {
  id: 50625,
  libelle: 'productize withdrawal Rubber',
};

export const sampleWithFullData: ISituationMatrimoniale = {
  id: 21610,
  libelle: 'user-facing sensor la',
};

export const sampleWithNewData: NewSituationMatrimoniale = {
  libelle: 'b Awesome Awesome',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
