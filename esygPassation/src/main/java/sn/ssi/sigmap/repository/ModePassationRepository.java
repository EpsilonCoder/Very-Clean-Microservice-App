package sn.ssi.sigmap.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.ModePassation;

/**
 * Spring Data R2DBC repository for the ModePassation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModePassationRepository extends ReactiveCrudRepository<ModePassation, Long>, ModePassationRepositoryInternal {
    @Override
    <S extends ModePassation> Mono<S> save(S entity);

    @Override
    Flux<ModePassation> findAll();

    @Override
    Mono<ModePassation> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface ModePassationRepositoryInternal {
    <S extends ModePassation> Mono<S> save(S entity);

    Flux<ModePassation> findAllBy(Pageable pageable);

    Flux<ModePassation> findAll();

    Mono<ModePassation> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<ModePassation> findAllBy(Pageable pageable, Criteria criteria);

}
