import { IModeSelection, NewModeSelection } from './mode-selection.model';

export const sampleWithRequiredData: IModeSelection = {
  id: 69552,
  libelle: 'strategic',
  code: 'Small Togo Health',
};

export const sampleWithPartialData: IModeSelection = {
  id: 67004,
  libelle: 'partnerships',
  code: 'Ngultrum Languedoc-Roussillon',
  description: 'Avon',
};

export const sampleWithFullData: IModeSelection = {
  id: 539,
  libelle: 'Espagne demand-driven',
  code: 'parse quantifying',
  description: 'Metal Sleek Antigua-et-Barbuda',
};

export const sampleWithNewData: NewModeSelection = {
  libelle: 'Shoes b payment',
  code: 'Incredible Royale',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
