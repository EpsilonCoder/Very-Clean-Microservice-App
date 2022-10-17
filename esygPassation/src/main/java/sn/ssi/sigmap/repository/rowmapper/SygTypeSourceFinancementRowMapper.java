package sn.ssi.sigmap.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;
import sn.ssi.sigmap.domain.SygTypeSourceFinancement;

/**
 * Converter between {@link Row} to {@link SygTypeSourceFinancement}, with proper type conversions.
 */
@Service
public class SygTypeSourceFinancementRowMapper implements BiFunction<Row, String, SygTypeSourceFinancement> {

    private final ColumnConverter converter;

    public SygTypeSourceFinancementRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link SygTypeSourceFinancement} stored in the database.
     */
    @Override
    public SygTypeSourceFinancement apply(Row row, String prefix) {
        SygTypeSourceFinancement entity = new SygTypeSourceFinancement();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setLibelle(converter.fromRow(row, prefix + "_libelle", String.class));
        return entity;
    }
}
