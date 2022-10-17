package sn.ssi.sigmap.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;
import sn.ssi.sigmap.domain.SygTypeService;

/**
 * Converter between {@link Row} to {@link SygTypeService}, with proper type conversions.
 */
@Service
public class SygTypeServiceRowMapper implements BiFunction<Row, String, SygTypeService> {

    private final ColumnConverter converter;

    public SygTypeServiceRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link SygTypeService} stored in the database.
     */
    @Override
    public SygTypeService apply(Row row, String prefix) {
        SygTypeService entity = new SygTypeService();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setLibelle(converter.fromRow(row, prefix + "_libelle", String.class));
        return entity;
    }
}
