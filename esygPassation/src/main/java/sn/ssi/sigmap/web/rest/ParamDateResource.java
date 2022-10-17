package sn.ssi.sigmap.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import sn.ssi.sigmap.repository.ParamDateRepository;
import sn.ssi.sigmap.service.ParamDateService;
import sn.ssi.sigmap.service.dto.ParamDateDTO;
import sn.ssi.sigmap.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link sn.ssi.sigmap.domain.ParamDate}.
 */
@RestController
@RequestMapping("/api")
public class ParamDateResource {

    private final Logger log = LoggerFactory.getLogger(ParamDateResource.class);

    private static final String ENTITY_NAME = "planpassationmsParamDate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParamDateService paramDateService;

    private final ParamDateRepository paramDateRepository;

    public ParamDateResource(ParamDateService paramDateService, ParamDateRepository paramDateRepository) {
        this.paramDateService = paramDateService;
        this.paramDateRepository = paramDateRepository;
    }

    /**
     * {@code POST  /param-dates} : Create a new paramDate.
     *
     * @param paramDateDTO the paramDateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paramDateDTO, or with status {@code 400 (Bad Request)} if the paramDate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/param-dates")
    public Mono<ResponseEntity<ParamDateDTO>> createParamDate(@RequestBody ParamDateDTO paramDateDTO) throws URISyntaxException {
        log.debug("REST request to save ParamDate : {}", paramDateDTO);
        if (paramDateDTO.getId() != null) {
            throw new BadRequestAlertException("A new paramDate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return paramDateService
            .save(paramDateDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/param-dates/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /param-dates/:id} : Updates an existing paramDate.
     *
     * @param id the id of the paramDateDTO to save.
     * @param paramDateDTO the paramDateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paramDateDTO,
     * or with status {@code 400 (Bad Request)} if the paramDateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paramDateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/param-dates/{id}")
    public Mono<ResponseEntity<ParamDateDTO>> updateParamDate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ParamDateDTO paramDateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ParamDate : {}, {}", id, paramDateDTO);
        if (paramDateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paramDateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return paramDateRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return paramDateService
                    .update(paramDateDTO)
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
     * {@code PATCH  /param-dates/:id} : Partial updates given fields of an existing paramDate, field will ignore if it is null
     *
     * @param id the id of the paramDateDTO to save.
     * @param paramDateDTO the paramDateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paramDateDTO,
     * or with status {@code 400 (Bad Request)} if the paramDateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paramDateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paramDateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/param-dates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ParamDateDTO>> partialUpdateParamDate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ParamDateDTO paramDateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ParamDate partially : {}, {}", id, paramDateDTO);
        if (paramDateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paramDateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return paramDateRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<ParamDateDTO> result = paramDateService.partialUpdate(paramDateDTO);

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
     * {@code GET  /param-dates} : get all the paramDates.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paramDates in body.
     */
    @GetMapping("/param-dates")
    public Mono<ResponseEntity<List<ParamDateDTO>>> getAllParamDates(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of ParamDates");
        return paramDateService
            .countAll()
            .zipWith(paramDateService.findAll(pageable).collectList())
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
     * {@code GET  /param-dates/:id} : get the "id" paramDate.
     *
     * @param id the id of the paramDateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paramDateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/param-dates/{id}")
    public Mono<ResponseEntity<ParamDateDTO>> getParamDate(@PathVariable Long id) {
        log.debug("REST request to get ParamDate : {}", id);
        Mono<ParamDateDTO> paramDateDTO = paramDateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paramDateDTO);
    }

    /**
     * {@code DELETE  /param-dates/:id} : delete the "id" paramDate.
     *
     * @param id the id of the paramDateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/param-dates/{id}")
    public Mono<ResponseEntity<Void>> deleteParamDate(@PathVariable Long id) {
        log.debug("REST request to delete ParamDate : {}", id);
        return paramDateService
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
