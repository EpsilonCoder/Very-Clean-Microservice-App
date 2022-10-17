package sn.ssi.sigmap.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.service.dto.SygTypeMarcheDTO;

/**
 * Service Interface for managing {@link sn.ssi.sigmap.domain.SygTypeMarche}.
 */
public interface SygTypeMarcheService {
    /**
     * Save a sygTypeMarche.
     *
     * @param sygTypeMarcheDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<SygTypeMarcheDTO> save(SygTypeMarcheDTO sygTypeMarcheDTO);

    /**
     * Updates a sygTypeMarche.
     *
     * @param sygTypeMarcheDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<SygTypeMarcheDTO> update(SygTypeMarcheDTO sygTypeMarcheDTO);

    /**
     * Partially updates a sygTypeMarche.
     *
     * @param sygTypeMarcheDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<SygTypeMarcheDTO> partialUpdate(SygTypeMarcheDTO sygTypeMarcheDTO);

    /**
     * Get all the sygTypeMarches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<SygTypeMarcheDTO> findAll(Pageable pageable);

    /**
     * Returns the number of sygTypeMarches available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" sygTypeMarche.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<SygTypeMarcheDTO> findOne(Long id);

    /**
     * Delete the "id" sygTypeMarche.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
