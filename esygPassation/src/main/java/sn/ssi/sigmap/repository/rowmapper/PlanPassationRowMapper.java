package sn.ssi.sigmap.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;
import sn.ssi.sigmap.domain.PlanPassation;

/**
 * Converter between {@link Row} to {@link PlanPassation}, with proper type conversions.
 */
@Service
public class PlanPassationRowMapper implements BiFunction<Row, String, PlanPassation> {

    private final ColumnConverter converter;

    public PlanPassationRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link PlanPassation} stored in the database.
     */
    @Override
    public PlanPassation apply(Row row, String prefix) {
        PlanPassation entity = new PlanPassation();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setStatut(converter.fromRow(row, prefix + "_statut", String.class));
        entity.setDateDebut(converter.fromRow(row, prefix + "_date_debut", LocalDate.class));
        entity.setDateFin(converter.fromRow(row, prefix + "_date_fin", LocalDate.class));
        entity.setCommentaire(converter.fromRow(row, prefix + "_commentaire", String.class));
        entity.setAnnee(converter.fromRow(row, prefix + "_annee", Integer.class));
        entity.setDateCreation(converter.fromRow(row, prefix + "_date_creation", LocalDate.class));
        entity.setDateMiseValidation(converter.fromRow(row, prefix + "_date_mise_validation", LocalDate.class));
        entity.setDateValidation(converter.fromRow(row, prefix + "_date_validation", LocalDate.class));
        entity.setCommentaireMiseEnValidationAC(converter.fromRow(row, prefix + "_commentaire_mise_en_validation_ac", String.class));
        entity.setReferenceMiseValidationAC(converter.fromRow(row, prefix + "_reference_mise_validation_ac", String.class));
        entity.setFichierMiseValidationACContentType(
            converter.fromRow(row, prefix + "_fichier_mise_validation_ac_content_type", String.class)
        );
        entity.setFichierMiseValidationAC(converter.fromRow(row, prefix + "_fichier_mise_validation_ac", byte[].class));
        entity.setDateMiseEnValidationCcmp(converter.fromRow(row, prefix + "_date_mise_en_validation_ccmp", LocalDate.class));
        entity.setFichierMiseValidationCcmpContentType(
            converter.fromRow(row, prefix + "_fichier_mise_validation_ccmp_content_type", String.class)
        );
        entity.setFichierMiseValidationCcmp(converter.fromRow(row, prefix + "_fichier_mise_validation_ccmp", byte[].class));
        entity.setReferenceMiseValidationCcmp(converter.fromRow(row, prefix + "_reference_mise_validation_ccmp", String.class));
        entity.setDateValidation1(converter.fromRow(row, prefix + "_date_validation_1", LocalDate.class));
        entity.setCommentaireValidation(converter.fromRow(row, prefix + "_commentaire_validation", String.class));
        entity.setReferenceValidation(converter.fromRow(row, prefix + "_reference_validation", String.class));
        entity.setFichierValidationContentType(converter.fromRow(row, prefix + "_fichier_validation_content_type", String.class));
        entity.setFichierValidation(converter.fromRow(row, prefix + "_fichier_validation", byte[].class));
        entity.setDateValidation2(converter.fromRow(row, prefix + "_date_validation_2", LocalDate.class));
        entity.setDateRejet(converter.fromRow(row, prefix + "_date_rejet", LocalDate.class));
        entity.setDatePublication(converter.fromRow(row, prefix + "_date_publication", LocalDate.class));
        entity.setCommentairePublication(converter.fromRow(row, prefix + "_commentaire_publication", String.class));
        return entity;
    }
}
