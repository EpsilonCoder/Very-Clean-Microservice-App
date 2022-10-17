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
import sn.ssi.sigmap.repository.RealisationRepository;
import sn.ssi.sigmap.service.RealisationService;
import sn.ssi.sigmap.service.dto.RealisationDTO;
import sn.ssi.sigmap.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link sn.ssi.sigmap.domain.Realisation}.
 */
@RestController
@RequestMapping("/api")
public class RealisationResource {

    private final Logger log = LoggerFactory.getLogger(RealisationResource.class);

    private static final String ENTITY_NAME = "planpassationmsRealisation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RealisationService realisationService;

    private final RealisationRepository realisationRepository;

    public RealisationResource(RealisationService realisationService, RealisationRepository realisationRepository) {
        this.realisationService = realisationService;
        this.realisationRepository = realisationRepository;
    }

    /**
     * {@code POST  /realisations} : Create a new realisation.
     *
     * @param realisationDTO the realisationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new realisationDTO, or with status {@code 400 (Bad Request)} if the realisation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/realisations")
    public Mono<ResponseEntity<RealisationDTO>> createRealisation(@Valid @RequestBody RealisationDTO realisationDTO)
        throws URISyntaxException {
        log.debug("REST request to save Realisation : {}", realisationDTO);
        if (realisationDTO.getId() != null) {
            throw new BadRequestAlertException("A new realisation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return realisationService
            .save(realisationDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/realisations/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /realisations/:id} : Updates an existing realisation.
     *
     * @param id the id of the realisationDTO to save.
     * @param realisationDTO the realisationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated realisationDTO,
     * or with status {@code 400 (Bad Request)} if the realisationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the realisationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/realisations/{id}")
    public Mono<ResponseEntity<RealisationDTO>> updateRealisation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RealisationDTO realisationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Realisation : {}, {}", id, realisationDTO);
        if (realisationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, realisationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return realisationRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return realisationService
                    .update(realisationDTO)
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
     * {@code PATCH  /realisations/:id} : Partial updates given fields of an existing realisation, field will ignore if it is null
     *
     * @param id the id of the realisationDTO to save.
     * @param realisationDTO the realisationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated realisationDTO,
     * or with status {@code 400 (Bad Request)} if the realisationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the realisationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the realisationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/realisations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<RealisationDTO>> partialUpdateRealisation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RealisationDTO realisationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Realisation partially : {}, {}", id, realisationDTO);
        if (realisationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, realisationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return realisationRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<RealisationDTO> result = realisationService.partialUpdate(realisationDTO);

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
     * {@code GET  /realisations} : get all the realisations.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of realisations in body.
     */
    @GetMapping("/realisations")
    public Mono<ResponseEntity<List<RealisationDTO>>> getAllRealisations(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Realisations");
        return realisationService
            .countAll()
            .zipWith(realisationService.findAll(pageable).collectList())
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
     * {@code GET  /realisations/:id} : get the "id" realisation.
     *
     * @param id the id of the realisationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the realisationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/realisations/{id}")
    public Mono<ResponseEntity<RealisationDTO>> getRealisation(@PathVariable Long id) {
        log.debug("REST request to get Realisation : {}", id);
        Mono<RealisationDTO> realisationDTO = realisationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(realisationDTO);
    }

    /**
     * {@code DELETE  /realisations/:id} : delete the "id" realisation.
     *
     * @param id the id of the realisationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/realisations/{id}")
    public Mono<ResponseEntity<Void>> deleteRealisation(@PathVariable Long id) {
        log.debug("REST request to delete Realisation : {}", id);
        return realisationService
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
