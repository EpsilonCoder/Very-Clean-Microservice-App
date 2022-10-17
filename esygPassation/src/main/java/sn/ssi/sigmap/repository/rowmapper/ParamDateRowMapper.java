package sn.ssi.sigmap.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;
import sn.ssi.sigmap.domain.ParamDate;

/**
 * Converter between {@link Row} to {@link ParamDate}, with proper type conversions.
 */
@Service
public class ParamDateRowMapper implements BiFunction<Row, String, ParamDate> {

    private final ColumnConverter converter;

    public ParamDateRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link ParamDate} stored in the database.
     */
    @Override
    public ParamDate apply(Row row, String prefix) {
        ParamDate entity = new ParamDate();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setDateCreat(converter.fromRow(row, prefix + "_date_creat", LocalDate.class));
        return entity;
    }
}
