package sn.ssi.sigmap.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;
import sn.ssi.sigmap.domain.SygService;

/**
 * Converter between {@link Row} to {@link SygService}, with proper type conversions.
 */
@Service
public class SygServiceRowMapper implements BiFunction<Row, String, SygService> {

    private final ColumnConverter converter;

    public SygServiceRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link SygService} stored in the database.
     */
    @Override
    public SygService apply(Row row, String prefix) {
        SygService entity = new SygService();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setCode(converter.fromRow(row, prefix + "_code", String.class));
        entity.setLibelle(converter.fromRow(row, prefix + "_libelle", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setSygTypeServiceId(converter.fromRow(row, prefix + "_syg_type_service_id", Long.class));
        return entity;
    }
}
