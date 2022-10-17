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
import sn.ssi.sigmap.domain.SygTypeMarche;
import sn.ssi.sigmap.repository.EntityManager;
import sn.ssi.sigmap.repository.SygTypeMarcheRepository;
import sn.ssi.sigmap.service.dto.SygTypeMarcheDTO;
import sn.ssi.sigmap.service.mapper.SygTypeMarcheMapper;

/**
 * Integration tests for the {@link SygTypeMarcheResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class SygTypeMarcheResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/syg-type-marches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SygTypeMarcheRepository sygTypeMarcheRepository;

    @Autowired
    private SygTypeMarcheMapper sygTypeMarcheMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private SygTypeMarche sygTypeMarche;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SygTypeMarche createEntity(EntityManager em) {
        SygTypeMarche sygTypeMarche = new SygTypeMarche().code(DEFAULT_CODE).libelle(DEFAULT_LIBELLE).description(DEFAULT_DESCRIPTION);
        return sygTypeMarche;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SygTypeMarche createUpdatedEntity(EntityManager em) {
        SygTypeMarche sygTypeMarche = new SygTypeMarche().code(UPDATED_CODE).libelle(UPDATED_LIBELLE).description(UPDATED_DESCRIPTION);
        return sygTypeMarche;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(SygTypeMarche.class).block();
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
        sygTypeMarche = createEntity(em);
    }

    @Test
    void createSygTypeMarche() throws Exception {
        int databaseSizeBeforeCreate = sygTypeMarcheRepository.findAll().collectList().block().size();
        // Create the SygTypeMarche
        SygTypeMarcheDTO sygTypeMarcheDTO = sygTypeMarcheMapper.toDto(sygTypeMarche);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeMarcheDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the SygTypeMarche in the database
        List<SygTypeMarche> sygTypeMarcheList = sygTypeMarcheRepository.findAll().collectList().block();
        assertThat(sygTypeMarcheList).hasSize(databaseSizeBeforeCreate + 1);
        SygTypeMarche testSygTypeMarche = sygTypeMarcheList.get(sygTypeMarcheList.size() - 1);
        assertThat(testSygTypeMarche.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSygTypeMarche.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testSygTypeMarche.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createSygTypeMarcheWithExistingId() throws Exception {
        // Create the SygTypeMarche with an existing ID
        sygTypeMarche.setId(1L);
        SygTypeMarcheDTO sygTypeMarcheDTO = sygTypeMarcheMapper.toDto(sygTypeMarche);

        int databaseSizeBeforeCreate = sygTypeMarcheRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeMarcheDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygTypeMarche in the database
        List<SygTypeMarche> sygTypeMarcheList = sygTypeMarcheRepository.findAll().collectList().block();
        assertThat(sygTypeMarcheList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sygTypeMarcheRepository.findAll().collectList().block().size();
        // set the field null
        sygTypeMarche.setCode(null);

        // Create the SygTypeMarche, which fails.
        SygTypeMarcheDTO sygTypeMarcheDTO = sygTypeMarcheMapper.toDto(sygTypeMarche);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeMarcheDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<SygTypeMarche> sygTypeMarcheList = sygTypeMarcheRepository.findAll().collectList().block();
        assertThat(sygTypeMarcheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = sygTypeMarcheRepository.findAll().collectList().block().size();
        // set the field null
        sygTypeMarche.setLibelle(null);

        // Create the SygTypeMarche, which fails.
        SygTypeMarcheDTO sygTypeMarcheDTO = sygTypeMarcheMapper.toDto(sygTypeMarche);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeMarcheDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<SygTypeMarche> sygTypeMarcheList = sygTypeMarcheRepository.findAll().collectList().block();
        assertThat(sygTypeMarcheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllSygTypeMarches() {
        // Initialize the database
        sygTypeMarcheRepository.save(sygTypeMarche).block();

        // Get all the sygTypeMarcheList
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
            .value(hasItem(sygTypeMarche.getId().intValue()))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].libelle")
            .value(hasItem(DEFAULT_LIBELLE))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION));
    }

    @Test
    void getSygTypeMarche() {
        // Initialize the database
        sygTypeMarcheRepository.save(sygTypeMarche).block();

        // Get the sygTypeMarche
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, sygTypeMarche.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(sygTypeMarche.getId().intValue()))
            .jsonPath("$.code")
            .value(is(DEFAULT_CODE))
            .jsonPath("$.libelle")
            .value(is(DEFAULT_LIBELLE))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingSygTypeMarche() {
        // Get the sygTypeMarche
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingSygTypeMarche() throws Exception {
        // Initialize the database
        sygTypeMarcheRepository.save(sygTypeMarche).block();

        int databaseSizeBeforeUpdate = sygTypeMarcheRepository.findAll().collectList().block().size();

        // Update the sygTypeMarche
        SygTypeMarche updatedSygTypeMarche = sygTypeMarcheRepository.findById(sygTypeMarche.getId()).block();
        updatedSygTypeMarche.code(UPDATED_CODE).libelle(UPDATED_LIBELLE).description(UPDATED_DESCRIPTION);
        SygTypeMarcheDTO sygTypeMarcheDTO = sygTypeMarcheMapper.toDto(updatedSygTypeMarche);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, sygTypeMarcheDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeMarcheDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SygTypeMarche in the database
        List<SygTypeMarche> sygTypeMarcheList = sygTypeMarcheRepository.findAll().collectList().block();
        assertThat(sygTypeMarcheList).hasSize(databaseSizeBeforeUpdate);
        SygTypeMarche testSygTypeMarche = sygTypeMarcheList.get(sygTypeMarcheList.size() - 1);
        assertThat(testSygTypeMarche.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSygTypeMarche.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testSygTypeMarche.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingSygTypeMarche() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeMarcheRepository.findAll().collectList().block().size();
        sygTypeMarche.setId(count.incrementAndGet());

        // Create the SygTypeMarche
        SygTypeMarcheDTO sygTypeMarcheDTO = sygTypeMarcheMapper.toDto(sygTypeMarche);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, sygTypeMarcheDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeMarcheDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygTypeMarche in the database
        List<SygTypeMarche> sygTypeMarcheList = sygTypeMarcheRepository.findAll().collectList().block();
        assertThat(sygTypeMarcheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSygTypeMarche() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeMarcheRepository.findAll().collectList().block().size();
        sygTypeMarche.setId(count.incrementAndGet());

        // Create the SygTypeMarche
        SygTypeMarcheDTO sygTypeMarcheDTO = sygTypeMarcheMapper.toDto(sygTypeMarche);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeMarcheDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygTypeMarche in the database
        List<SygTypeMarche> sygTypeMarcheList = sygTypeMarcheRepository.findAll().collectList().block();
        assertThat(sygTypeMarcheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSygTypeMarche() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeMarcheRepository.findAll().collectList().block().size();
        sygTypeMarche.setId(count.incrementAndGet());

        // Create the SygTypeMarche
        SygTypeMarcheDTO sygTypeMarcheDTO = sygTypeMarcheMapper.toDto(sygTypeMarche);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeMarcheDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the SygTypeMarche in the database
        List<SygTypeMarche> sygTypeMarcheList = sygTypeMarcheRepository.findAll().collectList().block();
        assertThat(sygTypeMarcheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSygTypeMarcheWithPatch() throws Exception {
        // Initialize the database
        sygTypeMarcheRepository.save(sygTypeMarche).block();

        int databaseSizeBeforeUpdate = sygTypeMarcheRepository.findAll().collectList().block().size();

        // Update the sygTypeMarche using partial update
        SygTypeMarche partialUpdatedSygTypeMarche = new SygTypeMarche();
        partialUpdatedSygTypeMarche.setId(sygTypeMarche.getId());

        partialUpdatedSygTypeMarche.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSygTypeMarche.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSygTypeMarche))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SygTypeMarche in the database
        List<SygTypeMarche> sygTypeMarcheList = sygTypeMarcheRepository.findAll().collectList().block();
        assertThat(sygTypeMarcheList).hasSize(databaseSizeBeforeUpdate);
        SygTypeMarche testSygTypeMarche = sygTypeMarcheList.get(sygTypeMarcheList.size() - 1);
        assertThat(testSygTypeMarche.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSygTypeMarche.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testSygTypeMarche.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void fullUpdateSygTypeMarcheWithPatch() throws Exception {
        // Initialize the database
        sygTypeMarcheRepository.save(sygTypeMarche).block();

        int databaseSizeBeforeUpdate = sygTypeMarcheRepository.findAll().collectList().block().size();

        // Update the sygTypeMarche using partial update
        SygTypeMarche partialUpdatedSygTypeMarche = new SygTypeMarche();
        partialUpdatedSygTypeMarche.setId(sygTypeMarche.getId());

        partialUpdatedSygTypeMarche.code(UPDATED_CODE).libelle(UPDATED_LIBELLE).description(UPDATED_DESCRIPTION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSygTypeMarche.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSygTypeMarche))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SygTypeMarche in the database
        List<SygTypeMarche> sygTypeMarcheList = sygTypeMarcheRepository.findAll().collectList().block();
        assertThat(sygTypeMarcheList).hasSize(databaseSizeBeforeUpdate);
        SygTypeMarche testSygTypeMarche = sygTypeMarcheList.get(sygTypeMarcheList.size() - 1);
        assertThat(testSygTypeMarche.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSygTypeMarche.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testSygTypeMarche.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingSygTypeMarche() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeMarcheRepository.findAll().collectList().block().size();
        sygTypeMarche.setId(count.incrementAndGet());

        // Create the SygTypeMarche
        SygTypeMarcheDTO sygTypeMarcheDTO = sygTypeMarcheMapper.toDto(sygTypeMarche);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, sygTypeMarcheDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeMarcheDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygTypeMarche in the database
        List<SygTypeMarche> sygTypeMarcheList = sygTypeMarcheRepository.findAll().collectList().block();
        assertThat(sygTypeMarcheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSygTypeMarche() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeMarcheRepository.findAll().collectList().block().size();
        sygTypeMarche.setId(count.incrementAndGet());

        // Create the SygTypeMarche
        SygTypeMarcheDTO sygTypeMarcheDTO = sygTypeMarcheMapper.toDto(sygTypeMarche);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeMarcheDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygTypeMarche in the database
        List<SygTypeMarche> sygTypeMarcheList = sygTypeMarcheRepository.findAll().collectList().block();
        assertThat(sygTypeMarcheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSygTypeMarche() throws Exception {
        int databaseSizeBeforeUpdate = sygTypeMarcheRepository.findAll().collectList().block().size();
        sygTypeMarche.setId(count.incrementAndGet());

        // Create the SygTypeMarche
        SygTypeMarcheDTO sygTypeMarcheDTO = sygTypeMarcheMapper.toDto(sygTypeMarche);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygTypeMarcheDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the SygTypeMarche in the database
        List<SygTypeMarche> sygTypeMarcheList = sygTypeMarcheRepository.findAll().collectList().block();
        assertThat(sygTypeMarcheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSygTypeMarche() {
        // Initialize the database
        sygTypeMarcheRepository.save(sygTypeMarche).block();

        int databaseSizeBeforeDelete = sygTypeMarcheRepository.findAll().collectList().block().size();

        // Delete the sygTypeMarche
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, sygTypeMarche.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<SygTypeMarche> sygTypeMarcheList = sygTypeMarcheRepository.findAll().collectList().block();
        assertThat(sygTypeMarcheList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
