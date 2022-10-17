import dayjs from 'dayjs/esm';

export interface IJoursFeries {
  id: number;
  date?: dayjs.Dayjs | null;
  description?: string | null;
}

export type NewJoursFeries = Omit<IJoursFeries, 'id'> & { id: null };
