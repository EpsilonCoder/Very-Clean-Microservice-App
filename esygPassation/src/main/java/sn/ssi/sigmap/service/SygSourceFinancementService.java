package sn.ssi.sigmap.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.service.dto.SygSourceFinancementDTO;

/**
 * Service Interface for managing {@link sn.ssi.sigmap.domain.SygSourceFinancement}.
 */
public interface SygSourceFinancementService {
    /**
     * Save a sygSourceFinancement.
     *
     * @param sygSourceFinancementDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<SygSourceFinancementDTO> save(SygSourceFinancementDTO sygSourceFinancementDTO);

    /**
     * Updates a sygSourceFinancement.
     *
     * @param sygSourceFinancementDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<SygSourceFinancementDTO> update(SygSourceFinancementDTO sygSourceFinancementDTO);

    /**
     * Partially updates a sygSourceFinancement.
     *
     * @param sygSourceFinancementDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<SygSourceFinancementDTO> partialUpdate(SygSourceFinancementDTO sygSourceFinancementDTO);

    /**
     * Get all the sygSourceFinancements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<SygSourceFinancementDTO> findAll(Pageable pageable);

    /**
     * Get all the sygSourceFinancements with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<SygSourceFinancementDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Returns the number of sygSourceFinancements available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" sygSourceFinancement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<SygSourceFinancementDTO> findOne(Long id);

    /**
     * Delete the "id" sygSourceFinancement.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
