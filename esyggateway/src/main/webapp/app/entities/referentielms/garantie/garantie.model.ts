export interface IGarantie {
  id: number;
  libelle?: string | null;
  typeGarantie?: string | null;
  description?: string | null;
}

export type NewGarantie = Omit<IGarantie, 'id'> & { id: null };
