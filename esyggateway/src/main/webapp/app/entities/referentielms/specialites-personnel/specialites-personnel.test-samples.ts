import { ISpecialitesPersonnel, NewSpecialitesPersonnel } from './specialites-personnel.model';

export const sampleWithRequiredData: ISpecialitesPersonnel = {
  id: 75878,
  libelle: 'a',
};

export const sampleWithPartialData: ISpecialitesPersonnel = {
  id: 6746,
  libelle: 'Alsace sky',
};

export const sampleWithFullData: ISpecialitesPersonnel = {
  id: 18060,
  libelle: 'Industrial',
};

export const sampleWithNewData: NewSpecialitesPersonnel = {
  libelle: 'XML a up',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
