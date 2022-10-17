package sn.ssi.sigmap.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.SygSourceFinancement;

/**
 * Spring Data R2DBC repository for the SygSourceFinancement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SygSourceFinancementRepository
    extends ReactiveCrudRepository<SygSourceFinancement, Long>, SygSourceFinancementRepositoryInternal {
    Flux<SygSourceFinancement> findAllBy(Pageable pageable);

    @Override
    Mono<SygSourceFinancement> findOneWithEagerRelationships(Long id);

    @Override
    Flux<SygSourceFinancement> findAllWithEagerRelationships();

    @Override
    Flux<SygSourceFinancement> findAllWithEagerRelationships(Pageable page);

    @Query("SELECT * FROM syg_source_financement entity WHERE entity.syg_type_source_financement_id = :id")
    Flux<SygSourceFinancement> findBySygTypeSourceFinancement(Long id);

    @Query("SELECT * FROM syg_source_financement entity WHERE entity.syg_type_source_financement_id IS NULL")
    Flux<SygSourceFinancement> findAllWhereSygTypeSourceFinancementIsNull();

    @Override
    <S extends SygSourceFinancement> Mono<S> save(S entity);

    @Override
    Flux<SygSourceFinancement> findAll();

    @Override
    Mono<SygSourceFinancement> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface SygSourceFinancementRepositoryInternal {
    <S extends SygSourceFinancement> Mono<S> save(S entity);

    Flux<SygSourceFinancement> findAllBy(Pageable pageable);

    Flux<SygSourceFinancement> findAll();

    Mono<SygSourceFinancement> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<SygSourceFinancement> findAllBy(Pageable pageable, Criteria criteria);

    Mono<SygSourceFinancement> findOneWithEagerRelationships(Long id);

    Flux<SygSourceFinancement> findAllWithEagerRelationships();

    Flux<SygSourceFinancement> findAllWithEagerRelationships(Pageable page);

    Mono<Void> deleteById(Long id);
}
