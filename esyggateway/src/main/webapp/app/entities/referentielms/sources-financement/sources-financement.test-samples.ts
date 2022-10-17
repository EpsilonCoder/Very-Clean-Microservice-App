import { ISourcesFinancement, NewSourcesFinancement } from './sources-financement.model';

export const sampleWithRequiredData: ISourcesFinancement = {
  id: 88135,
  code: 'Handmade Poitou-Charentes',
  libelle: 'Granite',
};

export const sampleWithPartialData: ISourcesFinancement = {
  id: 16876,
  code: 'Assistant Keyboard a',
  libelle: 'e-tailers Pants open-source',
  corbeille: 'b',
};

export const sampleWithFullData: ISourcesFinancement = {
  id: 72326,
  code: 'plug-and-play B2C Concrete',
  libelle: 'Ball payment benchmark',
  corbeille: 'Loan repurpose incremental',
};

export const sampleWithNewData: NewSourcesFinancement = {
  code: 'payment Rubber',
  libelle: 'c',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
