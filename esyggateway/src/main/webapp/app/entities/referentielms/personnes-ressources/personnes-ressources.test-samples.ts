import { IPersonnesRessources, NewPersonnesRessources } from './personnes-ressources.model';

export const sampleWithRequiredData: IPersonnesRessources = {
  id: 40590,
  prenom: 'alarm even-keeled Superviseur',
  nom: 'Chips Car system',
  email: 'Jeanne.Gaillard53@gmail.com',
  telephone: 50259,
  fonction: 'Health Optional',
};

export const sampleWithPartialData: IPersonnesRessources = {
  id: 17134,
  prenom: 'lime Towels',
  nom: 'Gorgeous intuitive Fresh',
  email: 'Thophile_Meunier75@gmail.com',
  telephone: 52856,
  fonction: 'auxiliary Ghana Picardie',
  commentaires: 'Cloned',
};

export const sampleWithFullData: IPersonnesRessources = {
  id: 73070,
  prenom: 'Liechtenstein Account',
  nom: 'Philippines',
  email: 'Alix_Michel80@yahoo.fr',
  telephone: 66699,
  fonction: 'Chicken Ergonomic',
  commentaires: 'deposit',
};

export const sampleWithNewData: NewPersonnesRessources = {
  prenom: 'Awesome',
  nom: 'composite bandwidth Metal',
  email: 'Mayeul7@yahoo.fr',
  telephone: 16965,
  fonction: 'Tilsitt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
