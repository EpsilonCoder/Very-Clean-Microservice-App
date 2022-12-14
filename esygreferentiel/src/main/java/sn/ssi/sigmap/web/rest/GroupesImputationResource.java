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
import sn.ssi.sigmap.domain.GroupesImputation;
import sn.ssi.sigmap.repository.GroupesImputationRepository;
import sn.ssi.sigmap.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.ssi.sigmap.domain.GroupesImputation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GroupesImputationResource {

    private final Logger log = LoggerFactory.getLogger(GroupesImputationResource.class);

    private static final String ENTITY_NAME = "referentielmsGroupesImputation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupesImputationRepository groupesImputationRepository;

    public GroupesImputationResource(GroupesImputationRepository groupesImputationRepository) {
        this.groupesImputationRepository = groupesImputationRepository;
    }

    /**
     * {@code POST  /groupes-imputations} : Create a new groupesImputation.
     *
     * @param groupesImputation the groupesImputation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupesImputation, or with status {@code 400 (Bad Request)} if the groupesImputation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/groupes-imputations")
    public ResponseEntity<GroupesImputation> createGroupesImputation(@Valid @RequestBody GroupesImputation groupesImputation)
        throws URISyntaxException {
        log.debug("REST request to save GroupesImputation : {}", groupesImputation);
        if (groupesImputation.getId() != null) {
            throw new BadRequestAlertException("A new groupesImputation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupesImputation result = groupesImputationRepository.save(groupesImputation);
        return ResponseEntity
            .created(new URI("/api/groupes-imputations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /groupes-imputations/:id} : Updates an existing groupesImputation.
     *
     * @param id the id of the groupesImputation to save.
     * @param groupesImputation the groupesImputation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupesImputation,
     * or with status {@code 400 (Bad Request)} if the groupesImputation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupesImputation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/groupes-imputations/{id}")
    public ResponseEntity<GroupesImputation> updateGroupesImputation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GroupesImputation groupesImputation
    ) throws URISyntaxException {
        log.debug("REST request to update GroupesImputation : {}, {}", id, groupesImputation);
        if (groupesImputation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groupesImputation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groupesImputationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GroupesImputation result = groupesImputationRepository.save(groupesImputation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupesImputation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /groupes-imputations/:id} : Partial updates given fields of an existing groupesImputation, field will ignore if it is null
     *
     * @param id the id of the groupesImputation to save.
     * @param groupesImputation the groupesImputation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupesImputation,
     * or with status {@code 400 (Bad Request)} if the groupesImputation is not valid,
     * or with status {@code 404 (Not Found)} if the groupesImputation is not found,
     * or with status {@code 500 (Internal Server Error)} if the groupesImputation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/groupes-imputations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GroupesImputation> partialUpdateGroupesImputation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GroupesImputation groupesImputation
    ) throws URISyntaxException {
        log.debug("REST request to partial update GroupesImputation partially : {}, {}", id, groupesImputation);
        if (groupesImputation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groupesImputation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groupesImputationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GroupesImputation> result = groupesImputationRepository
            .findById(groupesImputation.getId())
            .map(existingGroupesImputation -> {
                if (groupesImputation.getLibelle() != null) {
                    existingGroupesImputation.setLibelle(groupesImputation.getLibelle());
                }
                if (groupesImputation.getDescription() != null) {
                    existingGroupesImputation.setDescription(groupesImputation.getDescription());
                }

                return existingGroupesImputation;
            })
            .map(groupesImputationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupesImputation.getId().toString())
        );
    }

    /**
     * {@code GET  /groupes-imputations} : get all the groupesImputations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupesImputations in body.
     */
    @GetMapping("/groupes-imputations")
    public ResponseEntity<List<GroupesImputation>> getAllGroupesImputations(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of GroupesImputations");
        Page<GroupesImputation> page = groupesImputationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /groupes-imputations/:id} : get the "id" groupesImputation.
     *
     * @param id the id of the groupesImputation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupesImputation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/groupes-imputations/{id}")
    public ResponseEntity<GroupesImputation> getGroupesImputation(@PathVariable Long id) {
        log.debug("REST request to get GroupesImputation : {}", id);
        Optional<GroupesImputation> groupesImputation = groupesImputationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(groupesImputation);
    }

    /**
     * {@code DELETE  /groupes-imputations/:id} : delete the "id" groupesImputation.
     *
     * @param id the id of the groupesImputation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/groupes-imputations/{id}")
    public ResponseEntity<Void> deleteGroupesImputation(@PathVariable Long id) {
        log.debug("REST request to delete GroupesImputation : {}", id);
        groupesImputationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
