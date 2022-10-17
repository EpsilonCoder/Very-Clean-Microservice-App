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
import sn.ssi.sigmap.domain.ModePassation;
import sn.ssi.sigmap.repository.EntityManager;
import sn.ssi.sigmap.repository.ModePassationRepository;

/**
 * Integration tests for the {@link ModePassationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ModePassationResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mode-passations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ModePassationRepository modePassationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private ModePassation modePassation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModePassation createEntity(EntityManager em) {
        ModePassation modePassation = new ModePassation().libelle(DEFAULT_LIBELLE).code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return modePassation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModePassation createUpdatedEntity(EntityManager em) {
        ModePassation modePassation = new ModePassation().libelle(UPDATED_LIBELLE).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return modePassation;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(ModePassation.class).block();
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
        modePassation = createEntity(em);
    }

    @Test
    void createModePassation() throws Exception {
        int databaseSizeBeforeCreate = modePassationRepository.findAll().collectList().block().size();
        // Create the ModePassation
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(modePassation))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the ModePassation in the database
        List<ModePassation> modePassationList = modePassationRepository.findAll().collectList().block();
        assertThat(modePassationList).hasSize(databaseSizeBeforeCreate + 1);
        ModePassation testModePassation = modePassationList.get(modePassationList.size() - 1);
        assertThat(testModePassation.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testModePassation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testModePassation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createModePassationWithExistingId() throws Exception {
        // Create the ModePassation with an existing ID
        modePassation.setId(1L);

        int databaseSizeBeforeCreate = modePassationRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(modePassation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ModePassation in the database
        List<ModePassation> modePassationList = modePassationRepository.findAll().collectList().block();
        assertThat(modePassationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = modePassationRepository.findAll().collectList().block().size();
        // set the field null
        modePassation.setLibelle(null);

        // Create the ModePassation, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(modePassation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<ModePassation> modePassationList = modePassationRepository.findAll().collectList().block();
        assertThat(modePassationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = modePassationRepository.findAll().collectList().block().size();
        // set the field null
        modePassation.setCode(null);

        // Create the ModePassation, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(modePassation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<ModePassation> modePassationList = modePassationRepository.findAll().collectList().block();
        assertThat(modePassationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = modePassationRepository.findAll().collectList().block().size();
        // set the field null
        modePassation.setDescription(null);

        // Create the ModePassation, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(modePassation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<ModePassation> modePassationList = modePassationRepository.findAll().collectList().block();
        assertThat(modePassationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllModePassationsAsStream() {
        // Initialize the database
        modePassationRepository.save(modePassation).block();

        List<ModePassation> modePassationList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(ModePassation.class)
            .getResponseBody()
            .filter(modePassation::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(modePassationList).isNotNull();
        assertThat(modePassationList).hasSize(1);
        ModePassation testModePassation = modePassationList.get(0);
        assertThat(testModePassation.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testModePassation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testModePassation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void getAllModePassations() {
        // Initialize the database
        modePassationRepository.save(modePassation).block();

        // Get all the modePassationList
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
            .value(hasItem(modePassation.getId().intValue()))
            .jsonPath("$.[*].libelle")
            .value(hasItem(DEFAULT_LIBELLE))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION));
    }

    @Test
    void getModePassation() {
        // Initialize the database
        modePassationRepository.save(modePassation).block();

        // Get the modePassation
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, modePassation.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(modePassation.getId().intValue()))
            .jsonPath("$.libelle")
            .value(is(DEFAULT_LIBELLE))
            .jsonPath("$.code")
            .value(is(DEFAULT_CODE))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingModePassation() {
        // Get the modePassation
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingModePassation() throws Exception {
        // Initialize the database
        modePassationRepository.save(modePassation).block();

        int databaseSizeBeforeUpdate = modePassationRepository.findAll().collectList().block().size();

        // Update the modePassation
        ModePassation updatedModePassation = modePassationRepository.findById(modePassation.getId()).block();
        updatedModePassation.libelle(UPDATED_LIBELLE).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedModePassation.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedModePassation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ModePassation in the database
        List<ModePassation> modePassationList = modePassationRepository.findAll().collectList().block();
        assertThat(modePassationList).hasSize(databaseSizeBeforeUpdate);
        ModePassation testModePassation = modePassationList.get(modePassationList.size() - 1);
        assertThat(testModePassation.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testModePassation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testModePassation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingModePassation() throws Exception {
        int databaseSizeBeforeUpdate = modePassationRepository.findAll().collectList().block().size();
        modePassation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, modePassation.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(modePassation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ModePassation in the database
        List<ModePassation> modePassationList = modePassationRepository.findAll().collectList().block();
        assertThat(modePassationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchModePassation() throws Exception {
        int databaseSizeBeforeUpdate = modePassationRepository.findAll().collectList().block().size();
        modePassation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(modePassation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ModePassation in the database
        List<ModePassation> modePassationList = modePassationRepository.findAll().collectList().block();
        assertThat(modePassationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamModePassation() throws Exception {
        int databaseSizeBeforeUpdate = modePassationRepository.findAll().collectList().block().size();
        modePassation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(modePassation))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ModePassation in the database
        List<ModePassation> modePassationList = modePassationRepository.findAll().collectList().block();
        assertThat(modePassationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateModePassationWithPatch() throws Exception {
        // Initialize the database
        modePassationRepository.save(modePassation).block();

        int databaseSizeBeforeUpdate = modePassationRepository.findAll().collectList().block().size();

        // Update the modePassation using partial update
        ModePassation partialUpdatedModePassation = new ModePassation();
        partialUpdatedModePassation.setId(modePassation.getId());

        partialUpdatedModePassation.libelle(UPDATED_LIBELLE).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedModePassation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedModePassation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ModePassation in the database
        List<ModePassation> modePassationList = modePassationRepository.findAll().collectList().block();
        assertThat(modePassationList).hasSize(databaseSizeBeforeUpdate);
        ModePassation testModePassation = modePassationList.get(modePassationList.size() - 1);
        assertThat(testModePassation.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testModePassation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testModePassation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void fullUpdateModePassationWithPatch() throws Exception {
        // Initialize the database
        modePassationRepository.save(modePassation).block();

        int databaseSizeBeforeUpdate = modePassationRepository.findAll().collectList().block().size();

        // Update the modePassation using partial update
        ModePassation partialUpdatedModePassation = new ModePassation();
        partialUpdatedModePassation.setId(modePassation.getId());

        partialUpdatedModePassation.libelle(UPDATED_LIBELLE).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedModePassation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedModePassation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ModePassation in the database
        List<ModePassation> modePassationList = modePassationRepository.findAll().collectList().block();
        assertThat(modePassationList).hasSize(databaseSizeBeforeUpdate);
        ModePassation testModePassation = modePassationList.get(modePassationList.size() - 1);
        assertThat(testModePassation.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testModePassation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testModePassation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingModePassation() throws Exception {
        int databaseSizeBeforeUpdate = modePassationRepository.findAll().collectList().block().size();
        modePassation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, modePassation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(modePassation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ModePassation in the database
        List<ModePassation> modePassationList = modePassationRepository.findAll().collectList().block();
        assertThat(modePassationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchModePassation() throws Exception {
        int databaseSizeBeforeUpdate = modePassationRepository.findAll().collectList().block().size();
        modePassation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(modePassation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ModePassation in the database
        List<ModePassation> modePassationList = modePassationRepository.findAll().collectList().block();
        assertThat(modePassationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamModePassation() throws Exception {
        int databaseSizeBeforeUpdate = modePassationRepository.findAll().collectList().block().size();
        modePassation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(modePassation))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ModePassation in the database
        List<ModePassation> modePassationList = modePassationRepository.findAll().collectList().block();
        assertThat(modePassationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteModePassation() {
        // Initialize the database
        modePassationRepository.save(modePassation).block();

        int databaseSizeBeforeDelete = modePassationRepository.findAll().collectList().block().size();

        // Delete the modePassation
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, modePassation.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<ModePassation> modePassationList = modePassationRepository.findAll().collectList().block();
        assertThat(modePassationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
