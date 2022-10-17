export interface ICategorieFournisseur {
  id: number;
  libelle?: string | null;
  description?: string | null;
}

export type NewCategorieFournisseur = Omit<ICategorieFournisseur, 'id'> & { id: null };
