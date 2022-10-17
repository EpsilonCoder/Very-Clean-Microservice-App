package sn.ssi.sigmap.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.repository.HistoriqueRepository;
import sn.ssi.sigmap.service.HistoriqueService;
import sn.ssi.sigmap.service.dto.HistoriqueDTO;
import sn.ssi.sigmap.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link sn.ssi.sigmap.domain.Historique}.
 */
@RestController
@RequestMapping("/api")
public class HistoriqueResource {

    private final Logger log = LoggerFactory.getLogger(HistoriqueResource.class);

    private static final String ENTITY_NAME = "planpassationmsHistorique";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistoriqueService historiqueService;

    private final HistoriqueRepository historiqueRepository;

    public HistoriqueResource(HistoriqueService historiqueService, HistoriqueRepository historiqueRepository) {
        this.historiqueService = historiqueService;
        this.historiqueRepository = historiqueRepository;
    }

    /**
     * {@code POST  /historiques} : Create a new historique.
     *
     * @param historiqueDTO the historiqueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historiqueDTO, or with status {@code 400 (Bad Request)} if the historique has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/historiques")
    public Mono<ResponseEntity<HistoriqueDTO>> createHistorique(@Valid @RequestBody HistoriqueDTO historiqueDTO) throws URISyntaxException {
        log.debug("REST request to save Historique : {}", historiqueDTO);
        if (historiqueDTO.getId() != null) {
            throw new BadRequestAlertException("A new historique cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return historiqueService
            .save(historiqueDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/historiques/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /historiques/:id} : Updates an existing historique.
     *
     * @param id the id of the historiqueDTO to save.
     * @param historiqueDTO the historiqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historiqueDTO,
     * or with status {@code 400 (Bad Request)} if the historiqueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historiqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/historiques/{id}")
    public Mono<ResponseEntity<HistoriqueDTO>> updateHistorique(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HistoriqueDTO historiqueDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Historique : {}, {}", id, historiqueDTO);
        if (historiqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historiqueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return historiqueRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return historiqueService
                    .update(historiqueDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /historiques/:id} : Partial updates given fields of an existing historique, field will ignore if it is null
     *
     * @param id the id of the historiqueDTO to save.
     * @param historiqueDTO the historiqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historiqueDTO,
     * or with status {@code 400 (Bad Request)} if the historiqueDTO is not valid,
     * or with status {@code 404 (Not Found)} if the historiqueDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the historiqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/historiques/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<HistoriqueDTO>> partialUpdateHistorique(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HistoriqueDTO historiqueDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Historique partially : {}, {}", id, historiqueDTO);
        if (historiqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historiqueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return historiqueRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<HistoriqueDTO> result = historiqueService.partialUpdate(historiqueDTO);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /historiques} : get all the historiques.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historiques in body.
     */
    @GetMapping("/historiques")
    public Mono<ResponseEntity<List<HistoriqueDTO>>> getAllHistoriques(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Historiques");
        return historiqueService
            .countAll()
            .zipWith(historiqueService.findAll(pageable).collectList())
            .map(countWithEntities ->
                ResponseEntity
                    .ok()
                    .headers(
                        PaginationUtil.generatePaginationHttpHeaders(
                            UriComponentsBuilder.fromHttpRequest(request),
                            new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                        )
                    )
                    .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /historiques/:id} : get the "id" historique.
     *
     * @param id the id of the historiqueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historiqueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/historiques/{id}")
    public Mono<ResponseEntity<HistoriqueDTO>> getHistorique(@PathVariable Long id) {
        log.debug("REST request to get Historique : {}", id);
        Mono<HistoriqueDTO> historiqueDTO = historiqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(historiqueDTO);
    }

    /**
     * {@code DELETE  /historiques/:id} : delete the "id" historique.
     *
     * @param id the id of the historiqueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/historiques/{id}")
    public Mono<ResponseEntity<Void>> deleteHistorique(@PathVariable Long id) {
        log.debug("REST request to delete Historique : {}", id);
        return historiqueService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
