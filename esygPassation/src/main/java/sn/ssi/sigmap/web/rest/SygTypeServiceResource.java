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
import sn.ssi.sigmap.repository.SygTypeServiceRepository;
import sn.ssi.sigmap.service.SygTypeServiceService;
import sn.ssi.sigmap.service.dto.SygTypeServiceDTO;
import sn.ssi.sigmap.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link sn.ssi.sigmap.domain.SygTypeService}.
 */
@RestController
@RequestMapping("/api")
public class SygTypeServiceResource {

    private final Logger log = LoggerFactory.getLogger(SygTypeServiceResource.class);

    private static final String ENTITY_NAME = "planpassationmsSygTypeService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SygTypeServiceService sygTypeServiceService;

    private final SygTypeServiceRepository sygTypeServiceRepository;

    public SygTypeServiceResource(SygTypeServiceService sygTypeServiceService, SygTypeServiceRepository sygTypeServiceRepository) {
        this.sygTypeServiceService = sygTypeServiceService;
        this.sygTypeServiceRepository = sygTypeServiceRepository;
    }

    /**
     * {@code POST  /syg-type-services} : Create a new sygTypeService.
     *
     * @param sygTypeServiceDTO the sygTypeServiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sygTypeServiceDTO, or with status {@code 400 (Bad Request)} if the sygTypeService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/syg-type-services")
    public Mono<ResponseEntity<SygTypeServiceDTO>> createSygTypeService(@Valid @RequestBody SygTypeServiceDTO sygTypeServiceDTO)
        throws URISyntaxException {
        log.debug("REST request to save SygTypeService : {}", sygTypeServiceDTO);
        if (sygTypeServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new sygTypeService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return sygTypeServiceService
            .save(sygTypeServiceDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/syg-type-services/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /syg-type-services/:id} : Updates an existing sygTypeService.
     *
     * @param id the id of the sygTypeServiceDTO to save.
     * @param sygTypeServiceDTO the sygTypeServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sygTypeServiceDTO,
     * or with status {@code 400 (Bad Request)} if the sygTypeServiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sygTypeServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/syg-type-services/{id}")
    public Mono<ResponseEntity<SygTypeServiceDTO>> updateSygTypeService(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SygTypeServiceDTO sygTypeServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SygTypeService : {}, {}", id, sygTypeServiceDTO);
        if (sygTypeServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sygTypeServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return sygTypeServiceRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return sygTypeServiceService
                    .update(sygTypeServiceDTO)
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
     * {@code PATCH  /syg-type-services/:id} : Partial updates given fields of an existing sygTypeService, field will ignore if it is null
     *
     * @param id the id of the sygTypeServiceDTO to save.
     * @param sygTypeServiceDTO the sygTypeServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sygTypeServiceDTO,
     * or with status {@code 400 (Bad Request)} if the sygTypeServiceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sygTypeServiceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sygTypeServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/syg-type-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<SygTypeServiceDTO>> partialUpdateSygTypeService(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SygTypeServiceDTO sygTypeServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SygTypeService partially : {}, {}", id, sygTypeServiceDTO);
        if (sygTypeServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sygTypeServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return sygTypeServiceRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<SygTypeServiceDTO> result = sygTypeServiceService.partialUpdate(sygTypeServiceDTO);

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
     * {@code GET  /syg-type-services} : get all the sygTypeServices.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sygTypeServices in body.
     */
    @GetMapping("/syg-type-services")
    public Mono<ResponseEntity<List<SygTypeServiceDTO>>> getAllSygTypeServices(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of SygTypeServices");
        return sygTypeServiceService
            .countAll()
            .zipWith(sygTypeServiceService.findAll(pageable).collectList())
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
     * {@code GET  /syg-type-services/:id} : get the "id" sygTypeService.
     *
     * @param id the id of the sygTypeServiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sygTypeServiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/syg-type-services/{id}")
    public Mono<ResponseEntity<SygTypeServiceDTO>> getSygTypeService(@PathVariable Long id) {
        log.debug("REST request to get SygTypeService : {}", id);
        Mono<SygTypeServiceDTO> sygTypeServiceDTO = sygTypeServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sygTypeServiceDTO);
    }

    /**
     * {@code DELETE  /syg-type-services/:id} : delete the "id" sygTypeService.
     *
     * @param id the id of the sygTypeServiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/syg-type-services/{id}")
    public Mono<ResponseEntity<Void>> deleteSygTypeService(@PathVariable Long id) {
        log.debug("REST request to delete SygTypeService : {}", id);
        return sygTypeServiceService
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
