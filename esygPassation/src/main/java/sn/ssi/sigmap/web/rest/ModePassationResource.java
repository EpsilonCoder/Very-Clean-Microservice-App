package sn.ssi.sigmap.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.ModePassation;
import sn.ssi.sigmap.repository.ModePassationRepository;
import sn.ssi.sigmap.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link sn.ssi.sigmap.domain.ModePassation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ModePassationResource {

    private final Logger log = LoggerFactory.getLogger(ModePassationResource.class);

    private static final String ENTITY_NAME = "planpassationmsModePassation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModePassationRepository modePassationRepository;

    public ModePassationResource(ModePassationRepository modePassationRepository) {
        this.modePassationRepository = modePassationRepository;
    }

    /**
     * {@code POST  /mode-passations} : Create a new modePassation.
     *
     * @param modePassation the modePassation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modePassation, or with status {@code 400 (Bad Request)} if the modePassation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mode-passations")
    public Mono<ResponseEntity<ModePassation>> createModePassation(@Valid @RequestBody ModePassation modePassation)
        throws URISyntaxException {
        log.debug("REST request to save ModePassation : {}", modePassation);
        if (modePassation.getId() != null) {
            throw new BadRequestAlertException("A new modePassation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return modePassationRepository
            .save(modePassation)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/mode-passations/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /mode-passations/:id} : Updates an existing modePassation.
     *
     * @param id the id of the modePassation to save.
     * @param modePassation the modePassation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modePassation,
     * or with status {@code 400 (Bad Request)} if the modePassation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modePassation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mode-passations/{id}")
    public Mono<ResponseEntity<ModePassation>> updateModePassation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ModePassation modePassation
    ) throws URISyntaxException {
        log.debug("REST request to update ModePassation : {}, {}", id, modePassation);
        if (modePassation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modePassation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return modePassationRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return modePassationRepository
                    .save(modePassation)
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
     * {@code PATCH  /mode-passations/:id} : Partial updates given fields of an existing modePassation, field will ignore if it is null
     *
     * @param id the id of the modePassation to save.
     * @param modePassation the modePassation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modePassation,
     * or with status {@code 400 (Bad Request)} if the modePassation is not valid,
     * or with status {@code 404 (Not Found)} if the modePassation is not found,
     * or with status {@code 500 (Internal Server Error)} if the modePassation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mode-passations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ModePassation>> partialUpdateModePassation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ModePassation modePassation
    ) throws URISyntaxException {
        log.debug("REST request to partial update ModePassation partially : {}, {}", id, modePassation);
        if (modePassation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modePassation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return modePassationRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<ModePassation> result = modePassationRepository
                    .findById(modePassation.getId())
                    .map(existingModePassation -> {
                        if (modePassation.getLibelle() != null) {
                            existingModePassation.setLibelle(modePassation.getLibelle());
                        }
                        if (modePassation.getCode() != null) {
                            existingModePassation.setCode(modePassation.getCode());
                        }
                        if (modePassation.getDescription() != null) {
                            existingModePassation.setDescription(modePassation.getDescription());
                        }

                        return existingModePassation;
                    })
                    .flatMap(modePassationRepository::save);

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
     * {@code GET  /mode-passations} : get all the modePassations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modePassations in body.
     */
    @GetMapping("/mode-passations")
    public Mono<List<ModePassation>> getAllModePassations() {
        log.debug("REST request to get all ModePassations");
        return modePassationRepository.findAll().collectList();
    }

    /**
     * {@code GET  /mode-passations} : get all the modePassations as a stream.
     * @return the {@link Flux} of modePassations.
     */
    @GetMapping(value = "/mode-passations", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ModePassation> getAllModePassationsAsStream() {
        log.debug("REST request to get all ModePassations as a stream");
        return modePassationRepository.findAll();
    }

    /**
     * {@code GET  /mode-passations/:id} : get the "id" modePassation.
     *
     * @param id the id of the modePassation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modePassation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mode-passations/{id}")
    public Mono<ResponseEntity<ModePassation>> getModePassation(@PathVariable Long id) {
        log.debug("REST request to get ModePassation : {}", id);
        Mono<ModePassation> modePassation = modePassationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(modePassation);
    }

    /**
     * {@code DELETE  /mode-passations/:id} : delete the "id" modePassation.
     *
     * @param id the id of the modePassation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mode-passations/{id}")
    public Mono<ResponseEntity<Void>> deleteModePassation(@PathVariable Long id) {
        log.debug("REST request to delete ModePassation : {}", id);
        return modePassationRepository
            .deleteById(id)
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
