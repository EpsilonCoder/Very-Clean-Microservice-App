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
import sn.ssi.sigmap.domain.SygAutoriteContractante;
import sn.ssi.sigmap.repository.SygAutoriteContractanteRepository;
import sn.ssi.sigmap.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.ssi.sigmap.domain.SygAutoriteContractante}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SygAutoriteContractanteResource {

    private final Logger log = LoggerFactory.getLogger(SygAutoriteContractanteResource.class);

    private static final String ENTITY_NAME = "referentielmsSygAutoriteContractante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SygAutoriteContractanteRepository sygAutoriteContractanteRepository;

    public SygAutoriteContractanteResource(SygAutoriteContractanteRepository sygAutoriteContractanteRepository) {
        this.sygAutoriteContractanteRepository = sygAutoriteContractanteRepository;
    }

    /**
     * {@code POST  /syg-autorite-contractantes} : Create a new sygAutoriteContractante.
     *
     * @param sygAutoriteContractante the sygAutoriteContractante to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sygAutoriteContractante, or with status {@code 400 (Bad Request)} if the sygAutoriteContractante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/syg-autorite-contractantes")
    public ResponseEntity<SygAutoriteContractante> createSygAutoriteContractante(
        @Valid @RequestBody SygAutoriteContractante sygAutoriteContractante
    ) throws URISyntaxException {
        log.debug("REST request to save SygAutoriteContractante : {}", sygAutoriteContractante);
        if (sygAutoriteContractante.getId() != null) {
            throw new BadRequestAlertException("A new sygAutoriteContractante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SygAutoriteContractante result = sygAutoriteContractanteRepository.save(sygAutoriteContractante);
        return ResponseEntity
            .created(new URI("/api/syg-autorite-contractantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /syg-autorite-contractantes/:id} : Updates an existing sygAutoriteContractante.
     *
     * @param id the id of the sygAutoriteContractante to save.
     * @param sygAutoriteContractante the sygAutoriteContractante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sygAutoriteContractante,
     * or with status {@code 400 (Bad Request)} if the sygAutoriteContractante is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sygAutoriteContractante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/syg-autorite-contractantes/{id}")
    public ResponseEntity<SygAutoriteContractante> updateSygAutoriteContractante(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SygAutoriteContractante sygAutoriteContractante
    ) throws URISyntaxException {
        log.debug("REST request to update SygAutoriteContractante : {}, {}", id, sygAutoriteContractante);
        if (sygAutoriteContractante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sygAutoriteContractante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sygAutoriteContractanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SygAutoriteContractante result = sygAutoriteContractanteRepository.save(sygAutoriteContractante);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sygAutoriteContractante.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /syg-autorite-contractantes/:id} : Partial updates given fields of an existing sygAutoriteContractante, field will ignore if it is null
     *
     * @param id the id of the sygAutoriteContractante to save.
     * @param sygAutoriteContractante the sygAutoriteContractante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sygAutoriteContractante,
     * or with status {@code 400 (Bad Request)} if the sygAutoriteContractante is not valid,
     * or with status {@code 404 (Not Found)} if the sygAutoriteContractante is not found,
     * or with status {@code 500 (Internal Server Error)} if the sygAutoriteContractante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/syg-autorite-contractantes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SygAutoriteContractante> partialUpdateSygAutoriteContractante(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SygAutoriteContractante sygAutoriteContractante
    ) throws URISyntaxException {
        log.debug("REST request to partial update SygAutoriteContractante partially : {}, {}", id, sygAutoriteContractante);
        if (sygAutoriteContractante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sygAutoriteContractante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sygAutoriteContractanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SygAutoriteContractante> result = sygAutoriteContractanteRepository
            .findById(sygAutoriteContractante.getId())
            .map(existingSygAutoriteContractante -> {
                if (sygAutoriteContractante.getOrdre() != null) {
                    existingSygAutoriteContractante.setOrdre(sygAutoriteContractante.getOrdre());
                }
                if (sygAutoriteContractante.getDenomination() != null) {
                    existingSygAutoriteContractante.setDenomination(sygAutoriteContractante.getDenomination());
                }
                if (sygAutoriteContractante.getResponsable() != null) {
                    existingSygAutoriteContractante.setResponsable(sygAutoriteContractante.getResponsable());
                }
                if (sygAutoriteContractante.getAdresse() != null) {
                    existingSygAutoriteContractante.setAdresse(sygAutoriteContractante.getAdresse());
                }
                if (sygAutoriteContractante.getTelephone() != null) {
                    existingSygAutoriteContractante.setTelephone(sygAutoriteContractante.getTelephone());
                }
                if (sygAutoriteContractante.getFax() != null) {
                    existingSygAutoriteContractante.setFax(sygAutoriteContractante.getFax());
                }
                if (sygAutoriteContractante.getEmail() != null) {
                    existingSygAutoriteContractante.setEmail(sygAutoriteContractante.getEmail());
                }
                if (sygAutoriteContractante.getSigle() != null) {
                    existingSygAutoriteContractante.setSigle(sygAutoriteContractante.getSigle());
                }
                if (sygAutoriteContractante.getUrlsiteweb() != null) {
                    existingSygAutoriteContractante.setUrlsiteweb(sygAutoriteContractante.getUrlsiteweb());
                }
                if (sygAutoriteContractante.getApprobation() != null) {
                    existingSygAutoriteContractante.setApprobation(sygAutoriteContractante.getApprobation());
                }
                if (sygAutoriteContractante.getLogo() != null) {
                    existingSygAutoriteContractante.setLogo(sygAutoriteContractante.getLogo());
                }
                if (sygAutoriteContractante.getLogoContentType() != null) {
                    existingSygAutoriteContractante.setLogoContentType(sygAutoriteContractante.getLogoContentType());
                }

                return existingSygAutoriteContractante;
            })
            .map(sygAutoriteContractanteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sygAutoriteContractante.getId().toString())
        );
    }

    /**
     * {@code GET  /syg-autorite-contractantes} : get all the sygAutoriteContractantes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sygAutoriteContractantes in body.
     */
    @GetMapping("/syg-autorite-contractantes")
    public ResponseEntity<List<SygAutoriteContractante>> getAllSygAutoriteContractantes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of SygAutoriteContractantes");
        Page<SygAutoriteContractante> page = sygAutoriteContractanteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /syg-autorite-contractantes/:id} : get the "id" sygAutoriteContractante.
     *
     * @param id the id of the sygAutoriteContractante to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sygAutoriteContractante, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/syg-autorite-contractantes/{id}")
    public ResponseEntity<SygAutoriteContractante> getSygAutoriteContractante(@PathVariable Long id) {
        log.debug("REST request to get SygAutoriteContractante : {}", id);
        Optional<SygAutoriteContractante> sygAutoriteContractante = sygAutoriteContractanteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sygAutoriteContractante);
    }

    /**
     * {@code DELETE  /syg-autorite-contractantes/:id} : delete the "id" sygAutoriteContractante.
     *
     * @param id the id of the sygAutoriteContractante to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/syg-autorite-contractantes/{id}")
    public ResponseEntity<Void> deleteSygAutoriteContractante(@PathVariable Long id) {
        log.debug("REST request to delete SygAutoriteContractante : {}", id);
        sygAutoriteContractanteRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
