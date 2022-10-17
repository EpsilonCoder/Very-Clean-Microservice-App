package sn.ssi.sigmap.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
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
import sn.ssi.sigmap.domain.ParamDate;
import sn.ssi.sigmap.repository.EntityManager;
import sn.ssi.sigmap.repository.ParamDateRepository;
import sn.ssi.sigmap.service.dto.ParamDateDTO;
import sn.ssi.sigmap.service.mapper.ParamDateMapper;

/**
 * Integration tests for the {@link ParamDateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ParamDateResourceIT {

    private static final LocalDate DEFAULT_DATE_CREAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREAT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/param-dates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParamDateRepository paramDateRepository;

    @Autowired
    private ParamDateMapper paramDateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private ParamDate paramDate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParamDate createEntity(EntityManager em) {
        ParamDate paramDate = new ParamDate().dateCreat(DEFAULT_DATE_CREAT);
        return paramDate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParamDate createUpdatedEntity(EntityManager em) {
        ParamDate paramDate = new ParamDate().dateCreat(UPDATED_DATE_CREAT);
        return paramDate;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(ParamDate.class).block();
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
        paramDate = createEntity(em);
    }

    @Test
    void createParamDate() throws Exception {
        int databaseSizeBeforeCreate = paramDateRepository.findAll().collectList().block().size();
        // Create the ParamDate
        ParamDateDTO paramDateDTO = paramDateMapper.toDto(paramDate);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(paramDateDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the ParamDate in the database
        List<ParamDate> paramDateList = paramDateRepository.findAll().collectList().block();
        assertThat(paramDateList).hasSize(databaseSizeBeforeCreate + 1);
        ParamDate testParamDate = paramDateList.get(paramDateList.size() - 1);
        assertThat(testParamDate.getDateCreat()).isEqualTo(DEFAULT_DATE_CREAT);
    }

    @Test
    void createParamDateWithExistingId() throws Exception {
        // Create the ParamDate with an existing ID
        paramDate.setId(1L);
        ParamDateDTO paramDateDTO = paramDateMapper.toDto(paramDate);

        int databaseSizeBeforeCreate = paramDateRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(paramDateDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ParamDate in the database
        List<ParamDate> paramDateList = paramDateRepository.findAll().collectList().block();
        assertThat(paramDateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllParamDates() {
        // Initialize the database
        paramDateRepository.save(paramDate).block();

        // Get all the paramDateList
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
            .value(hasItem(paramDate.getId().intValue()))
            .jsonPath("$.[*].dateCreat")
            .value(hasItem(DEFAULT_DATE_CREAT.toString()));
    }

    @Test
    void getParamDate() {
        // Initialize the database
        paramDateRepository.save(paramDate).block();

        // Get the paramDate
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, paramDate.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(paramDate.getId().intValue()))
            .jsonPath("$.dateCreat")
            .value(is(DEFAULT_DATE_CREAT.toString()));
    }

    @Test
    void getNonExistingParamDate() {
        // Get the paramDate
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingParamDate() throws Exception {
        // Initialize the database
        paramDateRepository.save(paramDate).block();

        int databaseSizeBeforeUpdate = paramDateRepository.findAll().collectList().block().size();

        // Update the paramDate
        ParamDate updatedParamDate = paramDateRepository.findById(paramDate.getId()).block();
        updatedParamDate.dateCreat(UPDATED_DATE_CREAT);
        ParamDateDTO paramDateDTO = paramDateMapper.toDto(updatedParamDate);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, paramDateDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(paramDateDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ParamDate in the database
        List<ParamDate> paramDateList = paramDateRepository.findAll().collectList().block();
        assertThat(paramDateList).hasSize(databaseSizeBeforeUpdate);
        ParamDate testParamDate = paramDateList.get(paramDateList.size() - 1);
        assertThat(testParamDate.getDateCreat()).isEqualTo(UPDATED_DATE_CREAT);
    }

    @Test
    void putNonExistingParamDate() throws Exception {
        int databaseSizeBeforeUpdate = paramDateRepository.findAll().collectList().block().size();
        paramDate.setId(count.incrementAndGet());

        // Create the ParamDate
        ParamDateDTO paramDateDTO = paramDateMapper.toDto(paramDate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, paramDateDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(paramDateDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ParamDate in the database
        List<ParamDate> paramDateList = paramDateRepository.findAll().collectList().block();
        assertThat(paramDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchParamDate() throws Exception {
        int databaseSizeBeforeUpdate = paramDateRepository.findAll().collectList().block().size();
        paramDate.setId(count.incrementAndGet());

        // Create the ParamDate
        ParamDateDTO paramDateDTO = paramDateMapper.toDto(paramDate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(paramDateDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ParamDate in the database
        List<ParamDate> paramDateList = paramDateRepository.findAll().collectList().block();
        assertThat(paramDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamParamDate() throws Exception {
        int databaseSizeBeforeUpdate = paramDateRepository.findAll().collectList().block().size();
        paramDate.setId(count.incrementAndGet());

        // Create the ParamDate
        ParamDateDTO paramDateDTO = paramDateMapper.toDto(paramDate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(paramDateDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ParamDate in the database
        List<ParamDate> paramDateList = paramDateRepository.findAll().collectList().block();
        assertThat(paramDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateParamDateWithPatch() throws Exception {
        // Initialize the database
        paramDateRepository.save(paramDate).block();

        int databaseSizeBeforeUpdate = paramDateRepository.findAll().collectList().block().size();

        // Update the paramDate using partial update
        ParamDate partialUpdatedParamDate = new ParamDate();
        partialUpdatedParamDate.setId(paramDate.getId());

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedParamDate.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedParamDate))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ParamDate in the database
        List<ParamDate> paramDateList = paramDateRepository.findAll().collectList().block();
        assertThat(paramDateList).hasSize(databaseSizeBeforeUpdate);
        ParamDate testParamDate = paramDateList.get(paramDateList.size() - 1);
        assertThat(testParamDate.getDateCreat()).isEqualTo(DEFAULT_DATE_CREAT);
    }

    @Test
    void fullUpdateParamDateWithPatch() throws Exception {
        // Initialize the database
        paramDateRepository.save(paramDate).block();

        int databaseSizeBeforeUpdate = paramDateRepository.findAll().collectList().block().size();

        // Update the paramDate using partial update
        ParamDate partialUpdatedParamDate = new ParamDate();
        partialUpdatedParamDate.setId(paramDate.getId());

        partialUpdatedParamDate.dateCreat(UPDATED_DATE_CREAT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedParamDate.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedParamDate))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ParamDate in the database
        List<ParamDate> paramDateList = paramDateRepository.findAll().collectList().block();
        assertThat(paramDateList).hasSize(databaseSizeBeforeUpdate);
        ParamDate testParamDate = paramDateList.get(paramDateList.size() - 1);
        assertThat(testParamDate.getDateCreat()).isEqualTo(UPDATED_DATE_CREAT);
    }

    @Test
    void patchNonExistingParamDate() throws Exception {
        int databaseSizeBeforeUpdate = paramDateRepository.findAll().collectList().block().size();
        paramDate.setId(count.incrementAndGet());

        // Create the ParamDate
        ParamDateDTO paramDateDTO = paramDateMapper.toDto(paramDate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, paramDateDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(paramDateDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ParamDate in the database
        List<ParamDate> paramDateList = paramDateRepository.findAll().collectList().block();
        assertThat(paramDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchParamDate() throws Exception {
        int databaseSizeBeforeUpdate = paramDateRepository.findAll().collectList().block().size();
        paramDate.setId(count.incrementAndGet());

        // Create the ParamDate
        ParamDateDTO paramDateDTO = paramDateMapper.toDto(paramDate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(paramDateDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ParamDate in the database
        List<ParamDate> paramDateList = paramDateRepository.findAll().collectList().block();
        assertThat(paramDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamParamDate() throws Exception {
        int databaseSizeBeforeUpdate = paramDateRepository.findAll().collectList().block().size();
        paramDate.setId(count.incrementAndGet());

        // Create the ParamDate
        ParamDateDTO paramDateDTO = paramDateMapper.toDto(paramDate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(paramDateDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ParamDate in the database
        List<ParamDate> paramDateList = paramDateRepository.findAll().collectList().block();
        assertThat(paramDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteParamDate() {
        // Initialize the database
        paramDateRepository.save(paramDate).block();

        int databaseSizeBeforeDelete = paramDateRepository.findAll().collectList().block().size();

        // Delete the paramDate
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, paramDate.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<ParamDate> paramDateList = paramDateRepository.findAll().collectList().block();
        assertThat(paramDateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
