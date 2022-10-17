import dayjs from 'dayjs/esm';

import { IDelais, NewDelais } from './delais.model';

export const sampleWithRequiredData: IDelais = {
  id: 48735,
  libelle: 'primary projection',
  code: 'open-source',
  unite: 'JBOD',
  valeur: 65115,
  debutValidite: dayjs('2021-08-14T04:22'),
  finValidite: dayjs('2021-08-14T00:15'),
};

export const sampleWithPartialData: IDelais = {
  id: 63065,
  libelle: 'Ingenieur action-items systems',
  code: 'Mandatory content-based',
  unite: 'B2B Burundi',
  valeur: 41962,
  debutValidite: dayjs('2021-08-13T14:52'),
  finValidite: dayjs('2021-08-13T20:42'),
};

export const sampleWithFullData: IDelais = {
  id: 9295,
  libelle: 'Franche-Comté',
  code: 'Vision-oriented',
  unite: 'Avon backing Avon',
  valeur: 73704,
  debutValidite: dayjs('2021-08-13T17:51'),
  finValidite: dayjs('2021-08-14T07:54'),
  commentaires: 'Electronics Picardie',
};

export const sampleWithNewData: NewDelais = {
  libelle: 'Uruguay quantifying Concrete',
  code: 'Account Fish',
  unite: 'Centre Borders',
  valeur: 13091,
  debutValidite: dayjs('2021-08-14T08:41'),
  finValidite: dayjs('2021-08-13T23:23'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
