package sn.ssi.sigmap.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.ParamDate;

/**
 * Spring Data R2DBC repository for the ParamDate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParamDateRepository extends ReactiveCrudRepository<ParamDate, Long>, ParamDateRepositoryInternal {
    Flux<ParamDate> findAllBy(Pageable pageable);

    @Override
    <S extends ParamDate> Mono<S> save(S entity);

    @Override
    Flux<ParamDate> findAll();

    @Override
    Mono<ParamDate> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface ParamDateRepositoryInternal {
    <S extends ParamDate> Mono<S> save(S entity);

    Flux<ParamDate> findAllBy(Pageable pageable);

    Flux<ParamDate> findAll();

    Mono<ParamDate> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<ParamDate> findAllBy(Pageable pageable, Criteria criteria);

}
