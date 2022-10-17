import dayjs from 'dayjs/esm';

import { IAvisGeneraux, NewAvisGeneraux } from './avis-generaux.model';

export const sampleWithRequiredData: IAvisGeneraux = {
  id: 32156,
};

export const sampleWithPartialData: IAvisGeneraux = {
  id: 82597,
  annee: 'Libye Soft Cambridgeshire',
  fichierAvis: '../fake-data/blob/hipster.png',
  fichierAvisContentType: 'unknown',
  lastVersionValid: 40834,
  datePublication: dayjs('2021-09-23T04:58'),
};

export const sampleWithFullData: IAvisGeneraux = {
  id: 40474,
  numero: 'Metal purple',
  annee: 'firmware synthesizing',
  description: 'deliverables',
  fichierAvis: '../fake-data/blob/hipster.png',
  fichierAvisContentType: 'unknown',
  version: 53276,
  lastVersionValid: 32106,
  etat: 'Namibie robust',
  datePublication: dayjs('2021-09-23T08:13'),
};

export const sampleWithNewData: NewAvisGeneraux = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
