package sn.ssi.sigmap.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.service.dto.RealisationDTO;

/**
 * Service Interface for managing {@link sn.ssi.sigmap.domain.Realisation}.
 */
public interface RealisationService {
    /**
     * Save a realisation.
     *
     * @param realisationDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<RealisationDTO> save(RealisationDTO realisationDTO);

    /**
     * Updates a realisation.
     *
     * @param realisationDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<RealisationDTO> update(RealisationDTO realisationDTO);

    /**
     * Partially updates a realisation.
     *
     * @param realisationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<RealisationDTO> partialUpdate(RealisationDTO realisationDTO);

    /**
     * Get all the realisations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<RealisationDTO> findAll(Pageable pageable);

    /**
     * Returns the number of realisations available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" realisation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<RealisationDTO> findOne(Long id);

    /**
     * Delete the "id" realisation.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
