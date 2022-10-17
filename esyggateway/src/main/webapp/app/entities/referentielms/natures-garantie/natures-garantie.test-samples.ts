import { INaturesGarantie, NewNaturesGarantie } from './natures-garantie.model';

export const sampleWithRequiredData: INaturesGarantie = {
  id: 12397,
  libelle: 'monetize innovate',
};

export const sampleWithPartialData: INaturesGarantie = {
  id: 47454,
  libelle: 'a frictionless Fresh',
};

export const sampleWithFullData: INaturesGarantie = {
  id: 38297,
  libelle: 'transmitting Fish lime',
};

export const sampleWithNewData: NewNaturesGarantie = {
  libelle: 'transparent Keyboard',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
