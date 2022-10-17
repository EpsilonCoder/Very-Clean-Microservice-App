package sn.ssi.sigmap.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class PlanPassationSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("statut", table, columnPrefix + "_statut"));
        columns.add(Column.aliased("date_debut", table, columnPrefix + "_date_debut"));
        columns.add(Column.aliased("date_fin", table, columnPrefix + "_date_fin"));
        columns.add(Column.aliased("commentaire", table, columnPrefix + "_commentaire"));
        columns.add(Column.aliased("annee", table, columnPrefix + "_annee"));
        columns.add(Column.aliased("date_creation", table, columnPrefix + "_date_creation"));
        columns.add(Column.aliased("date_mise_validation", table, columnPrefix + "_date_mise_validation"));
        columns.add(Column.aliased("date_validation", table, columnPrefix + "_date_validation"));
        columns.add(Column.aliased("commentaire_mise_en_validation_ac", table, columnPrefix + "_commentaire_mise_en_validation_ac"));
        columns.add(Column.aliased("reference_mise_validation_ac", table, columnPrefix + "_reference_mise_validation_ac"));
        columns.add(Column.aliased("fichier_mise_validation_ac", table, columnPrefix + "_fichier_mise_validation_ac"));
        columns.add(
            Column.aliased("fichier_mise_validation_ac_content_type", table, columnPrefix + "_fichier_mise_validation_ac_content_type")
        );
        columns.add(Column.aliased("date_mise_en_validation_ccmp", table, columnPrefix + "_date_mise_en_validation_ccmp"));
        columns.add(Column.aliased("fichier_mise_validation_ccmp", table, columnPrefix + "_fichier_mise_validation_ccmp"));
        columns.add(
            Column.aliased("fichier_mise_validation_ccmp_content_type", table, columnPrefix + "_fichier_mise_validation_ccmp_content_type")
        );
        columns.add(Column.aliased("reference_mise_validation_ccmp", table, columnPrefix + "_reference_mise_validation_ccmp"));
        columns.add(Column.aliased("date_validation_1", table, columnPrefix + "_date_validation_1"));
        columns.add(Column.aliased("commentaire_validation", table, columnPrefix + "_commentaire_validation"));
        columns.add(Column.aliased("reference_validation", table, columnPrefix + "_reference_validation"));
        columns.add(Column.aliased("fichier_validation", table, columnPrefix + "_fichier_validation"));
        columns.add(Column.aliased("fichier_validation_content_type", table, columnPrefix + "_fichier_validation_content_type"));
        columns.add(Column.aliased("date_validation_2", table, columnPrefix + "_date_validation_2"));
        columns.add(Column.aliased("date_rejet", table, columnPrefix + "_date_rejet"));
        columns.add(Column.aliased("date_publication", table, columnPrefix + "_date_publication"));
        columns.add(Column.aliased("commentaire_publication", table, columnPrefix + "_commentaire_publication"));

        return columns;
    }
}
