export interface ITypeAutoriteContractante {
  id: number;
  libelle?: string | null;
  code?: string | null;
  description?: string | null;
  ordre?: number | null;
  chapitre?: string | null;
}

export type NewTypeAutoriteContractante = Omit<ITypeAutoriteContractante, 'id'> & { id: null };
