package sn.ssi.sigmap.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class RealisationSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("libelle", table, columnPrefix + "_libelle"));
        columns.add(Column.aliased("date_attribution", table, columnPrefix + "_date_attribution"));
        columns.add(Column.aliased("delaiexecution", table, columnPrefix + "_delaiexecution"));
        columns.add(Column.aliased("objet", table, columnPrefix + "_objet"));
        columns.add(Column.aliased("montant", table, columnPrefix + "_montant"));
        columns.add(Column.aliased("examen_dncmp", table, columnPrefix + "_examen_dncmp"));
        columns.add(Column.aliased("examen_ptf", table, columnPrefix + "_examen_ptf"));
        columns.add(Column.aliased("chapitre_imputation", table, columnPrefix + "_chapitre_imputation"));
        columns.add(Column.aliased("autorisation_engagement", table, columnPrefix + "_autorisation_engagement"));
        columns.add(Column.aliased("date_reception_dossier", table, columnPrefix + "_date_reception_dossier"));
        columns.add(Column.aliased("date_non_objection", table, columnPrefix + "_date_non_objection"));
        columns.add(Column.aliased("date_autorisation", table, columnPrefix + "_date_autorisation"));
        columns.add(Column.aliased("date_recep_non_objection", table, columnPrefix + "_date_recep_non_objection"));
        columns.add(Column.aliased("date_recep_doss_corrige", table, columnPrefix + "_date_recep_doss_corrige"));
        columns.add(Column.aliased("date_pub_par_prmp", table, columnPrefix + "_date_pub_par_prmp"));
        columns.add(Column.aliased("date_ouverture_plis", table, columnPrefix + "_date_ouverture_plis"));
        columns.add(Column.aliased("date_recep_non_object_ocmp", table, columnPrefix + "_date_recep_non_object_ocmp"));
        columns.add(Column.aliased("date_recep_rapport_eva", table, columnPrefix + "_date_recep_rapport_eva"));
        columns.add(Column.aliased("date_recep_non_object_ptf", table, columnPrefix + "_date_recep_non_object_ptf"));
        columns.add(Column.aliased("date_examen_juridique", table, columnPrefix + "_date_examen_juridique"));
        columns.add(Column.aliased("date_notif_contrat", table, columnPrefix + "_date_notif_contrat"));
        columns.add(Column.aliased("date_approbation_contrat", table, columnPrefix + "_date_approbation_contrat"));

        columns.add(Column.aliased("plan_passation_id", table, columnPrefix + "_plan_passation_id"));
        return columns;
    }
}
