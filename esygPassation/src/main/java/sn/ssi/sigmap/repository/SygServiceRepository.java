package sn.ssi.sigmap.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.SygService;

/**
 * Spring Data R2DBC repository for the SygService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SygServiceRepository extends ReactiveCrudRepository<SygService, Long>, SygServiceRepositoryInternal {
    Flux<SygService> findAllBy(Pageable pageable);

    @Override
    Mono<SygService> findOneWithEagerRelationships(Long id);

    @Override
    Flux<SygService> findAllWithEagerRelationships();

    @Override
    Flux<SygService> findAllWithEagerRelationships(Pageable page);

    @Query("SELECT * FROM syg_service entity WHERE entity.syg_type_service_id = :id")
    Flux<SygService> findBySygTypeService(Long id);

    @Query("SELECT * FROM syg_service entity WHERE entity.syg_type_service_id IS NULL")
    Flux<SygService> findAllWhereSygTypeServiceIsNull();

    @Override
    <S extends SygService> Mono<S> save(S entity);

    @Override
    Flux<SygService> findAll();

    @Override
    Mono<SygService> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface SygServiceRepositoryInternal {
    <S extends SygService> Mono<S> save(S entity);

    Flux<SygService> findAllBy(Pageable pageable);

    Flux<SygService> findAll();

    Mono<SygService> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<SygService> findAllBy(Pageable pageable, Criteria criteria);

    Mono<SygService> findOneWithEagerRelationships(Long id);

    Flux<SygService> findAllWithEagerRelationships();

    Flux<SygService> findAllWithEagerRelationships(Pageable page);

    Mono<Void> deleteById(Long id);
}
