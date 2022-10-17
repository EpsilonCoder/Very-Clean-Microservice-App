export interface INaturesGarantie {
  id: number;
  libelle?: string | null;
}

export type NewNaturesGarantie = Omit<INaturesGarantie, 'id'> & { id: null };
