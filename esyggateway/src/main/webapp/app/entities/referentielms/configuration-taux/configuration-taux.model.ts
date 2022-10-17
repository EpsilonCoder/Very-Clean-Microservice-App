import dayjs from 'dayjs/esm';
import { IPays } from 'app/entities/referentielms/pays/pays.model';

export interface IConfigurationTaux {
  id: number;
  code?: string | null;
  libelle?: string | null;
  taux?: number | null;
  dateDebut?: dayjs.Dayjs | null;
  dateFin?: dayjs.Dayjs | null;
  invalid?: boolean | null;
  pays?: Pick<IPays, 'id'> | null;
}

export type NewConfigurationTaux = Omit<IConfigurationTaux, 'id'> & { id: null };
