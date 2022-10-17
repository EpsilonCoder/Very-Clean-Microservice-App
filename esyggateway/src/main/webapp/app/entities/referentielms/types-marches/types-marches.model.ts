export interface ITypesMarches {
  id: number;
  code?: string | null;
  libelle?: string | null;
  description?: string | null;
}

export type NewTypesMarches = Omit<ITypesMarches, 'id'> & { id: null };
