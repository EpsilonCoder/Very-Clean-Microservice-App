package sn.ssi.sigmap.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.service.dto.SygServiceDTO;

/**
 * Service Interface for managing {@link sn.ssi.sigmap.domain.SygService}.
 */
public interface SygServiceService {
    /**
     * Save a sygService.
     *
     * @param sygServiceDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<SygServiceDTO> save(SygServiceDTO sygServiceDTO);

    /**
     * Updates a sygService.
     *
     * @param sygServiceDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<SygServiceDTO> update(SygServiceDTO sygServiceDTO);

    /**
     * Partially updates a sygService.
     *
     * @param sygServiceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<SygServiceDTO> partialUpdate(SygServiceDTO sygServiceDTO);

    /**
     * Get all the sygServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<SygServiceDTO> findAll(Pageable pageable);

    /**
     * Get all the sygServices with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<SygServiceDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Returns the number of sygServices available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" sygService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<SygServiceDTO> findOne(Long id);

    /**
     * Delete the "id" sygService.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
