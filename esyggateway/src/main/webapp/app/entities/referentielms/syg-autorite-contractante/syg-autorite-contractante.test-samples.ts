import { ISygAutoriteContractante, NewSygAutoriteContractante } from './syg-autorite-contractante.model';

export const sampleWithRequiredData: ISygAutoriteContractante = {
  id: 17832,
  ordre: 71312,
  denomination: 'c',
  responsable: 'Centre Ruble',
  adresse: 'Namibia Bedfordshire calculating',
  telephone: '+33 364515882',
  email: 'Ariane.Roux@hotmail.fr',
  sigle: 'connecting',
};

export const sampleWithPartialData: ISygAutoriteContractante = {
  id: 11015,
  ordre: 57759,
  denomination: 'Fundamental',
  responsable: 'Multi-layered c global',
  adresse: 'Universal wireless',
  telephone: '0281669196',
  email: 'Lazare53@hotmail.fr',
  sigle: 'Fresh b',
  urlsiteweb: 'c',
  approbation: 'back-end out-of-the-box sensor',
  logo: '../fake-data/blob/hipster.png',
  logoContentType: 'unknown',
};

export const sampleWithFullData: ISygAutoriteContractante = {
  id: 67070,
  ordre: 85396,
  denomination: 'bypass',
  responsable: 'synergies copy',
  adresse: 'plum innovate',
  telephone: '+33 125157490',
  fax: 'Bedfordshire',
  email: 'Leu_Barre33@yahoo.fr',
  sigle: 'hack b',
  urlsiteweb: 'mesh card',
  approbation: 'Cross-platform Avon',
  logo: '../fake-data/blob/hipster.png',
  logoContentType: 'unknown',
};

export const sampleWithNewData: NewSygAutoriteContractante = {
  ordre: 25341,
  denomination: 'haptic b open-source',
  responsable: 'generating infomediaries',
  adresse: 'protocol',
  telephone: '+33 284644512',
  email: 'Aurore_Mercier@hotmail.fr',
  sigle: 'withdrawal',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
