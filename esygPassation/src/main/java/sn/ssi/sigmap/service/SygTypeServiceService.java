package sn.ssi.sigmap.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.service.dto.SygTypeServiceDTO;

/**
 * Service Interface for managing {@link sn.ssi.sigmap.domain.SygTypeService}.
 */
public interface SygTypeServiceService {
    /**
     * Save a sygTypeService.
     *
     * @param sygTypeServiceDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<SygTypeServiceDTO> save(SygTypeServiceDTO sygTypeServiceDTO);

    /**
     * Updates a sygTypeService.
     *
     * @param sygTypeServiceDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<SygTypeServiceDTO> update(SygTypeServiceDTO sygTypeServiceDTO);

    /**
     * Partially updates a sygTypeService.
     *
     * @param sygTypeServiceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<SygTypeServiceDTO> partialUpdate(SygTypeServiceDTO sygTypeServiceDTO);

    /**
     * Get all the sygTypeServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<SygTypeServiceDTO> findAll(Pageable pageable);

    /**
     * Returns the number of sygTypeServices available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" sygTypeService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<SygTypeServiceDTO> findOne(Long id);

    /**
     * Delete the "id" sygTypeService.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
