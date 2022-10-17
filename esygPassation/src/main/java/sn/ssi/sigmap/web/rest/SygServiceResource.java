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
import sn.ssi.sigmap.repository.SygServiceRepository;
import sn.ssi.sigmap.service.SygServiceService;
import sn.ssi.sigmap.service.dto.SygServiceDTO;
import sn.ssi.sigmap.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link sn.ssi.sigmap.domain.SygService}.
 */
@RestController
@RequestMapping("/api")
public class SygServiceResource {

    private final Logger log = LoggerFactory.getLogger(SygServiceResource.class);

    private static final String ENTITY_NAME = "planpassationmsSygService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SygServiceService sygServiceService;

    private final SygServiceRepository sygServiceRepository;

    public SygServiceResource(SygServiceService sygServiceService, SygServiceRepository sygServiceRepository) {
        this.sygServiceService = sygServiceService;
        this.sygServiceRepository = sygServiceRepository;
    }

    /**
     * {@code POST  /syg-services} : Create a new sygService.
     *
     * @param sygServiceDTO the sygServiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sygServiceDTO, or with status {@code 400 (Bad Request)} if the sygService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/syg-services")
    public Mono<ResponseEntity<SygServiceDTO>> createSygService(@Valid @RequestBody SygServiceDTO sygServiceDTO) throws URISyntaxException {
        log.debug("REST request to save SygService : {}", sygServiceDTO);
        if (sygServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new sygService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return sygServiceService
            .save(sygServiceDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/syg-services/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /syg-services/:id} : Updates an existing sygService.
     *
     * @param id the id of the sygServiceDTO to save.
     * @param sygServiceDTO the sygServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sygServiceDTO,
     * or with status {@code 400 (Bad Request)} if the sygServiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sygServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/syg-services/{id}")
    public Mono<ResponseEntity<SygServiceDTO>> updateSygService(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SygServiceDTO sygServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SygService : {}, {}", id, sygServiceDTO);
        if (sygServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sygServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return sygServiceRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return sygServiceService
                    .update(sygServiceDTO)
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
     * {@code PATCH  /syg-services/:id} : Partial updates given fields of an existing sygService, field will ignore if it is null
     *
     * @param id the id of the sygServiceDTO to save.
     * @param sygServiceDTO the sygServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sygServiceDTO,
     * or with status {@code 400 (Bad Request)} if the sygServiceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sygServiceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sygServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/syg-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<SygServiceDTO>> partialUpdateSygService(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SygServiceDTO sygServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SygService partially : {}, {}", id, sygServiceDTO);
        if (sygServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sygServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return sygServiceRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<SygServiceDTO> result = sygServiceService.partialUpdate(sygServiceDTO);

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
     * {@code GET  /syg-services} : get all the sygServices.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sygServices in body.
     */
    @GetMapping("/syg-services")
    public Mono<ResponseEntity<List<SygServiceDTO>>> getAllSygServices(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of SygServices");
        return sygServiceService
            .countAll()
            .zipWith(sygServiceService.findAll(pageable).collectList())
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
     * {@code GET  /syg-services/:id} : get the "id" sygService.
     *
     * @param id the id of the sygServiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sygServiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/syg-services/{id}")
    public Mono<ResponseEntity<SygServiceDTO>> getSygService(@PathVariable Long id) {
        log.debug("REST request to get SygService : {}", id);
        Mono<SygServiceDTO> sygServiceDTO = sygServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sygServiceDTO);
    }

    /**
     * {@code DELETE  /syg-services/:id} : delete the "id" sygService.
     *
     * @param id the id of the sygServiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/syg-services/{id}")
    public Mono<ResponseEntity<Void>> deleteSygService(@PathVariable Long id) {
        log.debug("REST request to delete SygService : {}", id);
        return sygServiceService
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
