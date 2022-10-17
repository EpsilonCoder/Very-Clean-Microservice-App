export interface ICategories {
  id: number;
  code?: string | null;
  designation?: string | null;
  commentaire?: string | null;
}

export type NewCategories = Omit<ICategories, 'id'> & { id: null };
