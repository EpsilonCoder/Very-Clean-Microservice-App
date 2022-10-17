package sn.ssi.sigmap.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;
import sn.ssi.sigmap.domain.SygTypeMarche;

/**
 * Converter between {@link Row} to {@link SygTypeMarche}, with proper type conversions.
 */
@Service
public class SygTypeMarcheRowMapper implements BiFunction<Row, String, SygTypeMarche> {

    private final ColumnConverter converter;

    public SygTypeMarcheRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link SygTypeMarche} stored in the database.
     */
    @Override
    public SygTypeMarche apply(Row row, String prefix) {
        SygTypeMarche entity = new SygTypeMarche();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setCode(converter.fromRow(row, prefix + "_code", String.class));
        entity.setLibelle(converter.fromRow(row, prefix + "_libelle", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        return entity;
    }
}
