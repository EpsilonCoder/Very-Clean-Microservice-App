export interface IBanque {
  id: number;
  libelle?: string | null;
  sigle?: string | null;
}

export type NewBanque = Omit<IBanque, 'id'> & { id: null };
