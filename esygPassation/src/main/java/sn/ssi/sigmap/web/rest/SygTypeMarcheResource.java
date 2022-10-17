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
import sn.ssi.sigmap.repository.SygTypeMarcheRepository;
import sn.ssi.sigmap.service.SygTypeMarcheService;
import sn.ssi.sigmap.service.dto.SygTypeMarcheDTO;
import sn.ssi.sigmap.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link sn.ssi.sigmap.domain.SygTypeMarche}.
 */
@RestController
@RequestMapping("/api")
public class SygTypeMarcheResource {

    private final Logger log = LoggerFactory.getLogger(SygTypeMarcheResource.class);

    private static final String ENTITY_NAME = "planpassationmsSygTypeMarche";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SygTypeMarcheService sygTypeMarcheService;

    private final SygTypeMarcheRepository sygTypeMarcheRepository;

    public SygTypeMarcheResource(SygTypeMarcheService sygTypeMarcheService, SygTypeMarcheRepository sygTypeMarcheRepository) {
        this.sygTypeMarcheService = sygTypeMarcheService;
        this.sygTypeMarcheRepository = sygTypeMarcheRepository;
    }

    /**
     * {@code POST  /syg-type-marches} : Create a new sygTypeMarche.
     *
     * @param sygTypeMarcheDTO the sygTypeMarcheDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sygTypeMarcheDTO, or with status {@code 400 (Bad Request)} if the sygTypeMarche has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/syg-type-marches")
    public Mono<ResponseEntity<SygTypeMarcheDTO>> createSygTypeMarche(@Valid @RequestBody SygTypeMarcheDTO sygTypeMarcheDTO)
        throws URISyntaxException {
        log.debug("REST request to save SygTypeMarche : {}", sygTypeMarcheDTO);
        if (sygTypeMarcheDTO.getId() != null) {
            throw new BadRequestAlertException("A new sygTypeMarche cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return sygTypeMarcheService
            .save(sygTypeMarcheDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/syg-type-marches/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /syg-type-marches/:id} : Updates an existing sygTypeMarche.
     *
     * @param id the id of the sygTypeMarcheDTO to save.
     * @param sygTypeMarcheDTO the sygTypeMarcheDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sygTypeMarcheDTO,
     * or with status {@code 400 (Bad Request)} if the sygTypeMarcheDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sygTypeMarcheDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/syg-type-marches/{id}")
    public Mono<ResponseEntity<SygTypeMarcheDTO>> updateSygTypeMarche(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SygTypeMarcheDTO sygTypeMarcheDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SygTypeMarche : {}, {}", id, sygTypeMarcheDTO);
        if (sygTypeMarcheDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sygTypeMarcheDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return sygTypeMarcheRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return sygTypeMarcheService
                    .update(sygTypeMarcheDTO)
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
     * {@code PATCH  /syg-type-marches/:id} : Partial updates given fields of an existing sygTypeMarche, field will ignore if it is null
     *
     * @param id the id of the sygTypeMarcheDTO to save.
     * @param sygTypeMarcheDTO the sygTypeMarcheDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sygTypeMarcheDTO,
     * or with status {@code 400 (Bad Request)} if the sygTypeMarcheDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sygTypeMarcheDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sygTypeMarcheDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/syg-type-marches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<SygTypeMarcheDTO>> partialUpdateSygTypeMarche(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SygTypeMarcheDTO sygTypeMarcheDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SygTypeMarche partially : {}, {}", id, sygTypeMarcheDTO);
        if (sygTypeMarcheDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sygTypeMarcheDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return sygTypeMarcheRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<SygTypeMarcheDTO> result = sygTypeMarcheService.partialUpdate(sygTypeMarcheDTO);

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
     * {@code GET  /syg-type-marches} : get all the sygTypeMarches.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sygTypeMarches in body.
     */
    @GetMapping("/syg-type-marches")
    public Mono<ResponseEntity<List<SygTypeMarcheDTO>>> getAllSygTypeMarches(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of SygTypeMarches");
        return sygTypeMarcheService
            .countAll()
            .zipWith(sygTypeMarcheService.findAll(pageable).collectList())
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
     * {@code GET  /syg-type-marches/:id} : get the "id" sygTypeMarche.
     *
     * @param id the id of the sygTypeMarcheDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sygTypeMarcheDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/syg-type-marches/{id}")
    public Mono<ResponseEntity<SygTypeMarcheDTO>> getSygTypeMarche(@PathVariable Long id) {
        log.debug("REST request to get SygTypeMarche : {}", id);
        Mono<SygTypeMarcheDTO> sygTypeMarcheDTO = sygTypeMarcheService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sygTypeMarcheDTO);
    }

    /**
     * {@code DELETE  /syg-type-marches/:id} : delete the "id" sygTypeMarche.
     *
     * @param id the id of the sygTypeMarcheDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/syg-type-marches/{id}")
    public Mono<ResponseEntity<Void>> deleteSygTypeMarche(@PathVariable Long id) {
        log.debug("REST request to delete SygTypeMarche : {}", id);
        return sygTypeMarcheService
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
