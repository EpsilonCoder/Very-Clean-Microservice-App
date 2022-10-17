package sn.ssi.sigmap.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.SygTypeMarche;

/**
 * Spring Data R2DBC repository for the SygTypeMarche entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SygTypeMarcheRepository extends ReactiveCrudRepository<SygTypeMarche, Long>, SygTypeMarcheRepositoryInternal {
    Flux<SygTypeMarche> findAllBy(Pageable pageable);

    @Override
    <S extends SygTypeMarche> Mono<S> save(S entity);

    @Override
    Flux<SygTypeMarche> findAll();

    @Override
    Mono<SygTypeMarche> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface SygTypeMarcheRepositoryInternal {
    <S extends SygTypeMarche> Mono<S> save(S entity);

    Flux<SygTypeMarche> findAllBy(Pageable pageable);

    Flux<SygTypeMarche> findAll();

    Mono<SygTypeMarche> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<SygTypeMarche> findAllBy(Pageable pageable, Criteria criteria);

}
