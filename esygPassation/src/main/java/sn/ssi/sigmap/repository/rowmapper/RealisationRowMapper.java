package sn.ssi.sigmap.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;
import sn.ssi.sigmap.domain.Realisation;

/**
 * Converter between {@link Row} to {@link Realisation}, with proper type conversions.
 */
@Service
public class RealisationRowMapper implements BiFunction<Row, String, Realisation> {

    private final ColumnConverter converter;

    public RealisationRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Realisation} stored in the database.
     */
    @Override
    public Realisation apply(Row row, String prefix) {
        Realisation entity = new Realisation();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setLibelle(converter.fromRow(row, prefix + "_libelle", String.class));
        entity.setDateAttribution(converter.fromRow(row, prefix + "_date_attribution", LocalDate.class));
        entity.setDelaiexecution(converter.fromRow(row, prefix + "_delaiexecution", Integer.class));
        entity.setObjet(converter.fromRow(row, prefix + "_objet", String.class));
        entity.setMontant(converter.fromRow(row, prefix + "_montant", BigDecimal.class));
        entity.setExamenDncmp(converter.fromRow(row, prefix + "_examen_dncmp", Integer.class));
        entity.setExamenPtf(converter.fromRow(row, prefix + "_examen_ptf", Integer.class));
        entity.setChapitreImputation(converter.fromRow(row, prefix + "_chapitre_imputation", String.class));
        entity.setAutorisationEngagement(converter.fromRow(row, prefix + "_autorisation_engagement", String.class));
        entity.setDateReceptionDossier(converter.fromRow(row, prefix + "_date_reception_dossier", LocalDate.class));
        entity.setDateNonObjection(converter.fromRow(row, prefix + "_date_non_objection", LocalDate.class));
        entity.setDateAutorisation(converter.fromRow(row, prefix + "_date_autorisation", LocalDate.class));
        entity.setDateRecepNonObjection(converter.fromRow(row, prefix + "_date_recep_non_objection", LocalDate.class));
        entity.setDateRecepDossCorrige(converter.fromRow(row, prefix + "_date_recep_doss_corrige", LocalDate.class));
        entity.setDatePubParPrmp(converter.fromRow(row, prefix + "_date_pub_par_prmp", LocalDate.class));
        entity.setDateOuverturePlis(converter.fromRow(row, prefix + "_date_ouverture_plis", LocalDate.class));
        entity.setDateRecepNonObjectOcmp(converter.fromRow(row, prefix + "_date_recep_non_object_ocmp", LocalDate.class));
        entity.setDateRecepRapportEva(converter.fromRow(row, prefix + "_date_recep_rapport_eva", LocalDate.class));
        entity.setDateRecepNonObjectPtf(converter.fromRow(row, prefix + "_date_recep_non_object_ptf", LocalDate.class));
        entity.setDateExamenJuridique(converter.fromRow(row, prefix + "_date_examen_juridique", LocalDate.class));
        entity.setDateNotifContrat(converter.fromRow(row, prefix + "_date_notif_contrat", LocalDate.class));
        entity.setDateApprobationContrat(converter.fromRow(row, prefix + "_date_approbation_contrat", LocalDate.class));
        entity.setPlanPassationId(converter.fromRow(row, prefix + "_plan_passation_id", Long.class));
        return entity;
    }
}
