package sn.ssi.sigmap.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.PlanPassation;

/**
 * Spring Data R2DBC repository for the PlanPassation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanPassationRepository extends ReactiveCrudRepository<PlanPassation, Long>, PlanPassationRepositoryInternal {
    Flux<PlanPassation> findAllBy(Pageable pageable);

    @Override
    <S extends PlanPassation> Mono<S> save(S entity);

    @Override
    Flux<PlanPassation> findAll();

    @Override
    Mono<PlanPassation> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface PlanPassationRepositoryInternal {
    <S extends PlanPassation> Mono<S> save(S entity);

    Flux<PlanPassation> findAllBy(Pageable pageable);

    Flux<PlanPassation> findAll();

    Mono<PlanPassation> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<PlanPassation> findAllBy(Pageable pageable, Criteria criteria);

}
