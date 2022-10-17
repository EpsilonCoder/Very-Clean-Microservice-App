package sn.ssi.sigmap.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;
import sn.ssi.sigmap.domain.Historique;

/**
 * Converter between {@link Row} to {@link Historique}, with proper type conversions.
 */
@Service
public class HistoriqueRowMapper implements BiFunction<Row, String, Historique> {

    private final ColumnConverter converter;

    public HistoriqueRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Historique} stored in the database.
     */
    @Override
    public Historique apply(Row row, String prefix) {
        Historique entity = new Historique();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setEtat(converter.fromRow(row, prefix + "_etat", String.class));
        entity.setDateRejet(converter.fromRow(row, prefix + "_date_rejet", LocalDate.class));
        entity.setDateRejet2(converter.fromRow(row, prefix + "_date_rejet_2", LocalDate.class));
        entity.setDateMiseValidation(converter.fromRow(row, prefix + "_date_mise_validation", LocalDate.class));
        entity.setCommentaireRejet(converter.fromRow(row, prefix + "_commentaire_rejet", String.class));
        entity.setCommentaireMiseValidation(converter.fromRow(row, prefix + "_commentaire_mise_validation", String.class));
        entity.setFichierMiseValidationContentType(converter.fromRow(row, prefix + "_fichier_mise_validation_content_type", String.class));
        entity.setFichierMiseValidation(converter.fromRow(row, prefix + "_fichier_mise_validation", byte[].class));
        entity.setFichierRejetContentType(converter.fromRow(row, prefix + "_fichier_rejet_content_type", String.class));
        entity.setFichierRejet(converter.fromRow(row, prefix + "_fichier_rejet", byte[].class));
        entity.setEtat2(converter.fromRow(row, prefix + "_etat_2", String.class));
        entity.setCommentaireRejet2(converter.fromRow(row, prefix + "_commentaire_rejet_2", String.class));
        entity.setFichierRejet2ContentType(converter.fromRow(row, prefix + "_fichier_rejet_2_content_type", String.class));
        entity.setFichierRejet2(converter.fromRow(row, prefix + "_fichier_rejet_2", byte[].class));
        entity.setPlanPassationId(converter.fromRow(row, prefix + "_plan_passation_id", Long.class));
        return entity;
    }
}
