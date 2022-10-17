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
import sn.ssi.sigmap.domain.SygService;
import sn.ssi.sigmap.repository.rowmapper.SygServiceRowMapper;
import sn.ssi.sigmap.repository.rowmapper.SygTypeServiceRowMapper;

/**
 * Spring Data R2DBC custom repository implementation for the SygService entity.
 */
@SuppressWarnings("unused")
class SygServiceRepositoryInternalImpl extends SimpleR2dbcRepository<SygService, Long> implements SygServiceRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final SygTypeServiceRowMapper sygtypeserviceMapper;
    private final SygServiceRowMapper sygserviceMapper;

    private static final Table entityTable = Table.aliased("syg_service", EntityManager.ENTITY_ALIAS);
    private static final Table sygTypeServiceTable = Table.aliased("syg_type_service", "sygTypeService");

    public SygServiceRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        SygTypeServiceRowMapper sygtypeserviceMapper,
        SygServiceRowMapper sygserviceMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(SygService.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.sygtypeserviceMapper = sygtypeserviceMapper;
        this.sygserviceMapper = sygserviceMapper;
    }

    @Override
    public Flux<SygService> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<SygService> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = SygServiceSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(SygTypeServiceSqlHelper.getColumns(sygTypeServiceTable, "sygTypeService"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(sygTypeServiceTable)
            .on(Column.create("syg_type_service_id", entityTable))
            .equals(Column.create("id", sygTypeServiceTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, SygService.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<SygService> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<SygService> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    @Override
    public Mono<SygService> findOneWithEagerRelationships(Long id) {
        return findById(id);
    }

    @Override
    public Flux<SygService> findAllWithEagerRelationships() {
        return findAll();
    }

    @Override
    public Flux<SygService> findAllWithEagerRelationships(Pageable page) {
        return findAllBy(page);
    }

    private SygService process(Row row, RowMetadata metadata) {
        SygService entity = sygserviceMapper.apply(row, "e");
        entity.setSygTypeService(sygtypeserviceMapper.apply(row, "sygTypeService"));
        return entity;
    }

    @Override
    public <S extends SygService> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
