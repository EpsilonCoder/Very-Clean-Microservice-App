package sn.ssi.sigmap.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.service.dto.PlanPassationDTO;

/**
 * Service Interface for managing {@link sn.ssi.sigmap.domain.PlanPassation}.
 */
public interface PlanPassationService {
    /**
     * Save a planPassation.
     *
     * @param planPassationDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<PlanPassationDTO> save(PlanPassationDTO planPassationDTO);

    /**
     * Updates a planPassation.
     *
     * @param planPassationDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<PlanPassationDTO> update(PlanPassationDTO planPassationDTO);

    /**
     * Partially updates a planPassation.
     *
     * @param planPassationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<PlanPassationDTO> partialUpdate(PlanPassationDTO planPassationDTO);

    /**
     * Get all the planPassations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<PlanPassationDTO> findAll(Pageable pageable);

    /**
     * Returns the number of planPassations available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" planPassation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<PlanPassationDTO> findOne(Long id);

    /**
     * Delete the "id" planPassation.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
