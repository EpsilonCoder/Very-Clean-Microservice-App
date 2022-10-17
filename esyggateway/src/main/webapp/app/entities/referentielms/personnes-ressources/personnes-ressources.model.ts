export interface IPersonnesRessources {
  id: number;
  prenom?: string | null;
  nom?: string | null;
  email?: string | null;
  telephone?: number | null;
  fonction?: string | null;
  commentaires?: string | null;
}

export type NewPersonnesRessources = Omit<IPersonnesRessources, 'id'> & { id: null };
