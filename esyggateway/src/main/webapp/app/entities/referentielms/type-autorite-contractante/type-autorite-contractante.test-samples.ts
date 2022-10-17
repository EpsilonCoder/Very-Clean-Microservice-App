import { ITypeAutoriteContractante, NewTypeAutoriteContractante } from './type-autorite-contractante.model';

export const sampleWithRequiredData: ITypeAutoriteContractante = {
  id: 72005,
  libelle: 'Computer robust mint',
  code: 'Bourgogne ',
  ordre: 56702,
  chapitre: 'Health',
};

export const sampleWithPartialData: ITypeAutoriteContractante = {
  id: 26514,
  libelle: 'Computer',
  code: 'Steel over',
  description: 'JBOD',
  ordre: 62637,
  chapitre: 'hardware',
};

export const sampleWithFullData: ITypeAutoriteContractante = {
  id: 65493,
  libelle: 'copying Fantastic EXE',
  code: 'invoice d’',
  description: 'b Pays',
  ordre: 67330,
  chapitre: 'deposit Bo',
};

export const sampleWithNewData: NewTypeAutoriteContractante = {
  libelle: 'Salad Automotive Buckinghamshire',
  code: 'Metal Cott',
  ordre: 44796,
  chapitre: 'Avon Accou',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
