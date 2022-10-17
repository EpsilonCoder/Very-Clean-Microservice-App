export interface IPays {
  id: number;
  libelle?: string | null;
  codePays?: string | null;
}

export type NewPays = Omit<IPays, 'id'> & { id: null };
