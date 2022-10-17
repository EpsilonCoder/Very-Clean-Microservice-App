import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'fonction',
        data: { pageTitle: 'esyggatewayApp.referentielmsFonction.home.title' },
        loadChildren: () => import('./referentielms/fonction/fonction.module').then(m => m.ReferentielmsFonctionModule),
      },
      {
        path: 'mode-selection',
        data: { pageTitle: 'esyggatewayApp.referentielmsModeSelection.home.title' },
        loadChildren: () => import('./referentielms/mode-selection/mode-selection.module').then(m => m.ReferentielmsModeSelectionModule),
      },
      {
        path: 'type-autorite-contractante',
        data: { pageTitle: 'esyggatewayApp.referentielmsTypeAutoriteContractante.home.title' },
        loadChildren: () =>
          import('./referentielms/type-autorite-contractante/type-autorite-contractante.module').then(
            m => m.ReferentielmsTypeAutoriteContractanteModule
          ),
      },
      {
        path: 'banque',
        data: { pageTitle: 'esyggatewayApp.referentielmsBanque.home.title' },
        loadChildren: () => import('./referentielms/banque/banque.module').then(m => m.ReferentielmsBanqueModule),
      },
      {
        path: 'criteres-qualification',
        data: { pageTitle: 'esyggatewayApp.referentielmsCriteresQualification.home.title' },
        loadChildren: () =>
          import('./referentielms/criteres-qualification/criteres-qualification.module').then(
            m => m.ReferentielmsCriteresQualificationModule
          ),
      },
      {
        path: 'departement',
        data: { pageTitle: 'esyggatewayApp.referentielmsDepartement.home.title' },
        loadChildren: () => import('./referentielms/departement/departement.module').then(m => m.ReferentielmsDepartementModule),
      },
      {
        path: 'situation-matrimoniale',
        data: { pageTitle: 'esyggatewayApp.referentielmsSituationMatrimoniale.home.title' },
        loadChildren: () =>
          import('./referentielms/situation-matrimoniale/situation-matrimoniale.module').then(
            m => m.ReferentielmsSituationMatrimonialeModule
          ),
      },
      {
        path: 'specialites-personnel',
        data: { pageTitle: 'esyggatewayApp.referentielmsSpecialitesPersonnel.home.title' },
        loadChildren: () =>
          import('./referentielms/specialites-personnel/specialites-personnel.module').then(m => m.ReferentielmsSpecialitesPersonnelModule),
      },
      {
        path: 'groupes-imputation',
        data: { pageTitle: 'esyggatewayApp.referentielmsGroupesImputation.home.title' },
        loadChildren: () =>
          import('./referentielms/groupes-imputation/groupes-imputation.module').then(m => m.ReferentielmsGroupesImputationModule),
      },
      {
        path: 'pieces-administratives',
        data: { pageTitle: 'esyggatewayApp.referentielmsPiecesAdministratives.home.title' },
        loadChildren: () =>
          import('./referentielms/pieces-administratives/pieces-administratives.module').then(
            m => m.ReferentielmsPiecesAdministrativesModule
          ),
      },
      {
        path: 'hierarchie',
        data: { pageTitle: 'esyggatewayApp.referentielmsHierarchie.home.title' },
        loadChildren: () => import('./referentielms/hierarchie/hierarchie.module').then(m => m.ReferentielmsHierarchieModule),
      },
      {
        path: 'direction',
        data: { pageTitle: 'esyggatewayApp.referentielmsDirection.home.title' },
        loadChildren: () => import('./referentielms/direction/direction.module').then(m => m.ReferentielmsDirectionModule),
      },
      {
        path: 'jours-feries',
        data: { pageTitle: 'esyggatewayApp.referentielmsJoursFeries.home.title' },
        loadChildren: () => import('./referentielms/jours-feries/jours-feries.module').then(m => m.ReferentielmsJoursFeriesModule),
      },
      {
        path: 'delais',
        data: { pageTitle: 'esyggatewayApp.referentielmsDelais.home.title' },
        loadChildren: () => import('./referentielms/delais/delais.module').then(m => m.ReferentielmsDelaisModule),
      },
      {
        path: 'garantie',
        data: { pageTitle: 'esyggatewayApp.referentielmsGarantie.home.title' },
        loadChildren: () => import('./referentielms/garantie/garantie.module').then(m => m.ReferentielmsGarantieModule),
      },
      {
        path: 'natures-garantie',
        data: { pageTitle: 'esyggatewayApp.referentielmsNaturesGarantie.home.title' },
        loadChildren: () =>
          import('./referentielms/natures-garantie/natures-garantie.module').then(m => m.ReferentielmsNaturesGarantieModule),
      },
      {
        path: 'sources-financement',
        data: { pageTitle: 'esyggatewayApp.referentielmsSourcesFinancement.home.title' },
        loadChildren: () =>
          import('./referentielms/sources-financement/sources-financement.module').then(m => m.ReferentielmsSourcesFinancementModule),
      },
      {
        path: 'categories',
        data: { pageTitle: 'esyggatewayApp.referentielmsCategories.home.title' },
        loadChildren: () => import('./referentielms/categories/categories.module').then(m => m.ReferentielmsCategoriesModule),
      },
      {
        path: 'autorite-contractante',
        data: { pageTitle: 'esyggatewayApp.referentielmsAutoriteContractante.home.title' },
        loadChildren: () =>
          import('./referentielms/autorite-contractante/autorite-contractante.module').then(m => m.ReferentielmsAutoriteContractanteModule),
      },
      {
        path: 'syg-autorite-contractante',
        data: { pageTitle: 'esyggatewayApp.referentielmsSygAutoriteContractante.home.title' },
        loadChildren: () =>
          import('./referentielms/syg-autorite-contractante/syg-autorite-contractante.module').then(
            m => m.ReferentielmsSygAutoriteContractanteModule
          ),
      },
      {
        path: 'avis-generaux',
        data: { pageTitle: 'esyggatewayApp.referentielmsAvisGeneraux.home.title' },
        loadChildren: () => import('./referentielms/avis-generaux/avis-generaux.module').then(m => m.ReferentielmsAvisGenerauxModule),
      },
      {
        path: 'types-marches',
        data: { pageTitle: 'esyggatewayApp.referentielmsTypesMarches.home.title' },
        loadChildren: () => import('./referentielms/types-marches/types-marches.module').then(m => m.ReferentielmsTypesMarchesModule),
      },
      {
        path: 'personnes-ressources',
        data: { pageTitle: 'esyggatewayApp.referentielmsPersonnesRessources.home.title' },
        loadChildren: () =>
          import('./referentielms/personnes-ressources/personnes-ressources.module').then(m => m.ReferentielmsPersonnesRessourcesModule),
      },
      {
        path: 'categorie-fournisseur',
        data: { pageTitle: 'esyggatewayApp.referentielmsCategorieFournisseur.home.title' },
        loadChildren: () =>
          import('./referentielms/categorie-fournisseur/categorie-fournisseur.module').then(m => m.ReferentielmsCategorieFournisseurModule),
      },
      {
        path: 'pays',
        data: { pageTitle: 'esyggatewayApp.referentielmsPays.home.title' },
        loadChildren: () => import('./referentielms/pays/pays.module').then(m => m.ReferentielmsPaysModule),
      },
      {
        path: 'configuration-taux',
        data: { pageTitle: 'esyggatewayApp.referentielmsConfigurationTaux.home.title' },
        loadChildren: () =>
          import('./referentielms/configuration-taux/configuration-taux.module').then(m => m.ReferentielmsConfigurationTauxModule),
      },
      {
        path: 'fournisseur',
        data: { pageTitle: 'esyggatewayApp.referentielmsFournisseur.home.title' },
        loadChildren: () => import('./referentielms/fournisseur/fournisseur.module').then(m => m.ReferentielmsFournisseurModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
