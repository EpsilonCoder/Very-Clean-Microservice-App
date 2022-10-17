export interface IDirection {
  id: number;
  sigle?: string | null;
  libelle?: string | null;
  description?: string | null;
}

export type NewDirection = Omit<IDirection, 'id'> & { id: null };
