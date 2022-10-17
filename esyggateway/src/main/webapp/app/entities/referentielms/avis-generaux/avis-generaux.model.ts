import dayjs from 'dayjs/esm';

export interface IAvisGeneraux {
  id: number;
  numero?: string | null;
  annee?: string | null;
  description?: string | null;
  fichierAvis?: string | null;
  fichierAvisContentType?: string | null;
  version?: number | null;
  lastVersionValid?: number | null;
  etat?: string | null;
  datePublication?: dayjs.Dayjs | null;
}

export type NewAvisGeneraux = Omit<IAvisGeneraux, 'id'> & { id: null };
