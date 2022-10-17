import dayjs from 'dayjs/esm';

export interface IDelais {
  id: number;
  libelle?: string | null;
  code?: string | null;
  unite?: string | null;
  valeur?: number | null;
  debutValidite?: dayjs.Dayjs | null;
  finValidite?: dayjs.Dayjs | null;
  commentaires?: string | null;
}

export type NewDelais = Omit<IDelais, 'id'> & { id: null };
