package sn.ssi.sigmap.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import sn.ssi.sigmap.domain.CriteresQualification;
import sn.ssi.sigmap.repository.CriteresQualificationRepository;
import sn.ssi.sigmap.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.ssi.sigmap.domain.CriteresQualification}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CriteresQualificationResource {

    private final Logger log = LoggerFactory.getLogger(CriteresQualificationResource.class);

    private static final String ENTITY_NAME = "referentielmsCriteresQualification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CriteresQualificationRepository criteresQualificationRepository;

    public CriteresQualificationResource(CriteresQualificationRepository criteresQualificationRepository) {
        this.criteresQualificationRepository = criteresQualificationRepository;
    }

    /**
     * {@code POST  /criteres-qualifications} : Create a new criteresQualification.
     *
     * @param criteresQualification the criteresQualification to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new criteresQualification, or with status {@code 400 (Bad Request)} if the criteresQualification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/criteres-qualifications")
    public ResponseEntity<CriteresQualification> createCriteresQualification(@RequestBody CriteresQualification criteresQualification)
        throws URISyntaxException {
        log.debug("REST request to save CriteresQualification : {}", criteresQualification);
        if (criteresQualification.getId() != null) {
            throw new BadRequestAlertException("A new criteresQualification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CriteresQualification result = criteresQualificationRepository.save(criteresQualification);
        return ResponseEntity
            .created(new URI("/api/criteres-qualifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /criteres-qualifications/:id} : Updates an existing criteresQualification.
     *
     * @param id the id of the criteresQualification to save.
     * @param criteresQualification the criteresQualification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated criteresQualification,
     * or with status {@code 400 (Bad Request)} if the criteresQualification is not valid,
     * or with status {@code 500 (Internal Server Error)} if the criteresQualification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/criteres-qualifications/{id}")
    public ResponseEntity<CriteresQualification> updateCriteresQualification(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CriteresQualification criteresQualification
    ) throws URISyntaxException {
        log.debug("REST request to update CriteresQualification : {}, {}", id, criteresQualification);
        if (criteresQualification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, criteresQualification.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!criteresQualificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CriteresQualification result = criteresQualificationRepository.save(criteresQualification);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, criteresQualification.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /criteres-qualifications/:id} : Partial updates given fields of an existing criteresQualification, field will ignore if it is null
     *
     * @param id the id of the criteresQualification to save.
     * @param criteresQualification the criteresQualification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated criteresQualification,
     * or with status {@code 400 (Bad Request)} if the criteresQualification is not valid,
     * or with status {@code 404 (Not Found)} if the criteresQualification is not found,
     * or with status {@code 500 (Internal Server Error)} if the criteresQualification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/criteres-qualifications/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CriteresQualification> partialUpdateCriteresQualification(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CriteresQualification criteresQualification
    ) throws URISyntaxException {
        log.debug("REST request to partial update CriteresQualification partially : {}, {}", id, criteresQualification);
        if (criteresQualification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, criteresQualification.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!criteresQualificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CriteresQualification> result = criteresQualificationRepository
            .findById(criteresQualification.getId())
            .map(existingCriteresQualification -> {
                if (criteresQualification.getLibelle() != null) {
                    existingCriteresQualification.setLibelle(criteresQualification.getLibelle());
                }

                return existingCriteresQualification;
            })
            .map(criteresQualificationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, criteresQualification.getId().toString())
        );
    }

    /**
     * {@code GET  /criteres-qualifications} : get all the criteresQualifications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of criteresQualifications in body.
     */
    @GetMapping("/criteres-qualifications")
    public ResponseEntity<List<CriteresQualification>> getAllCriteresQualifications(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CriteresQualifications");
        Page<CriteresQualification> page = criteresQualificationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /criteres-qualifications/:id} : get the "id" criteresQualification.
     *
     * @param id the id of the criteresQualification to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the criteresQualification, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/criteres-qualifications/{id}")
    public ResponseEntity<CriteresQualification> getCriteresQualification(@PathVariable Long id) {
        log.debug("REST request to get CriteresQualification : {}", id);
        Optional<CriteresQualification> criteresQualification = criteresQualificationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(criteresQualification);
    }

    /**
     * {@code DELETE  /criteres-qualifications/:id} : delete the "id" criteresQualification.
     *
     * @param id the id of the criteresQualification to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/criteres-qualifications/{id}")
    public ResponseEntity<Void> deleteCriteresQualification(@PathVariable Long id) {
        log.debug("REST request to delete CriteresQualification : {}", id);
        criteresQualificationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
