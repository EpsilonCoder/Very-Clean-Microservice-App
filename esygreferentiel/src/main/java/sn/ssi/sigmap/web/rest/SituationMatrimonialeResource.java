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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sn.ssi.sigmap.domain.SituationMatrimoniale;
import sn.ssi.sigmap.repository.SituationMatrimonialeRepository;
import sn.ssi.sigmap.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.ssi.sigmap.domain.SituationMatrimoniale}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SituationMatrimonialeResource {

    private final Logger log = LoggerFactory.getLogger(SituationMatrimonialeResource.class);

    private static final String ENTITY_NAME = "referentielmsSituationMatrimoniale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SituationMatrimonialeRepository situationMatrimonialeRepository;

    public SituationMatrimonialeResource(SituationMatrimonialeRepository situationMatrimonialeRepository) {
        this.situationMatrimonialeRepository = situationMatrimonialeRepository;
    }

    /**
     * {@code POST  /situation-matrimoniales} : Create a new situationMatrimoniale.
     *
     * @param situationMatrimoniale the situationMatrimoniale to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new situationMatrimoniale, or with status {@code 400 (Bad Request)} if the situationMatrimoniale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/situation-matrimoniales")
    public ResponseEntity<SituationMatrimoniale> createSituationMatrimoniale(
        @Valid @RequestBody SituationMatrimoniale situationMatrimoniale
    ) throws URISyntaxException {
        log.debug("REST request to save SituationMatrimoniale : {}", situationMatrimoniale);
        if (situationMatrimoniale.getId() != null) {
            throw new BadRequestAlertException("A new situationMatrimoniale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SituationMatrimoniale result = situationMatrimonialeRepository.save(situationMatrimoniale);
        return ResponseEntity
            .created(new URI("/api/situation-matrimoniales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /situation-matrimoniales/:id} : Updates an existing situationMatrimoniale.
     *
     * @param id the id of the situationMatrimoniale to save.
     * @param situationMatrimoniale the situationMatrimoniale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated situationMatrimoniale,
     * or with status {@code 400 (Bad Request)} if the situationMatrimoniale is not valid,
     * or with status {@code 500 (Internal Server Error)} if the situationMatrimoniale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/situation-matrimoniales/{id}")
    public ResponseEntity<SituationMatrimoniale> updateSituationMatrimoniale(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SituationMatrimoniale situationMatrimoniale
    ) throws URISyntaxException {
        log.debug("REST request to update SituationMatrimoniale : {}, {}", id, situationMatrimoniale);
        if (situationMatrimoniale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, situationMatrimoniale.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!situationMatrimonialeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SituationMatrimoniale result = situationMatrimonialeRepository.save(situationMatrimoniale);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, situationMatrimoniale.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /situation-matrimoniales/:id} : Partial updates given fields of an existing situationMatrimoniale, field will ignore if it is null
     *
     * @param id the id of the situationMatrimoniale to save.
     * @param situationMatrimoniale the situationMatrimoniale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated situationMatrimoniale,
     * or with status {@code 400 (Bad Request)} if the situationMatrimoniale is not valid,
     * or with status {@code 404 (Not Found)} if the situationMatrimoniale is not found,
     * or with status {@code 500 (Internal Server Error)} if the situationMatrimoniale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/situation-matrimoniales/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SituationMatrimoniale> partialUpdateSituationMatrimoniale(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SituationMatrimoniale situationMatrimoniale
    ) throws URISyntaxException {
        log.debug("REST request to partial update SituationMatrimoniale partially : {}, {}", id, situationMatrimoniale);
        if (situationMatrimoniale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, situationMatrimoniale.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!situationMatrimonialeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SituationMatrimoniale> result = situationMatrimonialeRepository
            .findById(situationMatrimoniale.getId())
            .map(existingSituationMatrimoniale -> {
                if (situationMatrimoniale.getLibelle() != null) {
                    existingSituationMatrimoniale.setLibelle(situationMatrimoniale.getLibelle());
                }

                return existingSituationMatrimoniale;
            })
            .map(situationMatrimonialeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, situationMatrimoniale.getId().toString())
        );
    }

    /**
     * {@code GET  /situation-matrimoniales} : get all the situationMatrimoniales.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of situationMatrimoniales in body.
     */
    @GetMapping("/situation-matrimoniales")
    public ResponseEntity<List<SituationMatrimoniale>> getAllSituationMatrimoniales(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of SituationMatrimoniales");
        Page<SituationMatrimoniale> page = situationMatrimonialeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /situation-matrimoniales/:id} : get the "id" situationMatrimoniale.
     *
     * @param id the id of the situationMatrimoniale to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the situationMatrimoniale, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/situation-matrimoniales/{id}")
    public ResponseEntity<SituationMatrimoniale> getSituationMatrimoniale(@PathVariable Long id) {
        log.debug("REST request to get SituationMatrimoniale : {}", id);
        Optional<SituationMatrimoniale> situationMatrimoniale = situationMatrimonialeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(situationMatrimoniale);
    }

    /**
     * {@code DELETE  /situation-matrimoniales/:id} : delete the "id" situationMatrimoniale.
     *
     * @param id the id of the situationMatrimoniale to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/situation-matrimoniales/{id}")
    public ResponseEntity<Void> deleteSituationMatrimoniale(@PathVariable Long id) {
        log.debug("REST request to delete SituationMatrimoniale : {}", id);
        situationMatrimonialeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
