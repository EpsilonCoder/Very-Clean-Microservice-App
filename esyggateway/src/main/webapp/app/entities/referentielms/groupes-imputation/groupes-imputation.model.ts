export interface IGroupesImputation {
  id: number;
  libelle?: string | null;
  description?: string | null;
}

export type NewGroupesImputation = Omit<IGroupesImputation, 'id'> & { id: null };
