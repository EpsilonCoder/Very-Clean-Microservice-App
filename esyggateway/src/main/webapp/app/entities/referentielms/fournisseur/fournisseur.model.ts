import dayjs from 'dayjs/esm';
import { ICategorieFournisseur } from 'app/entities/referentielms/categorie-fournisseur/categorie-fournisseur.model';
import { IPays } from 'app/entities/referentielms/pays/pays.model';

export interface IFournisseur {
  id: number;
  raisonSociale?: string | null;
  adresse?: string | null;
  email?: string | null;
  telephone?: string | null;
  pieceJointe?: string | null;
  numeroRegistreCommerce?: string | null;
  date?: dayjs.Dayjs | null;
  sigle?: string | null;
  numeroIdentiteFiscale?: string | null;
  categorieFournisseur?: Pick<ICategorieFournisseur, 'id'> | null;
  pays?: Pick<IPays, 'id'> | null;
}

export type NewFournisseur = Omit<IFournisseur, 'id'> & { id: null };
