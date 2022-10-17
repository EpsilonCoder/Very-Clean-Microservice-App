package sn.ssi.sigmap.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.Historique;
import sn.ssi.sigmap.repository.rowmapper.HistoriqueRowMapper;
import sn.ssi.sigmap.repository.rowmapper.PlanPassationRowMapper;

/**
 * Spring Data R2DBC custom repository implementation for the Historique entity.
 */
@SuppressWarnings("unused")
class HistoriqueRepositoryInternalImpl extends SimpleR2dbcRepository<Historique, Long> implements HistoriqueRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final PlanPassationRowMapper planpassationMapper;
    private final HistoriqueRowMapper historiqueMapper;

    private static final Table entityTable = Table.aliased("historique", EntityManager.ENTITY_ALIAS);
    private static final Table planPassationTable = Table.aliased("plan_passation", "planPassation");

    public HistoriqueRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        PlanPassationRowMapper planpassationMapper,
        HistoriqueRowMapper historiqueMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Historique.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.planpassationMapper = planpassationMapper;
        this.historiqueMapper = historiqueMapper;
    }

    @Override
    public Flux<Historique> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Historique> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = HistoriqueSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(PlanPassationSqlHelper.getColumns(planPassationTable, "planPassation"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(planPassationTable)
            .on(Column.create("plan_passation_id", entityTable))
            .equals(Column.create("id", planPassationTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Historique.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Historique> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Historique> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Historique process(Row row, RowMetadata metadata) {
        Historique entity = historiqueMapper.apply(row, "e");
        entity.setPlanPassation(planpassationMapper.apply(row, "planPassation"));
        return entity;
    }

    @Override
    public <S extends Historique> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
