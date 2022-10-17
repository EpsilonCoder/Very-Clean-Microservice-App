export interface ICriteresQualification {
  id: number;
  libelle?: string | null;
}

export type NewCriteresQualification = Omit<ICriteresQualification, 'id'> & { id: null };
