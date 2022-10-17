package sn.ssi.sigmap.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.service.dto.HistoriqueDTO;

/**
 * Service Interface for managing {@link sn.ssi.sigmap.domain.Historique}.
 */
public interface HistoriqueService {
    /**
     * Save a historique.
     *
     * @param historiqueDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<HistoriqueDTO> save(HistoriqueDTO historiqueDTO);

    /**
     * Updates a historique.
     *
     * @param historiqueDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<HistoriqueDTO> update(HistoriqueDTO historiqueDTO);

    /**
     * Partially updates a historique.
     *
     * @param historiqueDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<HistoriqueDTO> partialUpdate(HistoriqueDTO historiqueDTO);

    /**
     * Get all the historiques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<HistoriqueDTO> findAll(Pageable pageable);

    /**
     * Returns the number of historiques available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" historique.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<HistoriqueDTO> findOne(Long id);

    /**
     * Delete the "id" historique.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
