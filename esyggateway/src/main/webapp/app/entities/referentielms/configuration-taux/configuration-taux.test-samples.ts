import dayjs from 'dayjs/esm';

import { IConfigurationTaux, NewConfigurationTaux } from './configuration-taux.model';

export const sampleWithRequiredData: IConfigurationTaux = {
  id: 25980,
  dateDebut: dayjs('2021-11-03T18:27'),
  dateFin: dayjs('2021-11-03T13:45'),
  invalid: false,
};

export const sampleWithPartialData: IConfigurationTaux = {
  id: 90597,
  code: 'system Vaugirard',
  libelle: 'website',
  taux: 6838,
  dateDebut: dayjs('2021-11-03T22:30'),
  dateFin: dayjs('2021-11-03T22:27'),
  invalid: false,
};

export const sampleWithFullData: IConfigurationTaux = {
  id: 45770,
  code: 'Architecte circuit transmitter',
  libelle: 'empowering',
  taux: 11393,
  dateDebut: dayjs('2021-11-04T06:31'),
  dateFin: dayjs('2021-11-03T22:12'),
  invalid: true,
};

export const sampleWithNewData: NewConfigurationTaux = {
  dateDebut: dayjs('2021-11-03T16:08'),
  dateFin: dayjs('2021-11-03T17:19'),
  invalid: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
