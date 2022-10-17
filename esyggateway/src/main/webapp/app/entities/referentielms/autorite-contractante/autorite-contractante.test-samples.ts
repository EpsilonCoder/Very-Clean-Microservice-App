import { IAutoriteContractante, NewAutoriteContractante } from './autorite-contractante.model';

export const sampleWithRequiredData: IAutoriteContractante = {
  id: 86062,
  ordre: 27605,
  denomination: 'a facilitate',
  responsable: 'Cheese networks program',
  adresse: 'alliance Caribbean',
  telephone: '+33 497026627',
  email: 'Agapet.Fontaine@hotmail.fr',
  sigle: 'Kids upward-trending magenta',
  approbation: 'b Nord-Pas-de-Calais Gibraltar',
};

export const sampleWithPartialData: IAutoriteContractante = {
  id: 58469,
  ordre: 47877,
  denomination: 'B2C',
  responsable: 'virtual withdrawal',
  adresse: 'Granite',
  telephone: '0183526989',
  fax: 'methodologies',
  email: 'Adegrine_Andre65@gmail.com',
  sigle: 'Savings',
  urlsiteweb: 'analyzer Shoes',
  approbation: 'Steel application',
  logo: '../fake-data/blob/hipster.png',
  logoContentType: 'unknown',
};

export const sampleWithFullData: IAutoriteContractante = {
  id: 12082,
  ordre: 44039,
  denomination: 'TCP',
  responsable: 'Wooden Re-contextualized',
  adresse: 'AGP',
  telephone: '0384751511',
  fax: 'Azerbaijanian Savings',
  email: 'Azeline_Meunier@yahoo.fr',
  sigle: 'calculating maximized Gloves',
  urlsiteweb: 'synthesize optical',
  approbation: 'Integrated invoice',
  logo: '../fake-data/blob/hipster.png',
  logoContentType: 'unknown',
};

export const sampleWithNewData: NewAutoriteContractante = {
  ordre: 18453,
  denomination: 'Specialiste Cambridgeshire repurpose',
  responsable: 'c',
  adresse: 'index Incredible moderator',
  telephone: '+33 176756786',
  email: 'Clio55@hotmail.fr',
  sigle: '17(E.U.A.-17) Bedfordshire index',
  approbation: 'up',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
