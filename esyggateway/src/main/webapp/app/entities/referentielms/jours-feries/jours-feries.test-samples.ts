import dayjs from 'dayjs/esm';

import { IJoursFeries, NewJoursFeries } from './jours-feries.model';

export const sampleWithRequiredData: IJoursFeries = {
  id: 73483,
  date: dayjs('2021-08-13T14:17'),
};

export const sampleWithPartialData: IJoursFeries = {
  id: 62612,
  date: dayjs('2021-08-14T06:22'),
};

export const sampleWithFullData: IJoursFeries = {
  id: 95265,
  date: dayjs('2021-08-13T17:40'),
  description: 'Loan',
};

export const sampleWithNewData: NewJoursFeries = {
  date: dayjs('2021-08-14T01:01'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
