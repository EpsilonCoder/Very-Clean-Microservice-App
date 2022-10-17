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
import sn.ssi.sigmap.domain.SygTypeService;
import sn.ssi.sigmap.repository.EntityManager;
import sn.ssi.sigmap.repository.SygTypeServiceRepository;
import sn.ssi.sigmap.service.dto.SygTypeServiceDTO;
import sn.ssi.sigmap.service.mapper.SygTypeServiceMapper;

/**
 * Integration tests for the {@link SygTypeServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class SygTypeServiceResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/syg-type-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SygTypeServiceRepository sygTypeServiceRepository;

    @Autowired
    private SygTypeServiceMapper sygTypeServiceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private SygTypeService sygTypeService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SygTypeService createEntity(EntityManager em) {
        SygTypeService sygTypeService = new SygTypeService().libelle(DEFAULT_LIBELLE);
        return sygTypeService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SygTypeService createUpdatedEntity(EntityManager em) {
        SygTypeService sygTypeService = new SygTypeService().libelle(UPDATED_LIBELLE);
        return sygTypeService;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(SygTypeService.class).block();
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
        sygTypeService = createEntity(em);
    }

    @Test
    void createSygTypeService() throws Exception {
        int databaseSizeBeforeCreate = sygTypeServiceRepository.findAll().collectList().block().size();
        // Create the SygTypeService
        SygTypeServiceDTO sygTypeServiceDTO = sygTypeServiceMapper.toDto(sygTypeService);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeServiceDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the SygTypeService in the database
        List<SygTypeService> sygTypeServiceList = sygTypeServiceRepository.findAll().collectList().block();
        assertThat(sygTypeServiceList).hasSize(databaseSizeBeforeCreate + 1);
        SygTypeService testSygTypeService = sygTypeServiceList.get(sygTypeServiceList.size() - 1);
        assertThat(testSygTypeService.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    void createSygTypeServiceWithExistingId() throws Exception {
        // Create the SygTypeService with an existing ID
        sygTypeService.setId(1L);
        SygTypeServiceDTO sygTypeServiceDTO = sygTypeServiceMapper.toDto(sygTypeService);

        int databaseSizeBeforeCreate = sygTypeServiceRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeServiceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygTypeService in the database
        List<SygTypeService> sygTypeServiceList = sygTypeServiceRepository.findAll().collectList().block();
        assertThat(sygTypeServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = sygTypeServiceRepository.findAll().collectList().block().size();
        // set the field null
        sygTypeService.setLibelle(null);

        // Create the SygTypeService, which fails.
        SygTypeServiceDTO sygTypeServiceDTO = sygTypeServiceMapper.toDto(sygTypeService);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeServiceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<SygTypeService> sygTypeServiceList = sygTypeServiceRepository.findAll().collectList().block();
        assertThat(sygTypeServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllSygTypeServices() {
        // Initialize the database
        sygTypeServiceRepository.save(sygTypeService).block();

        // Get all the sygTypeServiceList
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
            .value(hasItem(sygTypeService.getId().intValue()))
            .jsonPath("$.[*].libelle")
            .value(hasItem(DEFAULT_LIBELLE));
    }

    @Test
    void getSygTypeService() {
        // Initialize the database
        sygTypeServiceRepository.save(sygTypeService).block();

        // Get the sygTypeService
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, sygTypeService.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(sygTypeService.getId().intValue()))
            .jsonPath("$.libelle")
            .value(is(DEFAULT_LIBELLE));
    }

    @Test
    void getNonExistingSygTypeService() {
        // Get the sygTypeService
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingSygTypeService() throws Exception {
        // Initialize the database
        sygTypeServiceRepository.save(sygTypeService).block();

        int databaseSizeBeforeUpdate = sygTypeServiceRepository.findAll().collectList().block().size();

        // Update the sygTypeService
        SygTypeService updatedSygTypeService = sygTypeServiceRepository.findById(sygTypeService.getId()).block();
        updatedSygTypeService.libelle(UPDATED_LIBELLE);
        SygTypeServiceDTO sygTypeServiceDTO = sygTypeServiceMapper.toDto(updatedSygTypeService);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, sygTypeServiceDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeServiceDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SygTypeService in the database
        List<SygTypeService> sygTypeServiceList = sygTypeServiceRepository.findAll().collectList().block();
        assertThat(sygTypeServiceList).hasSize(databaseSizeBeforeUpdate);
        SygTypeService testSygTypeService = sygTypeServiceList.get(sygTypeServiceList.size() - 1);
        assertThat(testSygTypeService.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    void putNonExistingSygTypeService() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeServiceRepository.findAll().collectList().block().size();
        sygTypeService.setId(count.incrementAndGet());

        // Create the SygTypeService
        SygTypeServiceDTO sygTypeServiceDTO = sygTypeServiceMapper.toDto(sygTypeService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, sygTypeServiceDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeServiceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygTypeService in the database
        List<SygTypeService> sygTypeServiceList = sygTypeServiceRepository.findAll().collectList().block();
        assertThat(sygTypeServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSygTypeService() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeServiceRepository.findAll().collectList().block().size();
        sygTypeService.setId(count.incrementAndGet());

        // Create the SygTypeService
        SygTypeServiceDTO sygTypeServiceDTO = sygTypeServiceMapper.toDto(sygTypeService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeServiceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygTypeService in the database
        List<SygTypeService> sygTypeServiceList = sygTypeServiceRepository.findAll().collectList().block();
        assertThat(sygTypeServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSygTypeService() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeServiceRepository.findAll().collectList().block().size();
        sygTypeService.setId(count.incrementAndGet());

        // Create the SygTypeService
        SygTypeServiceDTO sygTypeServiceDTO = sygTypeServiceMapper.toDto(sygTypeService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeServiceDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the SygTypeService in the database
        List<SygTypeService> sygTypeServiceList = sygTypeServiceRepository.findAll().collectList().block();
        assertThat(sygTypeServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSygTypeServiceWithPatch() throws Exception {
        // Initialize the database
        sygTypeServiceRepository.save(sygTypeService).block();

        int databaseSizeBeforeUpdate = sygTypeServiceRepository.findAll().collectList().block().size();

        // Update the sygTypeService using partial update
        SygTypeService partialUpdatedSygTypeService = new SygTypeService();
        partialUpdatedSygTypeService.setId(sygTypeService.getId());

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSygTypeService.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSygTypeService))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SygTypeService in the database
        List<SygTypeService> sygTypeServiceList = sygTypeServiceRepository.findAll().collectList().block();
        assertThat(sygTypeServiceList).hasSize(databaseSizeBeforeUpdate);
        SygTypeService testSygTypeService = sygTypeServiceList.get(sygTypeServiceList.size() - 1);
        assertThat(testSygTypeService.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    void fullUpdateSygTypeServiceWithPatch() throws Exception {
        // Initialize the database
        sygTypeServiceRepository.save(sygTypeService).block();

        int databaseSizeBeforeUpdate = sygTypeServiceRepository.findAll().collectList().block().size();

        // Update the sygTypeService using partial update
        SygTypeService partialUpdatedSygTypeService = new SygTypeService();
        partialUpdatedSygTypeService.setId(sygTypeService.getId());

        partialUpdatedSygTypeService.libelle(UPDATED_LIBELLE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSygTypeService.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSygTypeService))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SygTypeService in the database
        List<SygTypeService> sygTypeServiceList = sygTypeServiceRepository.findAll().collectList().block();
        assertThat(sygTypeServiceList).hasSize(databaseSizeBeforeUpdate);
        SygTypeService testSygTypeService = sygTypeServiceList.get(sygTypeServiceList.size() - 1);
        assertThat(testSygTypeService.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    void patchNonExistingSygTypeService() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeServiceRepository.findAll().collectList().block().size();
        sygTypeService.setId(count.incrementAndGet());

        // Create the SygTypeService
        SygTypeServiceDTO sygTypeServiceDTO = sygTypeServiceMapper.toDto(sygTypeService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, sygTypeServiceDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeServiceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygTypeService in the database
        List<SygTypeService> sygTypeServiceList = sygTypeServiceRepository.findAll().collectList().block();
        assertThat(sygTypeServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSygTypeService() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeServiceRepository.findAll().collectList().block().size();
        sygTypeService.setId(count.incrementAndGet());

        // Create the SygTypeService
        SygTypeServiceDTO sygTypeServiceDTO = sygTypeServiceMapper.toDto(sygTypeService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeServiceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygTypeService in the database
        List<SygTypeService> sygTypeServiceList = sygTypeServiceRepository.findAll().collectList().block();
        assertThat(sygTypeServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSygTypeService() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeServiceRepository.findAll().collectList().block().size();
        sygTypeService.setId(count.incrementAndGet());

        // Create the SygTypeService
        SygTypeServiceDTO sygTypeServiceDTO = sygTypeServiceMapper.toDto(sygTypeService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeServiceDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the SygTypeService in the database
        List<SygTypeService> sygTypeServiceList = sygTypeServiceRepository.findAll().collectList().block();
        assertThat(sygTypeServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSygTypeService() {
        // Initialize the database
        sygTypeServiceRepository.save(sygTypeService).block();

        int databaseSizeBeforeDelete = sygTypeServiceRepository.findAll().collectList().block().size();

        // Delete the sygTypeService
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, sygTypeService.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<SygTypeService> sygTypeServiceList = sygTypeServiceRepository.findAll().collectList().block();
        assertThat(sygTypeServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
