export interface ISourcesFinancement {
  id: number;
  code?: string | null;
  libelle?: string | null;
  corbeille?: string | null;
}

export type NewSourcesFinancement = Omit<ISourcesFinancement, 'id'> & { id: null };
