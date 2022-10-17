package sn.ssi.sigmap.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.SygTypeService;

/**
 * Spring Data R2DBC repository for the SygTypeService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SygTypeServiceRepository extends ReactiveCrudRepository<SygTypeService, Long>, SygTypeServiceRepositoryInternal {
    Flux<SygTypeService> findAllBy(Pageable pageable);

    @Override
    <S extends SygTypeService> Mono<S> save(S entity);

    @Override
    Flux<SygTypeService> findAll();

    @Override
    Mono<SygTypeService> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface SygTypeServiceRepositoryInternal {
    <S extends SygTypeService> Mono<S> save(S entity);

    Flux<SygTypeService> findAllBy(Pageable pageable);

    Flux<SygTypeService> findAll();

    Mono<SygTypeService> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<SygTypeService> findAllBy(Pageable pageable, Criteria criteria);

}
