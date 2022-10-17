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
import sn.ssi.sigmap.domain.Categories;
import sn.ssi.sigmap.repository.CategoriesRepository;
import sn.ssi.sigmap.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.ssi.sigmap.domain.Categories}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CategoriesResource {

    private final Logger log = LoggerFactory.getLogger(CategoriesResource.class);

    private static final String ENTITY_NAME = "referentielmsCategories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriesRepository categoriesRepository;

    public CategoriesResource(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    /**
     * {@code POST  /categories} : Create a new categories.
     *
     * @param categories the categories to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categories, or with status {@code 400 (Bad Request)} if the categories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categories")
    public ResponseEntity<Categories> createCategories(@Valid @RequestBody Categories categories) throws URISyntaxException {
        log.debug("REST request to save Categories : {}", categories);
        if (categories.getId() != null) {
            throw new BadRequestAlertException("A new categories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Categories result = categoriesRepository.save(categories);
        return ResponseEntity
            .created(new URI("/api/categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categories/:id} : Updates an existing categories.
     *
     * @param id the id of the categories to save.
     * @param categories the categories to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categories,
     * or with status {@code 400 (Bad Request)} if the categories is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categories couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categories/{id}")
    public ResponseEntity<Categories> updateCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Categories categories
    ) throws URISyntaxException {
        log.debug("REST request to update Categories : {}, {}", id, categories);
        if (categories.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categories.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Categories result = categoriesRepository.save(categories);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categories.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /categories/:id} : Partial updates given fields of an existing categories, field will ignore if it is null
     *
     * @param id the id of the categories to save.
     * @param categories the categories to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categories,
     * or with status {@code 400 (Bad Request)} if the categories is not valid,
     * or with status {@code 404 (Not Found)} if the categories is not found,
     * or with status {@code 500 (Internal Server Error)} if the categories couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Categories> partialUpdateCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Categories categories
    ) throws URISyntaxException {
        log.debug("REST request to partial update Categories partially : {}, {}", id, categories);
        if (categories.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categories.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Categories> result = categoriesRepository
            .findById(categories.getId())
            .map(existingCategories -> {
                if (categories.getCode() != null) {
                    existingCategories.setCode(categories.getCode());
                }
                if (categories.getDesignation() != null) {
                    existingCategories.setDesignation(categories.getDesignation());
                }
                if (categories.getCommentaire() != null) {
                    existingCategories.setCommentaire(categories.getCommentaire());
                }

                return existingCategories;
            })
            .map(categoriesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categories.getId().toString())
        );
    }

    /**
     * {@code GET  /categories} : get all the categories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("/categories")
    public ResponseEntity<List<Categories>> getAllCategories(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Categories");
        Page<Categories> page = categoriesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categories/:id} : get the "id" categories.
     *
     * @param id the id of the categories to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categories, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<Categories> getCategories(@PathVariable Long id) {
        log.debug("REST request to get Categories : {}", id);
        Optional<Categories> categories = categoriesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(categories);
    }

    /**
     * {@code DELETE  /categories/:id} : delete the "id" categories.
     *
     * @param id the id of the categories to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategories(@PathVariable Long id) {
        log.debug("REST request to delete Categories : {}", id);
        categoriesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
