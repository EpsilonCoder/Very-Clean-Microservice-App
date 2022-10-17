package sn.ssi.sigmap.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.SygTypeSourceFinancement;

/**
 * Spring Data R2DBC repository for the SygTypeSourceFinancement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SygTypeSourceFinancementRepository
    extends ReactiveCrudRepository<SygTypeSourceFinancement, Long>, SygTypeSourceFinancementRepositoryInternal {
    Flux<SygTypeSourceFinancement> findAllBy(Pageable pageable);

    @Override
    <S extends SygTypeSourceFinancement> Mono<S> save(S entity);

    @Override
    Flux<SygTypeSourceFinancement> findAll();

    @Override
    Mono<SygTypeSourceFinancement> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface SygTypeSourceFinancementRepositoryInternal {
    <S extends SygTypeSourceFinancement> Mono<S> save(S entity);

    Flux<SygTypeSourceFinancement> findAllBy(Pageable pageable);

    Flux<SygTypeSourceFinancement> findAll();

    Mono<SygTypeSourceFinancement> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<SygTypeSourceFinancement> findAllBy(Pageable pageable, Criteria criteria);

}
