import { ICriteresQualification, NewCriteresQualification } from './criteres-qualification.model';

export const sampleWithRequiredData: ICriteresQualification = {
  id: 72432,
};

export const sampleWithPartialData: ICriteresQualification = {
  id: 281,
  libelle: 'Dirham c Djibouti',
};

export const sampleWithFullData: ICriteresQualification = {
  id: 45498,
  libelle: 'Égypte Centralized',
};

export const sampleWithNewData: NewCriteresQualification = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
