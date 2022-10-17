export interface IModeSelection {
  id: number;
  libelle?: string | null;
  code?: string | null;
  description?: string | null;
}

export type NewModeSelection = Omit<IModeSelection, 'id'> & { id: null };
