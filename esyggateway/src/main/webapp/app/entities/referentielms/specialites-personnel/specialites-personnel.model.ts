export interface ISpecialitesPersonnel {
  id: number;
  libelle?: string | null;
}

export type NewSpecialitesPersonnel = Omit<ISpecialitesPersonnel, 'id'> & { id: null };
