import { IGroupesImputation, NewGroupesImputation } from './groupes-imputation.model';

export const sampleWithRequiredData: IGroupesImputation = {
  id: 7662,
  libelle: 'Credit parsing Shirt',
};

export const sampleWithPartialData: IGroupesImputation = {
  id: 98981,
  libelle: 'blue mission-critical',
  description: 'Realigned Denar Refined',
};

export const sampleWithFullData: IGroupesImputation = {
  id: 58508,
  libelle: 'Wooden magenta',
  description: 'la brand',
};

export const sampleWithNewData: NewGroupesImputation = {
  libelle: 'matrix',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
