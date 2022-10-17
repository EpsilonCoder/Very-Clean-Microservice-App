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
import sn.ssi.sigmap.repository.PlanPassationRepository;
import sn.ssi.sigmap.service.PlanPassationService;
import sn.ssi.sigmap.service.dto.PlanPassationDTO;
import sn.ssi.sigmap.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link sn.ssi.sigmap.domain.PlanPassation}.
 */
@RestController
@RequestMapping("/api")
public class PlanPassationResource {

    private final Logger log = LoggerFactory.getLogger(PlanPassationResource.class);

    private static final String ENTITY_NAME = "planpassationmsPlanPassation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanPassationService planPassationService;

    private final PlanPassationRepository planPassationRepository;

    public PlanPassationResource(PlanPassationService planPassationService, PlanPassationRepository planPassationRepository) {
        this.planPassationService = planPassationService;
        this.planPassationRepository = planPassationRepository;
    }

    /**
     * {@code POST  /plan-passations} : Create a new planPassation.
     *
     * @param planPassationDTO the planPassationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planPassationDTO, or with status {@code 400 (Bad Request)} if the planPassation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plan-passations")
    public Mono<ResponseEntity<PlanPassationDTO>> createPlanPassation(@Valid @RequestBody PlanPassationDTO planPassationDTO)
        throws URISyntaxException {
        log.debug("REST request to save PlanPassation : {}", planPassationDTO);
        if (planPassationDTO.getId() != null) {
            throw new BadRequestAlertException("A new planPassation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return planPassationService
            .save(planPassationDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/plan-passations/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /plan-passations/:id} : Updates an existing planPassation.
     *
     * @param id the id of the planPassationDTO to save.
     * @param planPassationDTO the planPassationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planPassationDTO,
     * or with status {@code 400 (Bad Request)} if the planPassationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planPassationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plan-passations/{id}")
    public Mono<ResponseEntity<PlanPassationDTO>> updatePlanPassation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PlanPassationDTO planPassationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlanPassation : {}, {}", id, planPassationDTO);
        if (planPassationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planPassationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return planPassationRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return planPassationService
                    .update(planPassationDTO)
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
     * {@code PATCH  /plan-passations/:id} : Partial updates given fields of an existing planPassation, field will ignore if it is null
     *
     * @param id the id of the planPassationDTO to save.
     * @param planPassationDTO the planPassationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planPassationDTO,
     * or with status {@code 400 (Bad Request)} if the planPassationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the planPassationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the planPassationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plan-passations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<PlanPassationDTO>> partialUpdatePlanPassation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PlanPassationDTO planPassationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlanPassation partially : {}, {}", id, planPassationDTO);
        if (planPassationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planPassationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return planPassationRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<PlanPassationDTO> result = planPassationService.partialUpdate(planPassationDTO);

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
     * {@code GET  /plan-passations} : get all the planPassations.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planPassations in body.
     */
    @GetMapping("/plan-passations")
    public Mono<ResponseEntity<List<PlanPassationDTO>>> getAllPlanPassations(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of PlanPassations");
        return planPassationService
            .countAll()
            .zipWith(planPassationService.findAll(pageable).collectList())
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
     * {@code GET  /plan-passations/:id} : get the "id" planPassation.
     *
     * @param id the id of the planPassationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planPassationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plan-passations/{id}")
    public Mono<ResponseEntity<PlanPassationDTO>> getPlanPassation(@PathVariable Long id) {
        log.debug("REST request to get PlanPassation : {}", id);
        Mono<PlanPassationDTO> planPassationDTO = planPassationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planPassationDTO);
    }

    /**
     * {@code DELETE  /plan-passations/:id} : delete the "id" planPassation.
     *
     * @param id the id of the planPassationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plan-passations/{id}")
    public Mono<ResponseEntity<Void>> deletePlanPassation(@PathVariable Long id) {
        log.debug("REST request to delete PlanPassation : {}", id);
        return planPassationService
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
