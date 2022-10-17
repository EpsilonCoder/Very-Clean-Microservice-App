export interface ISituationMatrimoniale {
  id: number;
  libelle?: string | null;
}

export type NewSituationMatrimoniale = Omit<ISituationMatrimoniale, 'id'> & { id: null };
