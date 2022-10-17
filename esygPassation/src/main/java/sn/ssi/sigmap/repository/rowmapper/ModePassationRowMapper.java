package sn.ssi.sigmap.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;
import sn.ssi.sigmap.domain.ModePassation;

/**
 * Converter between {@link Row} to {@link ModePassation}, with proper type conversions.
 */
@Service
public class ModePassationRowMapper implements BiFunction<Row, String, ModePassation> {

    private final ColumnConverter converter;

    public ModePassationRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link ModePassation} stored in the database.
     */
    @Override
    public ModePassation apply(Row row, String prefix) {
        ModePassation entity = new ModePassation();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setLibelle(converter.fromRow(row, prefix + "_libelle", String.class));
        entity.setCode(converter.fromRow(row, prefix + "_code", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        return entity;
    }
}
