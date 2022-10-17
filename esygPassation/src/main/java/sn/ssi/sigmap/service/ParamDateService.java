package sn.ssi.sigmap.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.service.dto.ParamDateDTO;

/**
 * Service Interface for managing {@link sn.ssi.sigmap.domain.ParamDate}.
 */
public interface ParamDateService {
    /**
     * Save a paramDate.
     *
     * @param paramDateDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<ParamDateDTO> save(ParamDateDTO paramDateDTO);

    /**
     * Updates a paramDate.
     *
     * @param paramDateDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<ParamDateDTO> update(ParamDateDTO paramDateDTO);

    /**
     * Partially updates a paramDate.
     *
     * @param paramDateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<ParamDateDTO> partialUpdate(ParamDateDTO paramDateDTO);

    /**
     * Get all the paramDates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<ParamDateDTO> findAll(Pageable pageable);

    /**
     * Returns the number of paramDates available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" paramDate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<ParamDateDTO> findOne(Long id);

    /**
     * Delete the "id" paramDate.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
