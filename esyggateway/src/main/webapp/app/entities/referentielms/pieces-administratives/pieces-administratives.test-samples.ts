import { enumLocalisation } from 'app/entities/enumerations/enum-localisation.model';

import { IPiecesAdministratives, NewPiecesAdministratives } from './pieces-administratives.model';

export const sampleWithRequiredData: IPiecesAdministratives = {
  id: 4104,
  code: 'metrics',
  libelle: 'Digitized silver a',
  localisation: enumLocalisation['LOCALES'],
};

export const sampleWithPartialData: IPiecesAdministratives = {
  id: 91887,
  code: 'lime Saint-Bernard',
  libelle: 'Executif',
  localisation: enumLocalisation['LOCALES'],
  type: 55361,
};

export const sampleWithFullData: IPiecesAdministratives = {
  id: 4744,
  code: 'Open-source',
  libelle: 'Coordinateur',
  description: 'Concrete bus',
  localisation: enumLocalisation['LESDEUX'],
  type: 29898,
};

export const sampleWithNewData: NewPiecesAdministratives = {
  code: 'auxiliary Grass-roots',
  libelle: 'Frozen Steel JBOD',
  localisation: enumLocalisation['ETRANGERES'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
