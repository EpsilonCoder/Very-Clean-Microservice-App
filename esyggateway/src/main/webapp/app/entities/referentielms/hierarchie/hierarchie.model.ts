export interface IHierarchie {
  id: number;
  libelle?: string | null;
}

export type NewHierarchie = Omit<IHierarchie, 'id'> & { id: null };
