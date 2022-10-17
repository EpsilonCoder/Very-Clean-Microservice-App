package sn.ssi.sigmap.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class HistoriqueSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("etat", table, columnPrefix + "_etat"));
        columns.add(Column.aliased("date_rejet", table, columnPrefix + "_date_rejet"));
        columns.add(Column.aliased("date_rejet_2", table, columnPrefix + "_date_rejet_2"));
        columns.add(Column.aliased("date_mise_validation", table, columnPrefix + "_date_mise_validation"));
        columns.add(Column.aliased("commentaire_rejet", table, columnPrefix + "_commentaire_rejet"));
        columns.add(Column.aliased("commentaire_mise_validation", table, columnPrefix + "_commentaire_mise_validation"));
        columns.add(Column.aliased("fichier_mise_validation", table, columnPrefix + "_fichier_mise_validation"));
        columns.add(Column.aliased("fichier_mise_validation_content_type", table, columnPrefix + "_fichier_mise_validation_content_type"));
        columns.add(Column.aliased("fichier_rejet", table, columnPrefix + "_fichier_rejet"));
        columns.add(Column.aliased("fichier_rejet_content_type", table, columnPrefix + "_fichier_rejet_content_type"));
        columns.add(Column.aliased("etat_2", table, columnPrefix + "_etat_2"));
        columns.add(Column.aliased("commentaire_rejet_2", table, columnPrefix + "_commentaire_rejet_2"));
        columns.add(Column.aliased("fichier_rejet_2", table, columnPrefix + "_fichier_rejet_2"));
        columns.add(Column.aliased("fichier_rejet_2_content_type", table, columnPrefix + "_fichier_rejet_2_content_type"));

        columns.add(Column.aliased("plan_passation_id", table, columnPrefix + "_plan_passation_id"));
        return columns;
    }
}
