import dayjs from 'dayjs/esm';

import { IFournisseur, NewFournisseur } from './fournisseur.model';

export const sampleWithRequiredData: IFournisseur = {
  id: 12366,
  raisonSociale: 'Hryvnia reinvent radical',
  adresse: 'b compress c',
  email: 'Constance94@hotmail.fr',
  telephone: '+33 449398672',
  date: dayjs('2021-11-03T22:24'),
};

export const sampleWithPartialData: IFournisseur = {
  id: 48941,
  raisonSociale: 'Digitized composite JSON',
  adresse: 'navigating',
  email: 'Anglina_Andre20@hotmail.fr',
  telephone: '+33 750429896',
  pieceJointe: 'bus',
  date: dayjs('2021-11-03T13:57'),
  numeroIdentiteFiscale: 'Tilsitt Venezuela Frozen',
};

export const sampleWithFullData: IFournisseur = {
  id: 45215,
  raisonSociale: 'Unbranded Alsace Steel',
  adresse: 'b Albanie',
  email: 'Mireille.Meyer40@yahoo.fr',
  telephone: '+33 353675826',
  pieceJointe: 'Handmade Cuban eco-centric',
  numeroRegistreCommerce: 'evolve microchip Sud',
  date: dayjs('2021-11-03T22:27'),
  sigle: 'Turquie quantify Soft',
  numeroIdentiteFiscale: 'ivory bypass',
};

export const sampleWithNewData: NewFournisseur = {
  raisonSociale: 'reinvent Sports Cheese',
  adresse: 'Bacon THX Synergized',
  email: 'Maxellende35@gmail.com',
  telephone: '0630758960',
  date: dayjs('2021-11-03T22:13'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
