package sn.ssi.sigmap.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import sn.ssi.sigmap.IntegrationTest;
import sn.ssi.sigmap.domain.SygTypeSourceFinancement;
import sn.ssi.sigmap.repository.EntityManager;
import sn.ssi.sigmap.repository.SygTypeSourceFinancementRepository;
import sn.ssi.sigmap.service.dto.SygTypeSourceFinancementDTO;
import sn.ssi.sigmap.service.mapper.SygTypeSourceFinancementMapper;

/**
 * Integration tests for the {@link SygTypeSourceFinancementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class SygTypeSourceFinancementResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/syg-type-source-financements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SygTypeSourceFinancementRepository sygTypeSourceFinancementRepository;

    @Autowired
    private SygTypeSourceFinancementMapper sygTypeSourceFinancementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private SygTypeSourceFinancement sygTypeSourceFinancement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SygTypeSourceFinancement createEntity(EntityManager em) {
        SygTypeSourceFinancement sygTypeSourceFinancement = new SygTypeSourceFinancement().libelle(DEFAULT_LIBELLE);
        return sygTypeSourceFinancement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SygTypeSourceFinancement createUpdatedEntity(EntityManager em) {
        SygTypeSourceFinancement sygTypeSourceFinancement = new SygTypeSourceFinancement().libelle(UPDATED_LIBELLE);
        return sygTypeSourceFinancement;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(SygTypeSourceFinancement.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void setupCsrf() {
        webTestClient = webTestClient.mutateWith(csrf());
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        sygTypeSourceFinancement = createEntity(em);
    }

    @Test
    void createSygTypeSourceFinancement() throws Exception {
        int databaseSizeBeforeCreate = sygTypeSourceFinancementRepository.findAll().collectList().block().size();
        // Create the SygTypeSourceFinancement
        SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO = sygTypeSourceFinancementMapper.toDto(sygTypeSourceFinancement);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the SygTypeSourceFinancement in the database
        List<SygTypeSourceFinancement> sygTypeSourceFinancementList = sygTypeSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygTypeSourceFinancementList).hasSize(databaseSizeBeforeCreate + 1);
        SygTypeSourceFinancement testSygTypeSourceFinancement = sygTypeSourceFinancementList.get(sygTypeSourceFinancementList.size() - 1);
        assertThat(testSygTypeSourceFinancement.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    void createSygTypeSourceFinancementWithExistingId() throws Exception {
        // Create the SygTypeSourceFinancement with an existing ID
        sygTypeSourceFinancement.setId(1L);
        SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO = sygTypeSourceFinancementMapper.toDto(sygTypeSourceFinancement);

        int databaseSizeBeforeCreate = sygTypeSourceFinancementRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygTypeSourceFinancement in the database
        List<SygTypeSourceFinancement> sygTypeSourceFinancementList = sygTypeSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygTypeSourceFinancementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = sygTypeSourceFinancementRepository.findAll().collectList().block().size();
        // set the field null
        sygTypeSourceFinancement.setLibelle(null);

        // Create the SygTypeSourceFinancement, which fails.
        SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO = sygTypeSourceFinancementMapper.toDto(sygTypeSourceFinancement);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<SygTypeSourceFinancement> sygTypeSourceFinancementList = sygTypeSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygTypeSourceFinancementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllSygTypeSourceFinancements() {
        // Initialize the database
        sygTypeSourceFinancementRepository.save(sygTypeSourceFinancement).block();

        // Get all the sygTypeSourceFinancementList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(sygTypeSourceFinancement.getId().intValue()))
            .jsonPath("$.[*].libelle")
            .value(hasItem(DEFAULT_LIBELLE));
    }

    @Test
    void getSygTypeSourceFinancement() {
        // Initialize the database
        sygTypeSourceFinancementRepository.save(sygTypeSourceFinancement).block();

        // Get the sygTypeSourceFinancement
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, sygTypeSourceFinancement.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(sygTypeSourceFinancement.getId().intValue()))
            .jsonPath("$.libelle")
            .value(is(DEFAULT_LIBELLE));
    }

    @Test
    void getNonExistingSygTypeSourceFinancement() {
        // Get the sygTypeSourceFinancement
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingSygTypeSourceFinancement() throws Exception {
        // Initialize the database
        sygTypeSourceFinancementRepository.save(sygTypeSourceFinancement).block();

        int databaseSizeBeforeUpdate = sygTypeSourceFinancementRepository.findAll().collectList().block().size();

        // Update the sygTypeSourceFinancement
        SygTypeSourceFinancement updatedSygTypeSourceFinancement = sygTypeSourceFinancementRepository
            .findById(sygTypeSourceFinancement.getId())
            .block();
        updatedSygTypeSourceFinancement.libelle(UPDATED_LIBELLE);
        SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO = sygTypeSourceFinancementMapper.toDto(updatedSygTypeSourceFinancement);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, sygTypeSourceFinancementDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SygTypeSourceFinancement in the database
        List<SygTypeSourceFinancement> sygTypeSourceFinancementList = sygTypeSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygTypeSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
        SygTypeSourceFinancement testSygTypeSourceFinancement = sygTypeSourceFinancementList.get(sygTypeSourceFinancementList.size() - 1);
        assertThat(testSygTypeSourceFinancement.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    void putNonExistingSygTypeSourceFinancement() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeSourceFinancementRepository.findAll().collectList().block().size();
        sygTypeSourceFinancement.setId(count.incrementAndGet());

        // Create the SygTypeSourceFinancement
        SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO = sygTypeSourceFinancementMapper.toDto(sygTypeSourceFinancement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, sygTypeSourceFinancementDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygTypeSourceFinancement in the database
        List<SygTypeSourceFinancement> sygTypeSourceFinancementList = sygTypeSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygTypeSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSygTypeSourceFinancement() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeSourceFinancementRepository.findAll().collectList().block().size();
        sygTypeSourceFinancement.setId(count.incrementAndGet());

        // Create the SygTypeSourceFinancement
        SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO = sygTypeSourceFinancementMapper.toDto(sygTypeSourceFinancement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygTypeSourceFinancement in the database
        List<SygTypeSourceFinancement> sygTypeSourceFinancementList = sygTypeSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygTypeSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSygTypeSourceFinancement() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeSourceFinancementRepository.findAll().collectList().block().size();
        sygTypeSourceFinancement.setId(count.incrementAndGet());

        // Create the SygTypeSourceFinancement
        SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO = sygTypeSourceFinancementMapper.toDto(sygTypeSourceFinancement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the SygTypeSourceFinancement in the database
        List<SygTypeSourceFinancement> sygTypeSourceFinancementList = sygTypeSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygTypeSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSygTypeSourceFinancementWithPatch() throws Exception {
        // Initialize the database
        sygTypeSourceFinancementRepository.save(sygTypeSourceFinancement).block();

        int databaseSizeBeforeUpdate = sygTypeSourceFinancementRepository.findAll().collectList().block().size();

        // Update the sygTypeSourceFinancement using partial update
        SygTypeSourceFinancement partialUpdatedSygTypeSourceFinancement = new SygTypeSourceFinancement();
        partialUpdatedSygTypeSourceFinancement.setId(sygTypeSourceFinancement.getId());

        partialUpdatedSygTypeSourceFinancement.libelle(UPDATED_LIBELLE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSygTypeSourceFinancement.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSygTypeSourceFinancement))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SygTypeSourceFinancement in the database
        List<SygTypeSourceFinancement> sygTypeSourceFinancementList = sygTypeSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygTypeSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
        SygTypeSourceFinancement testSygTypeSourceFinancement = sygTypeSourceFinancementList.get(sygTypeSourceFinancementList.size() - 1);
        assertThat(testSygTypeSourceFinancement.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    void fullUpdateSygTypeSourceFinancementWithPatch() throws Exception {
        // Initialize the database
        sygTypeSourceFinancementRepository.save(sygTypeSourceFinancement).block();

        int databaseSizeBeforeUpdate = sygTypeSourceFinancementRepository.findAll().collectList().block().size();

        // Update the sygTypeSourceFinancement using partial update
        SygTypeSourceFinancement partialUpdatedSygTypeSourceFinancement = new SygTypeSourceFinancement();
        partialUpdatedSygTypeSourceFinancement.setId(sygTypeSourceFinancement.getId());

        partialUpdatedSygTypeSourceFinancement.libelle(UPDATED_LIBELLE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSygTypeSourceFinancement.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSygTypeSourceFinancement))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SygTypeSourceFinancement in the database
        List<SygTypeSourceFinancement> sygTypeSourceFinancementList = sygTypeSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygTypeSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
        SygTypeSourceFinancement testSygTypeSourceFinancement = sygTypeSourceFinancementList.get(sygTypeSourceFinancementList.size() - 1);
        assertThat(testSygTypeSourceFinancement.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    void patchNonExistingSygTypeSourceFinancement() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeSourceFinancementRepository.findAll().collectList().block().size();
        sygTypeSourceFinancement.setId(count.incrementAndGet());

        // Create the SygTypeSourceFinancement
        SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO = sygTypeSourceFinancementMapper.toDto(sygTypeSourceFinancement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, sygTypeSourceFinancementDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygTypeSourceFinancement in the database
        List<SygTypeSourceFinancement> sygTypeSourceFinancementList = sygTypeSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygTypeSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSygTypeSourceFinancement() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeSourceFinancementRepository.findAll().collectList().block().size();
        sygTypeSourceFinancement.setId(count.incrementAndGet());

        // Create the SygTypeSourceFinancement
        SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO = sygTypeSourceFinancementMapper.toDto(sygTypeSourceFinancement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygTypeSourceFinancement in the database
        List<SygTypeSourceFinancement> sygTypeSourceFinancementList = sygTypeSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygTypeSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSygTypeSourceFinancement() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeSourceFinancementRepository.findAll().collectList().block().size();
        sygTypeSourceFinancement.setId(count.incrementAndGet());

        // Create the SygTypeSourceFinancement
        SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO = sygTypeSourceFinancementMapper.toDto(sygTypeSourceFinancement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the SygTypeSourceFinancement in the database
        List<SygTypeSourceFinancement> sygTypeSourceFinancementList = sygTypeSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygTypeSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSygTypeSourceFinancement() {
        // Initialize the database
        sygTypeSourceFinancementRepository.save(sygTypeSourceFinancement).block();

        int databaseSizeBeforeDelete = sygTypeSourceFinancementRepository.findAll().collectList().block().size();

        // Delete the sygTypeSourceFinancement
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, sygTypeSourceFinancement.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<SygTypeSourceFinancement> sygTypeSourceFinancementList = sygTypeSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygTypeSourceFinancementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
