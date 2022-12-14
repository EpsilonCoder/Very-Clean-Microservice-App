package sn.ssi.sigmap.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.Historique;

/**
 * Spring Data R2DBC repository for the Historique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoriqueRepository extends ReactiveCrudRepository<Historique, Long>, HistoriqueRepositoryInternal {
    Flux<Historique> findAllBy(Pageable pageable);

    @Query("SELECT * FROM historique entity WHERE entity.plan_passation_id = :id")
    Flux<Historique> findByPlanPassation(Long id);

    @Query("SELECT * FROM historique entity WHERE entity.plan_passation_id IS NULL")
    Flux<Historique> findAllWherePlanPassationIsNull();

    @Override
    <S extends Historique> Mono<S> save(S entity);

    @Override
    Flux<Historique> findAll();

    @Override
    Mono<Historique> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface HistoriqueRepositoryInternal {
    <S extends Historique> Mono<S> save(S entity);

    Flux<Historique> findAllBy(Pageable pageable);

    Flux<Historique> findAll();

    Mono<Historique> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Historique> findAllBy(Pageable pageable, Criteria criteria);

}
