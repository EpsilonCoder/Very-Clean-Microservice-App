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
import sn.ssi.sigmap.domain.SygService;
import sn.ssi.sigmap.domain.SygTypeService;
import sn.ssi.sigmap.repository.EntityManager;
import sn.ssi.sigmap.repository.SygServiceRepository;
import sn.ssi.sigmap.service.SygServiceService;
import sn.ssi.sigmap.service.dto.SygServiceDTO;
import sn.ssi.sigmap.service.mapper.SygServiceMapper;

/**
 * Integration tests for the {@link SygServiceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class SygServiceResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/syg-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SygServiceRepository sygServiceRepository;

    @Mock
    private SygServiceRepository sygServiceRepositoryMock;

    @Autowired
    private SygServiceMapper sygServiceMapper;

    @Mock
    private SygServiceService sygServiceServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private SygService sygService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SygService createEntity(EntityManager em) {
        SygService sygService = new SygService().code(DEFAULT_CODE).libelle(DEFAULT_LIBELLE).description(DEFAULT_DESCRIPTION);
        // Add required entity
        SygTypeService sygTypeService;
        sygTypeService = em.insert(SygTypeServiceResourceIT.createEntity(em)).block();
        sygService.setSygTypeService(sygTypeService);
        return sygService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SygService createUpdatedEntity(EntityManager em) {
        SygService sygService = new SygService().code(UPDATED_CODE).libelle(UPDATED_LIBELLE).description(UPDATED_DESCRIPTION);
        // Add required entity
        SygTypeService sygTypeService;
        sygTypeService = em.insert(SygTypeServiceResourceIT.createUpdatedEntity(em)).block();
        sygService.setSygTypeService(sygTypeService);
        return sygService;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(SygService.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        SygTypeServiceResourceIT.deleteEntities(em);
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
        sygService = createEntity(em);
    }

    @Test
    void createSygService() throws Exception {
        int databaseSizeBeforeCreate = sygServiceRepository.findAll().collectList().block().size();
        // Create the SygService
        SygServiceDTO sygServiceDTO = sygServiceMapper.toDto(sygService);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygServiceDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the SygService in the database
        List<SygService> sygServiceList = sygServiceRepository.findAll().collectList().block();
        assertThat(sygServiceList).hasSize(databaseSizeBeforeCreate + 1);
        SygService testSygService = sygServiceList.get(sygServiceList.size() - 1);
        assertThat(testSygService.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSygService.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testSygService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createSygServiceWithExistingId() throws Exception {
        // Create the SygService with an existing ID
        sygService.setId(1L);
        SygServiceDTO sygServiceDTO = sygServiceMapper.toDto(sygService);

        int databaseSizeBeforeCreate = sygServiceRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygServiceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygService in the database
        List<SygService> sygServiceList = sygServiceRepository.findAll().collectList().block();
        assertThat(sygServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sygServiceRepository.findAll().collectList().block().size();
        // set the field null
        sygService.setCode(null);

        // Create the SygService, which fails.
        SygServiceDTO sygServiceDTO = sygServiceMapper.toDto(sygService);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygServiceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<SygService> sygServiceList = sygServiceRepository.findAll().collectList().block();
        assertThat(sygServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = sygServiceRepository.findAll().collectList().block().size();
        // set the field null
        sygService.setLibelle(null);

        // Create the SygService, which fails.
        SygServiceDTO sygServiceDTO = sygServiceMapper.toDto(sygService);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygServiceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<SygService> sygServiceList = sygServiceRepository.findAll().collectList().block();
        assertThat(sygServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllSygServices() {
        // Initialize the database
        sygServiceRepository.save(sygService).block();

        // Get all the sygServiceList
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
            .value(hasItem(sygService.getId().intValue()))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].libelle")
            .value(hasItem(DEFAULT_LIBELLE))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSygServicesWithEagerRelationshipsIsEnabled() {
        when(sygServiceServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(sygServiceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSygServicesWithEagerRelationshipsIsNotEnabled() {
        when(sygServiceServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(sygServiceRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getSygService() {
        // Initialize the database
        sygServiceRepository.save(sygService).block();

        // Get the sygService
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, sygService.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(sygService.getId().intValue()))
            .jsonPath("$.code")
            .value(is(DEFAULT_CODE))
            .jsonPath("$.libelle")
            .value(is(DEFAULT_LIBELLE))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingSygService() {
        // Get the sygService
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingSygService() throws Exception {
        // Initialize the database
        sygServiceRepository.save(sygService).block();

        int databaseSizeBeforeUpdate = sygServiceRepository.findAll().collectList().block().size();

        // Update the sygService
        SygService updatedSygService = sygServiceRepository.findById(sygService.getId()).block();
        updatedSygService.code(UPDATED_CODE).libelle(UPDATED_LIBELLE).description(UPDATED_DESCRIPTION);
        SygServiceDTO sygServiceDTO = sygServiceMapper.toDto(updatedSygService);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, sygServiceDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygServiceDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SygService in the database
        List<SygService> sygServiceList = sygServiceRepository.findAll().collectList().block();
        assertThat(sygServiceList).hasSize(databaseSizeBeforeUpdate);
        SygService testSygService = sygServiceList.get(sygServiceList.size() - 1);
        assertThat(testSygService.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSygService.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testSygService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingSygService() throws Exception {
        int databaseSizeBeforeUpdate = sygServiceRepository.findAll().collectList().block().size();
        sygService.setId(count.incrementAndGet());

        // Create the SygService
        SygServiceDTO sygServiceDTO = sygServiceMapper.toDto(sygService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, sygServiceDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygServiceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygService in the database
        List<SygService> sygServiceList = sygServiceRepository.findAll().collectList().block();
        assertThat(sygServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSygService() throws Exception {
        int databaseSizeBeforeUpdate = sygServiceRepository.findAll().collectList().block().size();
        sygService.setId(count.incrementAndGet());

        // Create the SygService
        SygServiceDTO sygServiceDTO = sygServiceMapper.toDto(sygService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygServiceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygService in the database
        List<SygService> sygServiceList = sygServiceRepository.findAll().collectList().block();
        assertThat(sygServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSygService() throws Exception {
        int databaseSizeBeforeUpdate = sygServiceRepository.findAll().collectList().block().size();
        sygService.setId(count.incrementAndGet());

        // Create the SygService
        SygServiceDTO sygServiceDTO = sygServiceMapper.toDto(sygService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygServiceDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the SygService in the database
        List<SygService> sygServiceList = sygServiceRepository.findAll().collectList().block();
        assertThat(sygServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSygServiceWithPatch() throws Exception {
        // Initialize the database
        sygServiceRepository.save(sygService).block();

        int databaseSizeBeforeUpdate = sygServiceRepository.findAll().collectList().block().size();

        // Update the sygService using partial update
        SygService partialUpdatedSygService = new SygService();
        partialUpdatedSygService.setId(sygService.getId());

        partialUpdatedSygService.libelle(UPDATED_LIBELLE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSygService.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSygService))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SygService in the database
        List<SygService> sygServiceList = sygServiceRepository.findAll().collectList().block();
        assertThat(sygServiceList).hasSize(databaseSizeBeforeUpdate);
        SygService testSygService = sygServiceList.get(sygServiceList.size() - 1);
        assertThat(testSygService.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSygService.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testSygService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void fullUpdateSygServiceWithPatch() throws Exception {
        // Initialize the database
        sygServiceRepository.save(sygService).block();

        int databaseSizeBeforeUpdate = sygServiceRepository.findAll().collectList().block().size();

        // Update the sygService using partial update
        SygService partialUpdatedSygService = new SygService();
        partialUpdatedSygService.setId(sygService.getId());

        partialUpdatedSygService.code(UPDATED_CODE).libelle(UPDATED_LIBELLE).description(UPDATED_DESCRIPTION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSygService.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSygService))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SygService in the database
        List<SygService> sygServiceList = sygServiceRepository.findAll().collectList().block();
        assertThat(sygServiceList).hasSize(databaseSizeBeforeUpdate);
        SygService testSygService = sygServiceList.get(sygServiceList.size() - 1);
        assertThat(testSygService.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSygService.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testSygService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingSygService() throws Exception {
        int databaseSizeBeforeUpdate = sygServiceRepository.findAll().collectList().block().size();
        sygService.setId(count.incrementAndGet());

        // Create the SygService
        SygServiceDTO sygServiceDTO = sygServiceMapper.toDto(sygService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, sygServiceDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygServiceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygService in the database
        List<SygService> sygServiceList = sygServiceRepository.findAll().collectList().block();
        assertThat(sygServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSygService() throws Exception {
        int databaseSizeBeforeUpdate = sygServiceRepository.findAll().collectList().block().size();
        sygService.setId(count.incrementAndGet());

        // Create the SygService
        SygServiceDTO sygServiceDTO = sygServiceMapper.toDto(sygService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygServiceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SygService in the database
        List<SygService> sygServiceList = sygServiceRepository.findAll().collectList().block();
        assertThat(sygServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSygService() throws Exception {
        int databaseSizeBeforeUpdate = sygServiceRepository.findAll().collectList().block().size();
        sygService.setId(count.incrementAndGet());

        // Create the SygService
        SygServiceDTO sygServiceDTO = sygServiceMapper.toDto(sygService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sygServiceDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the SygService in the database
        List<SygService> sygServiceList = sygServiceRepository.findAll().collectList().block();
        assertThat(sygServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSygService() {
        // Initialize the database
        sygServiceRepository.save(sygService).block();

        int databaseSizeBeforeDelete = sygServiceRepository.findAll().collectList().block().size();

        // Delete the sygService
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, sygService.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<SygService> sygServiceList = sygServiceRepository.findAll().collectList().block();
        assertThat(sygServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
