package sn.ssi.sigmap.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
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
import sn.ssi.sigmap.domain.SygSourceFinancement;
import sn.ssi.sigmap.repository.rowmapper.SygSourceFinancementRowMapper;
import sn.ssi.sigmap.repository.rowmapper.SygTypeSourceFinancementRowMapper;

/**
 * Spring Data R2DBC custom repository implementation for the SygSourceFinancement entity.
 */
@SuppressWarnings("unused")
class SygSourceFinancementRepositoryInternalImpl
    extends SimpleR2dbcRepository<SygSourceFinancement, Long>
    implements SygSourceFinancementRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final SygTypeSourceFinancementRowMapper sygtypesourcefinancementMapper;
    private final SygSourceFinancementRowMapper sygsourcefinancementMapper;

    private static final Table entityTable = Table.aliased("syg_source_financement", EntityManager.ENTITY_ALIAS);
    private static final Table sygTypeSourceFinancementTable = Table.aliased("syg_type_source_financement", "sygTypeSourceFinancement");

    public SygSourceFinancementRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        SygTypeSourceFinancementRowMapper sygtypesourcefinancementMapper,
        SygSourceFinancementRowMapper sygsourcefinancementMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(SygSourceFinancement.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.sygtypesourcefinancementMapper = sygtypesourcefinancementMapper;
        this.sygsourcefinancementMapper = sygsourcefinancementMapper;
    }

    @Override
    public Flux<SygSourceFinancement> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<SygSourceFinancement> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = SygSourceFinancementSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(SygTypeSourceFinancementSqlHelper.getColumns(sygTypeSourceFinancementTable, "sygTypeSourceFinancement"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(sygTypeSourceFinancementTable)
            .on(Column.create("syg_type_source_financement_id", entityTable))
            .equals(Column.create("id", sygTypeSourceFinancementTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, SygSourceFinancement.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<SygSourceFinancement> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<SygSourceFinancement> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    @Override
    public Mono<SygSourceFinancement> findOneWithEagerRelationships(Long id) {
        return findById(id);
    }

    @Override
    public Flux<SygSourceFinancement> findAllWithEagerRelationships() {
        return findAll();
    }

    @Override
    public Flux<SygSourceFinancement> findAllWithEagerRelationships(Pageable page) {
        return findAllBy(page);
    }

    private SygSourceFinancement process(Row row, RowMetadata metadata) {
        SygSourceFinancement entity = sygsourcefinancementMapper.apply(row, "e");
        entity.setSygTypeSourceFinancement(sygtypesourcefinancementMapper.apply(row, "sygTypeSourceFinancement"));
        return entity;
    }

    @Override
    public <S extends SygSourceFinancement> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
