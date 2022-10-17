import { ICategories, NewCategories } from './categories.model';

export const sampleWithRequiredData: ICategories = {
  id: 71041,
  code: 'withdrawal Towels 6th',
  designation: 'base driver',
};

export const sampleWithPartialData: ICategories = {
  id: 42298,
  code: 'maximize implementation cyan',
  designation: 'SQL systemic',
  commentaire: 'c',
};

export const sampleWithFullData: ICategories = {
  id: 88028,
  code: 'GB',
  designation: "Libyan d'Argenteuil Superviseur",
  commentaire: 'Midi-Pyrénées HDD',
};

export const sampleWithNewData: NewCategories = {
  code: '24/7 Ukraine c',
  designation: 'violet Multi-lateral Gold',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
