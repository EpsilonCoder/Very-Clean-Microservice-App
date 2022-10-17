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
import sn.ssi.sigmap.repository.SygTypeSourceFinancementRepository;
import sn.ssi.sigmap.service.SygTypeSourceFinancementService;
import sn.ssi.sigmap.service.dto.SygTypeSourceFinancementDTO;
import sn.ssi.sigmap.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link sn.ssi.sigmap.domain.SygTypeSourceFinancement}.
 */
@RestController
@RequestMapping("/api")
public class SygTypeSourceFinancementResource {

    private final Logger log = LoggerFactory.getLogger(SygTypeSourceFinancementResource.class);

    private static final String ENTITY_NAME = "planpassationmsSygTypeSourceFinancement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SygTypeSourceFinancementService sygTypeSourceFinancementService;

    private final SygTypeSourceFinancementRepository sygTypeSourceFinancementRepository;

    public SygTypeSourceFinancementResource(
        SygTypeSourceFinancementService sygTypeSourceFinancementService,
        SygTypeSourceFinancementRepository sygTypeSourceFinancementRepository
    ) {
        this.sygTypeSourceFinancementService = sygTypeSourceFinancementService;
        this.sygTypeSourceFinancementRepository = sygTypeSourceFinancementRepository;
    }

    /**
     * {@code POST  /syg-type-source-financements} : Create a new sygTypeSourceFinancement.
     *
     * @param sygTypeSourceFinancementDTO the sygTypeSourceFinancementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sygTypeSourceFinancementDTO, or with status {@code 400 (Bad Request)} if the sygTypeSourceFinancement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/syg-type-source-financements")
    public Mono<ResponseEntity<SygTypeSourceFinancementDTO>> createSygTypeSourceFinancement(
        @Valid @RequestBody SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO
    ) throws URISyntaxException {
        log.debug("REST request to save SygTypeSourceFinancement : {}", sygTypeSourceFinancementDTO);
        if (sygTypeSourceFinancementDTO.getId() != null) {
            throw new BadRequestAlertException("A new sygTypeSourceFinancement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return sygTypeSourceFinancementService
            .save(sygTypeSourceFinancementDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/syg-type-source-financements/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /syg-type-source-financements/:id} : Updates an existing sygTypeSourceFinancement.
     *
     * @param id the id of the sygTypeSourceFinancementDTO to save.
     * @param sygTypeSourceFinancementDTO the sygTypeSourceFinancementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sygTypeSourceFinancementDTO,
     * or with status {@code 400 (Bad Request)} if the sygTypeSourceFinancementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sygTypeSourceFinancementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/syg-type-source-financements/{id}")
    public Mono<ResponseEntity<SygTypeSourceFinancementDTO>> updateSygTypeSourceFinancement(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SygTypeSourceFinancement : {}, {}", id, sygTypeSourceFinancementDTO);
        if (sygTypeSourceFinancementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sygTypeSourceFinancementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return sygTypeSourceFinancementRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return sygTypeSourceFinancementService
                    .update(sygTypeSourceFinancementDTO)
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
     * {@code PATCH  /syg-type-source-financements/:id} : Partial updates given fields of an existing sygTypeSourceFinancement, field will ignore if it is null
     *
     * @param id the id of the sygTypeSourceFinancementDTO to save.
     * @param sygTypeSourceFinancementDTO the sygTypeSourceFinancementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sygTypeSourceFinancementDTO,
     * or with status {@code 400 (Bad Request)} if the sygTypeSourceFinancementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sygTypeSourceFinancementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sygTypeSourceFinancementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/syg-type-source-financements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<SygTypeSourceFinancementDTO>> partialUpdateSygTypeSourceFinancement(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SygTypeSourceFinancement partially : {}, {}", id, sygTypeSourceFinancementDTO);
        if (sygTypeSourceFinancementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sygTypeSourceFinancementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return sygTypeSourceFinancementRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<SygTypeSourceFinancementDTO> result = sygTypeSourceFinancementService.partialUpdate(sygTypeSourceFinancementDTO);

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
     * {@code GET  /syg-type-source-financements} : get all the sygTypeSourceFinancements.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sygTypeSourceFinancements in body.
     */
    @GetMapping("/syg-type-source-financements")
    public Mono<ResponseEntity<List<SygTypeSourceFinancementDTO>>> getAllSygTypeSourceFinancements(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of SygTypeSourceFinancements");
        return sygTypeSourceFinancementService
            .countAll()
            .zipWith(sygTypeSourceFinancementService.findAll(pageable).collectList())
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
     * {@code GET  /syg-type-source-financements/:id} : get the "id" sygTypeSourceFinancement.
     *
     * @param id the id of the sygTypeSourceFinancementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sygTypeSourceFinancementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/syg-type-source-financements/{id}")
    public Mono<ResponseEntity<SygTypeSourceFinancementDTO>> getSygTypeSourceFinancement(@PathVariable Long id) {
        log.debug("REST request to get SygTypeSourceFinancement : {}", id);
        Mono<SygTypeSourceFinancementDTO> sygTypeSourceFinancementDTO = sygTypeSourceFinancementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sygTypeSourceFinancementDTO);
    }

    /**
     * {@code DELETE  /syg-type-source-financements/:id} : delete the "id" sygTypeSourceFinancement.
     *
     * @param id the id of the sygTypeSourceFinancementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/syg-type-source-financements/{id}")
    public Mono<ResponseEntity<Void>> deleteSygTypeSourceFinancement(@PathVariable Long id) {
        log.debug("REST request to delete SygTypeSourceFinancement : {}", id);
        return sygTypeSourceFinancementService
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
