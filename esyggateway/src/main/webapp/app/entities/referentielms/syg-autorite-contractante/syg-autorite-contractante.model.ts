import { ITypeAutoriteContractante } from 'app/entities/referentielms/type-autorite-contractante/type-autorite-contractante.model';

export interface ISygAutoriteContractante {
  id: number;
  ordre?: number | null;
  denomination?: string | null;
  responsable?: string | null;
  adresse?: string | null;
  telephone?: string | null;
  fax?: string | null;
  email?: string | null;
  sigle?: string | null;
  urlsiteweb?: string | null;
  approbation?: string | null;
  logo?: string | null;
  logoContentType?: string | null;
  typeAutoriteContractante?: Pick<ITypeAutoriteContractante, 'id'> | null;
}

export type NewSygAutoriteContractante = Omit<ISygAutoriteContractante, 'id'> & { id: null };
