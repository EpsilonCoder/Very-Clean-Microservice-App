package sn.ssi.sigmap.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.IntegrationTest;
import sn.ssi.sigmap.domain.SygSourceFinancement;
import sn.ssi.sigmap.domain.SygTypeSourceFinancement;
import sn.ssi.sigmap.repository.EntityManager;
import sn.ssi.sigmap.repository.SygSourceFinancementRepository;
import sn.ssi.sigmap.service.SygSourceFinancementService;
import sn.ssi.sigmap.service.dto.SygSourceFinancementDTO;
import sn.ssi.sigmap.service.mapper.SygSourceFinancementMapper;

/**
 * Integration tests for the {@link SygSourceFinancementResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class SygSourceFinancementResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/syg-source-financements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SygSourceFinancementRepository sygSourceFinancementRepository;

    @Mock
    private SygSourceFinancementRepository sygSourceFinancementRepositoryMock;

    @Autowired
    private SygSourceFinancementMapper sygSourceFinancementMapper;

    @Mock
    private SygSourceFinancementService sygSourceFinancementServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private SygSourceFinancement sygSourceFinancement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SygSourceFinancement createEntity(EntityManager em) {
        SygSourceFinancement sygSourceFinancement = new SygSourceFinancement().libelle(DEFAULT_LIBELLE);
        // Add required entity
        SygTypeSourceFinancement sygTypeSourceFinancement;
        sygTypeSourceFinancement = em.insert(SygTypeSourceFinancementResourceIT.createEntity(em)).block();
        sygSourceFinancement.setSygTypeSourceFinancement(sygTypeSourceFinancement);
        return sygSourceFinancement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SygSourceFinancement createUpdatedEntity(EntityManager em) {
        SygSourceFinancement sygSourceFinancement = new SygSourceFinancement().libelle(UPDATED_LIBELLE);
        // Add required entity
        SygTypeSourceFinancement sygTypeSourceFinancement;
        sygTypeSourceFinancement = em.insert(SygTypeSourceFinancementResourceIT.createUpdatedEntity(em)).block();
        sygSourceFinancement.setSygTypeSourceFinancement(sygTypeSourceFinancement);
        return sygSourceFinancement;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(SygSourceFinancement.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        SygTypeSourceFinancementResourceIT.deleteEntities(em);
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
        sygSourceFinancement = createEntity(em);
    }

    @Test
    void createSygSourceFinancement() throws Exception {
        int databaseSizeBeforeCreate = sygSourceFinancementRepository.findAll().collectList().block().size();
        // Create the SygSourceFinancement
        SygSourceFinancementDTO sygSourceFinancementDTO = sygSourceFinancementMapper.toDto(sygSourceFinancement);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the SygSourceFinancement in the database
        List<SygSourceFinancement> sygSourceFinancementList = sygSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygSourceFinancementList).hasSize(databaseSizeBeforeCreate + 1);
        SygSourceFinancement testSygSourceFinancement = sygSourceFinancementList.get(sygSourceFinancementList.size() - 1);
        assertThat(testSygSourceFinancement.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    void createSygSourceFinancementWithExistingId() throws Exception {
        // Create the SygSourceFinancement with an existing ID
        sygSourceFinancement.setId(1L);
        SygSourceFinancementDTO sygSourceFinancementDTO = sygSourceFinancementMapper.toDto(sygSourceFinancement);

        int databaseSizeBeforeCreate = sygSourceFinancementRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygSourceFinancement in the database
        List<SygSourceFinancement> sygSourceFinancementList = sygSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygSourceFinancementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = sygSourceFinancementRepository.findAll().collectList().block().size();
        // set the field null
        sygSourceFinancement.setLibelle(null);

        // Create the SygSourceFinancement, which fails.
        SygSourceFinancementDTO sygSourceFinancementDTO = sygSourceFinancementMapper.toDto(sygSourceFinancement);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<SygSourceFinancement> sygSourceFinancementList = sygSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygSourceFinancementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllSygSourceFinancements() {
        // Initialize the database
        sygSourceFinancementRepository.save(sygSourceFinancement).block();

        // Get all the sygSourceFinancementList
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
            .value(hasItem(sygSourceFinancement.getId().intValue()))
            .jsonPath("$.[*].libelle")
            .value(hasItem(DEFAULT_LIBELLE));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSygSourceFinancementsWithEagerRelationshipsIsEnabled() {
        when(sygSourceFinancementServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(sygSourceFinancementServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSygSourceFinancementsWithEagerRelationshipsIsNotEnabled() {
        when(sygSourceFinancementServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(sygSourceFinancementRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getSygSourceFinancement() {
        // Initialize the database
        sygSourceFinancementRepository.save(sygSourceFinancement).block();

        // Get the sygSourceFinancement
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, sygSourceFinancement.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(sygSourceFinancement.getId().intValue()))
            .jsonPath("$.libelle")
            .value(is(DEFAULT_LIBELLE));
    }

    @Test
    void getNonExistingSygSourceFinancement() {
        // Get the sygSourceFinancement
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingSygSourceFinancement() throws Exception {
        // Initialize the database
        sygSourceFinancementRepository.save(sygSourceFinancement).block();

        int databaseSizeBeforeUpdate = sygSourceFinancementRepository.findAll().collectList().block().size();

        // Update the sygSourceFinancement
        SygSourceFinancement updatedSygSourceFinancement = sygSourceFinancementRepository.findById(sygSourceFinancement.getId()).block();
        updatedSygSourceFinancement.libelle(UPDATED_LIBELLE);
        SygSourceFinancementDTO sygSourceFinancementDTO = sygSourceFinancementMapper.toDto(updatedSygSourceFinancement);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, sygSourceFinancementDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SygSourceFinancement in the database
        List<SygSourceFinancement> sygSourceFinancementList = sygSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
        SygSourceFinancement testSygSourceFinancement = sygSourceFinancementList.get(sygSourceFinancementList.size() - 1);
        assertThat(testSygSourceFinancement.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    void putNonExistingSygSourceFinancement() throws Exception {
        int databaseSizeBeforeUpdate = sygSourceFinancementRepository.findAll().collectList().block().size();
        sygSourceFinancement.setId(count.incrementAndGet());

        // Create the SygSourceFinancement
        SygSourceFinancementDTO sygSourceFinancementDTO = sygSourceFinancementMapper.toDto(sygSourceFinancement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, sygSourceFinancementDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygSourceFinancement in the database
        List<SygSourceFinancement> sygSourceFinancementList = sygSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSygSourceFinancement() throws Exception {
        int databaseSizeBeforeUpdate = sygSourceFinancementRepository.findAll().collectList().block().size();
        sygSourceFinancement.setId(count.incrementAndGet());

        // Create the SygSourceFinancement
        SygSourceFinancementDTO sygSourceFinancementDTO = sygSourceFinancementMapper.toDto(sygSourceFinancement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygSourceFinancement in the database
        List<SygSourceFinancement> sygSourceFinancementList = sygSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSygSourceFinancement() throws Exception {
        int databaseSizeBeforeUpdate = sygSourceFinancementRepository.findAll().collectList().block().size();
        sygSourceFinancement.setId(count.incrementAndGet());

        // Create the SygSourceFinancement
        SygSourceFinancementDTO sygSourceFinancementDTO = sygSourceFinancementMapper.toDto(sygSourceFinancement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the SygSourceFinancement in the database
        List<SygSourceFinancement> sygSourceFinancementList = sygSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSygSourceFinancementWithPatch() throws Exception {
        // Initialize the database
        sygSourceFinancementRepository.save(sygSourceFinancement).block();

        int databaseSizeBeforeUpdate = sygSourceFinancementRepository.findAll().collectList().block().size();

        // Update the sygSourceFinancement using partial update
        SygSourceFinancement partialUpdatedSygSourceFinancement = new SygSourceFinancement();
        partialUpdatedSygSourceFinancement.setId(sygSourceFinancement.getId());

        partialUpdatedSygSourceFinancement.libelle(UPDATED_LIBELLE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSygSourceFinancement.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSygSourceFinancement))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SygSourceFinancement in the database
        List<SygSourceFinancement> sygSourceFinancementList = sygSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
        SygSourceFinancement testSygSourceFinancement = sygSourceFinancementList.get(sygSourceFinancementList.size() - 1);
        assertThat(testSygSourceFinancement.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    void fullUpdateSygSourceFinancementWithPatch() throws Exception {
        // Initialize the database
        sygSourceFinancementRepository.save(sygSourceFinancement).block();

        int databaseSizeBeforeUpdate = sygSourceFinancementRepository.findAll().collectList().block().size();

        // Update the sygSourceFinancement using partial update
        SygSourceFinancement partialUpdatedSygSourceFinancement = new SygSourceFinancement();
        partialUpdatedSygSourceFinancement.setId(sygSourceFinancement.getId());

        partialUpdatedSygSourceFinancement.libelle(UPDATED_LIBELLE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSygSourceFinancement.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSygSourceFinancement))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SygSourceFinancement in the database
        List<SygSourceFinancement> sygSourceFinancementList = sygSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
        SygSourceFinancement testSygSourceFinancement = sygSourceFinancementList.get(sygSourceFinancementList.size() - 1);
        assertThat(testSygSourceFinancement.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    void patchNonExistingSygSourceFinancement() throws Exception {
        int databaseSizeBeforeUpdate = sygSourceFinancementRepository.findAll().collectList().block().size();
        sygSourceFinancement.setId(count.incrementAndGet());

        // Create the SygSourceFinancement
        SygSourceFinancementDTO sygSourceFinancementDTO = sygSourceFinancementMapper.toDto(sygSourceFinancement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, sygSourceFinancementDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygSourceFinancement in the database
        List<SygSourceFinancement> sygSourceFinancementList = sygSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSygSourceFinancement() throws Exception {
        int databaseSizeBeforeUpdate = sygSourceFinancementRepository.findAll().collectList().block().size();
        sygSourceFinancement.setId(count.incrementAndGet());

        // Create the SygSourceFinancement
        SygSourceFinancementDTO sygSourceFinancementDTO = sygSourceFinancementMapper.toDto(sygSourceFinancement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygSourceFinancement in the database
        List<SygSourceFinancement> sygSourceFinancementList = sygSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSygSourceFinancement() throws Exception {
        int databaseSizeBeforeUpdate = sygSourceFinancementRepository.findAll().collectList().block().size();
        sygSourceFinancement.setId(count.incrementAndGet());

        // Create the SygSourceFinancement
        SygSourceFinancementDTO sygSourceFinancementDTO = sygSourceFinancementMapper.toDto(sygSourceFinancement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygSourceFinancementDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the SygSourceFinancement in the database
        List<SygSourceFinancement> sygSourceFinancementList = sygSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygSourceFinancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSygSourceFinancement() {
        // Initialize the database
        sygSourceFinancementRepository.save(sygSourceFinancement).block();

        int databaseSizeBeforeDelete = sygSourceFinancementRepository.findAll().collectList().block().size();

        // Delete the sygSourceFinancement
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, sygSourceFinancement.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<SygSourceFinancement> sygSourceFinancementList = sygSourceFinancementRepository.findAll().collectList().block();
        assertThat(sygSourceFinancementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
