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
import sn.ssi.sigmap.repository.SygSourceFinancementRepository;
import sn.ssi.sigmap.service.SygSourceFinancementService;
import sn.ssi.sigmap.service.dto.SygSourceFinancementDTO;
import sn.ssi.sigmap.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link sn.ssi.sigmap.domain.SygSourceFinancement}.
 */
@RestController
@RequestMapping("/api")
public class SygSourceFinancementResource {

    private final Logger log = LoggerFactory.getLogger(SygSourceFinancementResource.class);

    private static final String ENTITY_NAME = "planpassationmsSygSourceFinancement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SygSourceFinancementService sygSourceFinancementService;

    private final SygSourceFinancementRepository sygSourceFinancementRepository;

    public SygSourceFinancementResource(
        SygSourceFinancementService sygSourceFinancementService,
        SygSourceFinancementRepository sygSourceFinancementRepository
    ) {
        this.sygSourceFinancementService = sygSourceFinancementService;
        this.sygSourceFinancementRepository = sygSourceFinancementRepository;
    }

    /**
     * {@code POST  /syg-source-financements} : Create a new sygSourceFinancement.
     *
     * @param sygSourceFinancementDTO the sygSourceFinancementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sygSourceFinancementDTO, or with status {@code 400 (Bad Request)} if the sygSourceFinancement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/syg-source-financements")
    public Mono<ResponseEntity<SygSourceFinancementDTO>> createSygSourceFinancement(
        @Valid @RequestBody SygSourceFinancementDTO sygSourceFinancementDTO
    ) throws URISyntaxException {
        log.debug("REST request to save SygSourceFinancement : {}", sygSourceFinancementDTO);
        if (sygSourceFinancementDTO.getId() != null) {
            throw new BadRequestAlertException("A new sygSourceFinancement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return sygSourceFinancementService
            .save(sygSourceFinancementDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/syg-source-financements/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /syg-source-financements/:id} : Updates an existing sygSourceFinancement.
     *
     * @param id the id of the sygSourceFinancementDTO to save.
     * @param sygSourceFinancementDTO the sygSourceFinancementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sygSourceFinancementDTO,
     * or with status {@code 400 (Bad Request)} if the sygSourceFinancementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sygSourceFinancementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/syg-source-financements/{id}")
    public Mono<ResponseEntity<SygSourceFinancementDTO>> updateSygSourceFinancement(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SygSourceFinancementDTO sygSourceFinancementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SygSourceFinancement : {}, {}", id, sygSourceFinancementDTO);
        if (sygSourceFinancementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sygSourceFinancementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return sygSourceFinancementRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return sygSourceFinancementService
                    .update(sygSourceFinancementDTO)
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
     * {@code PATCH  /syg-source-financements/:id} : Partial updates given fields of an existing sygSourceFinancement, field will ignore if it is null
     *
     * @param id the id of the sygSourceFinancementDTO to save.
     * @param sygSourceFinancementDTO the sygSourceFinancementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sygSourceFinancementDTO,
     * or with status {@code 400 (Bad Request)} if the sygSourceFinancementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sygSourceFinancementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sygSourceFinancementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/syg-source-financements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<SygSourceFinancementDTO>> partialUpdateSygSourceFinancement(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SygSourceFinancementDTO sygSourceFinancementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SygSourceFinancement partially : {}, {}", id, sygSourceFinancementDTO);
        if (sygSourceFinancementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sygSourceFinancementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return sygSourceFinancementRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<SygSourceFinancementDTO> result = sygSourceFinancementService.partialUpdate(sygSourceFinancementDTO);

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
     * {@code GET  /syg-source-financements} : get all the sygSourceFinancements.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sygSourceFinancements in body.
     */
    @GetMapping("/syg-source-financements")
    public Mono<ResponseEntity<List<SygSourceFinancementDTO>>> getAllSygSourceFinancements(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of SygSourceFinancements");
        return sygSourceFinancementService
            .countAll()
            .zipWith(sygSourceFinancementService.findAll(pageable).collectList())
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
     * {@code GET  /syg-source-financements/:id} : get the "id" sygSourceFinancement.
     *
     * @param id the id of the sygSourceFinancementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sygSourceFinancementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/syg-source-financements/{id}")
    public Mono<ResponseEntity<SygSourceFinancementDTO>> getSygSourceFinancement(@PathVariable Long id) {
        log.debug("REST request to get SygSourceFinancement : {}", id);
        Mono<SygSourceFinancementDTO> sygSourceFinancementDTO = sygSourceFinancementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sygSourceFinancementDTO);
    }

    /**
     * {@code DELETE  /syg-source-financements/:id} : delete the "id" sygSourceFinancement.
     *
     * @param id the id of the sygSourceFinancementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/syg-source-financements/{id}")
    public Mono<ResponseEntity<Void>> deleteSygSourceFinancement(@PathVariable Long id) {
        log.debug("REST request to delete SygSourceFinancement : {}", id);
        return sygSourceFinancementService
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
