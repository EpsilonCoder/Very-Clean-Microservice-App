package sn.ssi.sigmap.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;
import sn.ssi.sigmap.domain.SygSourceFinancement;

/**
 * Converter between {@link Row} to {@link SygSourceFinancement}, with proper type conversions.
 */
@Service
public class SygSourceFinancementRowMapper implements BiFunction<Row, String, SygSourceFinancement> {

    private final ColumnConverter converter;

    public SygSourceFinancementRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link SygSourceFinancement} stored in the database.
     */
    @Override
    public SygSourceFinancement apply(Row row, String prefix) {
        SygSourceFinancement entity = new SygSourceFinancement();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setLibelle(converter.fromRow(row, prefix + "_libelle", String.class));
        entity.setSygTypeSourceFinancementId(converter.fromRow(row, prefix + "_syg_type_source_financement_id", Long.class));
        return entity;
    }
}
