package sn.ssi.sigmap.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.Realisation;

/**
 * Spring Data R2DBC repository for the Realisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RealisationRepository extends ReactiveCrudRepository<Realisation, Long>, RealisationRepositoryInternal {
    Flux<Realisation> findAllBy(Pageable pageable);

    @Query("SELECT * FROM realisation entity WHERE entity.plan_passation_id = :id")
    Flux<Realisation> findByPlanPassation(Long id);

    @Query("SELECT * FROM realisation entity WHERE entity.plan_passation_id IS NULL")
    Flux<Realisation> findAllWherePlanPassationIsNull();

    @Override
    <S extends Realisation> Mono<S> save(S entity);

    @Override
    Flux<Realisation> findAll();

    @Override
    Mono<Realisation> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface RealisationRepositoryInternal {
    <S extends Realisation> Mono<S> save(S entity);

    Flux<Realisation> findAllBy(Pageable pageable);

    Flux<Realisation> findAll();

    Mono<Realisation> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Realisation> findAllBy(Pageable pageable, Criteria criteria);

}
