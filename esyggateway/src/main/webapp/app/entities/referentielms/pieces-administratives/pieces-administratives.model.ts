import { enumLocalisation } from 'app/entities/enumerations/enum-localisation.model';

export interface IPiecesAdministratives {
  id: number;
  code?: string | null;
  libelle?: string | null;
  description?: string | null;
  localisation?: enumLocalisation | null;
  type?: number | null;
}

export type NewPiecesAdministratives = Omit<IPiecesAdministratives, 'id'> & { id: null };
