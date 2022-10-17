package sn.ssi.sigmap.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.service.dto.SygTypeSourceFinancementDTO;

/**
 * Service Interface for managing {@link sn.ssi.sigmap.domain.SygTypeSourceFinancement}.
 */
public interface SygTypeSourceFinancementService {
    /**
     * Save a sygTypeSourceFinancement.
     *
     * @param sygTypeSourceFinancementDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<SygTypeSourceFinancementDTO> save(SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO);

    /**
     * Updates a sygTypeSourceFinancement.
     *
     * @param sygTypeSourceFinancementDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<SygTypeSourceFinancementDTO> update(SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO);

    /**
     * Partially updates a sygTypeSourceFinancement.
     *
     * @param sygTypeSourceFinancementDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<SygTypeSourceFinancementDTO> partialUpdate(SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO);

    /**
     * Get all the sygTypeSourceFinancements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<SygTypeSourceFinancementDTO> findAll(Pageable pageable);

    /**
     * Returns the number of sygTypeSourceFinancements available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" sygTypeSourceFinancement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<SygTypeSourceFinancementDTO> findOne(Long id);

    /**
     * Delete the "id" sygTypeSourceFinancement.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
