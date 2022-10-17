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
import org.springframework.util.Base64Utils;
import sn.ssi.sigmap.IntegrationTest;
import sn.ssi.sigmap.domain.Historique;
import sn.ssi.sigmap.domain.PlanPassation;
import sn.ssi.sigmap.repository.EntityManager;
import sn.ssi.sigmap.repository.HistoriqueRepository;
import sn.ssi.sigmap.service.dto.HistoriqueDTO;
import sn.ssi.sigmap.service.mapper.HistoriqueMapper;

/**
 * Integration tests for the {@link HistoriqueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class HistoriqueResourceIT {

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_REJET = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REJET = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_REJET_2 = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REJET_2 = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MISE_VALIDATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MISE_VALIDATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTAIRE_REJET = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE_REJET = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRE_MISE_VALIDATION = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE_MISE_VALIDATION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FICHIER_MISE_VALIDATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHIER_MISE_VALIDATION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHIER_MISE_VALIDATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHIER_MISE_VALIDATION_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FICHIER_REJET = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHIER_REJET = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHIER_REJET_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHIER_REJET_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ETAT_2 = "AAAAAAAAAA";
    private static final String UPDATED_ETAT_2 = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRE_REJET_2 = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE_REJET_2 = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FICHIER_REJET_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHIER_REJET_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHIER_REJET_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHIER_REJET_2_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/historiques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HistoriqueRepository historiqueRepository;

    @Autowired
    private HistoriqueMapper historiqueMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Historique historique;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historique createEntity(EntityManager em) {
        Historique historique = new Historique()
            .etat(DEFAULT_ETAT)
            .dateRejet(DEFAULT_DATE_REJET)
            .dateRejet2(DEFAULT_DATE_REJET_2)
            .dateMiseValidation(DEFAULT_DATE_MISE_VALIDATION)
            .commentaireRejet(DEFAULT_COMMENTAIRE_REJET)
            .commentaireMiseValidation(DEFAULT_COMMENTAIRE_MISE_VALIDATION)
            .fichierMiseValidation(DEFAULT_FICHIER_MISE_VALIDATION)
            .fichierMiseValidationContentType(DEFAULT_FICHIER_MISE_VALIDATION_CONTENT_TYPE)
            .fichierRejet(DEFAULT_FICHIER_REJET)
            .fichierRejetContentType(DEFAULT_FICHIER_REJET_CONTENT_TYPE)
            .etat2(DEFAULT_ETAT_2)
            .commentaireRejet2(DEFAULT_COMMENTAIRE_REJET_2)
            .fichierRejet2(DEFAULT_FICHIER_REJET_2)
            .fichierRejet2ContentType(DEFAULT_FICHIER_REJET_2_CONTENT_TYPE);
        // Add required entity
        PlanPassation planPassation;
        planPassation = em.insert(PlanPassationResourceIT.createEntity(em)).block();
        historique.setPlanPassation(planPassation);
        return historique;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historique createUpdatedEntity(EntityManager em) {
        Historique historique = new Historique()
            .etat(UPDATED_ETAT)
            .dateRejet(UPDATED_DATE_REJET)
            .dateRejet2(UPDATED_DATE_REJET_2)
            .dateMiseValidation(UPDATED_DATE_MISE_VALIDATION)
            .commentaireRejet(UPDATED_COMMENTAIRE_REJET)
            .commentaireMiseValidation(UPDATED_COMMENTAIRE_MISE_VALIDATION)
            .fichierMiseValidation(UPDATED_FICHIER_MISE_VALIDATION)
            .fichierMiseValidationContentType(UPDATED_FICHIER_MISE_VALIDATION_CONTENT_TYPE)
            .fichierRejet(UPDATED_FICHIER_REJET)
            .fichierRejetContentType(UPDATED_FICHIER_REJET_CONTENT_TYPE)
            .etat2(UPDATED_ETAT_2)
            .commentaireRejet2(UPDATED_COMMENTAIRE_REJET_2)
            .fichierRejet2(UPDATED_FICHIER_REJET_2)
            .fichierRejet2ContentType(UPDATED_FICHIER_REJET_2_CONTENT_TYPE);
        // Add required entity
        PlanPassation planPassation;
        planPassation = em.insert(PlanPassationResourceIT.createUpdatedEntity(em)).block();
        historique.setPlanPassation(planPassation);
        return historique;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Historique.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        PlanPassationResourceIT.deleteEntities(em);
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
        historique = createEntity(em);
    }

    @Test
    void createHistorique() throws Exception {
        int databaseSizeBeforeCreate = historiqueRepository.findAll().collectList().block().size();
        // Create the Historique
        HistoriqueDTO historiqueDTO = historiqueMapper.toDto(historique);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(historiqueDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeCreate + 1);
        Historique testHistorique = historiqueList.get(historiqueList.size() - 1);
        assertThat(testHistorique.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testHistorique.getDateRejet()).isEqualTo(DEFAULT_DATE_REJET);
        assertThat(testHistorique.getDateRejet2()).isEqualTo(DEFAULT_DATE_REJET_2);
        assertThat(testHistorique.getDateMiseValidation()).isEqualTo(DEFAULT_DATE_MISE_VALIDATION);
        assertThat(testHistorique.getCommentaireRejet()).isEqualTo(DEFAULT_COMMENTAIRE_REJET);
        assertThat(testHistorique.getCommentaireMiseValidation()).isEqualTo(DEFAULT_COMMENTAIRE_MISE_VALIDATION);
        assertThat(testHistorique.getFichierMiseValidation()).isEqualTo(DEFAULT_FICHIER_MISE_VALIDATION);
        assertThat(testHistorique.getFichierMiseValidationContentType()).isEqualTo(DEFAULT_FICHIER_MISE_VALIDATION_CONTENT_TYPE);
        assertThat(testHistorique.getFichierRejet()).isEqualTo(DEFAULT_FICHIER_REJET);
        assertThat(testHistorique.getFichierRejetContentType()).isEqualTo(DEFAULT_FICHIER_REJET_CONTENT_TYPE);
        assertThat(testHistorique.getEtat2()).isEqualTo(DEFAULT_ETAT_2);
        assertThat(testHistorique.getCommentaireRejet2()).isEqualTo(DEFAULT_COMMENTAIRE_REJET_2);
        assertThat(testHistorique.getFichierRejet2()).isEqualTo(DEFAULT_FICHIER_REJET_2);
        assertThat(testHistorique.getFichierRejet2ContentType()).isEqualTo(DEFAULT_FICHIER_REJET_2_CONTENT_TYPE);
    }

    @Test
    void createHistoriqueWithExistingId() throws Exception {
        // Create the Historique with an existing ID
        historique.setId(1L);
        HistoriqueDTO historiqueDTO = historiqueMapper.toDto(historique);

        int databaseSizeBeforeCreate = historiqueRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(historiqueDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllHistoriques() {
        // Initialize the database
        historiqueRepository.save(historique).block();

        // Get all the historiqueList
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
            .value(hasItem(historique.getId().intValue()))
            .jsonPath("$.[*].etat")
            .value(hasItem(DEFAULT_ETAT))
            .jsonPath("$.[*].dateRejet")
            .value(hasItem(DEFAULT_DATE_REJET.toString()))
            .jsonPath("$.[*].dateRejet2")
            .value(hasItem(DEFAULT_DATE_REJET_2.toString()))
            .jsonPath("$.[*].dateMiseValidation")
            .value(hasItem(DEFAULT_DATE_MISE_VALIDATION.toString()))
            .jsonPath("$.[*].commentaireRejet")
            .value(hasItem(DEFAULT_COMMENTAIRE_REJET))
            .jsonPath("$.[*].commentaireMiseValidation")
            .value(hasItem(DEFAULT_COMMENTAIRE_MISE_VALIDATION))
            .jsonPath("$.[*].fichierMiseValidationContentType")
            .value(hasItem(DEFAULT_FICHIER_MISE_VALIDATION_CONTENT_TYPE))
            .jsonPath("$.[*].fichierMiseValidation")
            .value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER_MISE_VALIDATION)))
            .jsonPath("$.[*].fichierRejetContentType")
            .value(hasItem(DEFAULT_FICHIER_REJET_CONTENT_TYPE))
            .jsonPath("$.[*].fichierRejet")
            .value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER_REJET)))
            .jsonPath("$.[*].etat2")
            .value(hasItem(DEFAULT_ETAT_2))
            .jsonPath("$.[*].commentaireRejet2")
            .value(hasItem(DEFAULT_COMMENTAIRE_REJET_2))
            .jsonPath("$.[*].fichierRejet2ContentType")
            .value(hasItem(DEFAULT_FICHIER_REJET_2_CONTENT_TYPE))
            .jsonPath("$.[*].fichierRejet2")
            .value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER_REJET_2)));
    }

    @Test
    void getHistorique() {
        // Initialize the database
        historiqueRepository.save(historique).block();

        // Get the historique
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, historique.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(historique.getId().intValue()))
            .jsonPath("$.etat")
            .value(is(DEFAULT_ETAT))
            .jsonPath("$.dateRejet")
            .value(is(DEFAULT_DATE_REJET.toString()))
            .jsonPath("$.dateRejet2")
            .value(is(DEFAULT_DATE_REJET_2.toString()))
            .jsonPath("$.dateMiseValidation")
            .value(is(DEFAULT_DATE_MISE_VALIDATION.toString()))
            .jsonPath("$.commentaireRejet")
            .value(is(DEFAULT_COMMENTAIRE_REJET))
            .jsonPath("$.commentaireMiseValidation")
            .value(is(DEFAULT_COMMENTAIRE_MISE_VALIDATION))
            .jsonPath("$.fichierMiseValidationContentType")
            .value(is(DEFAULT_FICHIER_MISE_VALIDATION_CONTENT_TYPE))
            .jsonPath("$.fichierMiseValidation")
            .value(is(Base64Utils.encodeToString(DEFAULT_FICHIER_MISE_VALIDATION)))
            .jsonPath("$.fichierRejetContentType")
            .value(is(DEFAULT_FICHIER_REJET_CONTENT_TYPE))
            .jsonPath("$.fichierRejet")
            .value(is(Base64Utils.encodeToString(DEFAULT_FICHIER_REJET)))
            .jsonPath("$.etat2")
            .value(is(DEFAULT_ETAT_2))
            .jsonPath("$.commentaireRejet2")
            .value(is(DEFAULT_COMMENTAIRE_REJET_2))
            .jsonPath("$.fichierRejet2ContentType")
            .value(is(DEFAULT_FICHIER_REJET_2_CONTENT_TYPE))
            .jsonPath("$.fichierRejet2")
            .value(is(Base64Utils.encodeToString(DEFAULT_FICHIER_REJET_2)));
    }

    @Test
    void getNonExistingHistorique() {
        // Get the historique
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingHistorique() throws Exception {
        // Initialize the database
        historiqueRepository.save(historique).block();

        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();

        // Update the historique
        Historique updatedHistorique = historiqueRepository.findById(historique.getId()).block();
        updatedHistorique
            .etat(UPDATED_ETAT)
            .dateRejet(UPDATED_DATE_REJET)
            .dateRejet2(UPDATED_DATE_REJET_2)
            .dateMiseValidation(UPDATED_DATE_MISE_VALIDATION)
            .commentaireRejet(UPDATED_COMMENTAIRE_REJET)
            .commentaireMiseValidation(UPDATED_COMMENTAIRE_MISE_VALIDATION)
            .fichierMiseValidation(UPDATED_FICHIER_MISE_VALIDATION)
            .fichierMiseValidationContentType(UPDATED_FICHIER_MISE_VALIDATION_CONTENT_TYPE)
            .fichierRejet(UPDATED_FICHIER_REJET)
            .fichierRejetContentType(UPDATED_FICHIER_REJET_CONTENT_TYPE)
            .etat2(UPDATED_ETAT_2)
            .commentaireRejet2(UPDATED_COMMENTAIRE_REJET_2)
            .fichierRejet2(UPDATED_FICHIER_REJET_2)
            .fichierRejet2ContentType(UPDATED_FICHIER_REJET_2_CONTENT_TYPE);
        HistoriqueDTO historiqueDTO = historiqueMapper.toDto(updatedHistorique);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, historiqueDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(historiqueDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
        Historique testHistorique = historiqueList.get(historiqueList.size() - 1);
        assertThat(testHistorique.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testHistorique.getDateRejet()).isEqualTo(UPDATED_DATE_REJET);
        assertThat(testHistorique.getDateRejet2()).isEqualTo(UPDATED_DATE_REJET_2);
        assertThat(testHistorique.getDateMiseValidation()).isEqualTo(UPDATED_DATE_MISE_VALIDATION);
        assertThat(testHistorique.getCommentaireRejet()).isEqualTo(UPDATED_COMMENTAIRE_REJET);
        assertThat(testHistorique.getCommentaireMiseValidation()).isEqualTo(UPDATED_COMMENTAIRE_MISE_VALIDATION);
        assertThat(testHistorique.getFichierMiseValidation()).isEqualTo(UPDATED_FICHIER_MISE_VALIDATION);
        assertThat(testHistorique.getFichierMiseValidationContentType()).isEqualTo(UPDATED_FICHIER_MISE_VALIDATION_CONTENT_TYPE);
        assertThat(testHistorique.getFichierRejet()).isEqualTo(UPDATED_FICHIER_REJET);
        assertThat(testHistorique.getFichierRejetContentType()).isEqualTo(UPDATED_FICHIER_REJET_CONTENT_TYPE);
        assertThat(testHistorique.getEtat2()).isEqualTo(UPDATED_ETAT_2);
        assertThat(testHistorique.getCommentaireRejet2()).isEqualTo(UPDATED_COMMENTAIRE_REJET_2);
        assertThat(testHistorique.getFichierRejet2()).isEqualTo(UPDATED_FICHIER_REJET_2);
        assertThat(testHistorique.getFichierRejet2ContentType()).isEqualTo(UPDATED_FICHIER_REJET_2_CONTENT_TYPE);
    }

    @Test
    void putNonExistingHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();
        historique.setId(count.incrementAndGet());

        // Create the Historique
        HistoriqueDTO historiqueDTO = historiqueMapper.toDto(historique);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, historiqueDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(historiqueDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();
        historique.setId(count.incrementAndGet());

        // Create the Historique
        HistoriqueDTO historiqueDTO = historiqueMapper.toDto(historique);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(historiqueDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();
        historique.setId(count.incrementAndGet());

        // Create the Historique
        HistoriqueDTO historiqueDTO = historiqueMapper.toDto(historique);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(historiqueDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateHistoriqueWithPatch() throws Exception {
        // Initialize the database
        historiqueRepository.save(historique).block();

        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();

        // Update the historique using partial update
        Historique partialUpdatedHistorique = new Historique();
        partialUpdatedHistorique.setId(historique.getId());

        partialUpdatedHistorique
            .etat(UPDATED_ETAT)
            .dateMiseValidation(UPDATED_DATE_MISE_VALIDATION)
            .commentaireRejet(UPDATED_COMMENTAIRE_REJET)
            .commentaireMiseValidation(UPDATED_COMMENTAIRE_MISE_VALIDATION)
            .fichierMiseValidation(UPDATED_FICHIER_MISE_VALIDATION)
            .fichierMiseValidationContentType(UPDATED_FICHIER_MISE_VALIDATION_CONTENT_TYPE)
            .fichierRejet(UPDATED_FICHIER_REJET)
            .fichierRejetContentType(UPDATED_FICHIER_REJET_CONTENT_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedHistorique.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedHistorique))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
        Historique testHistorique = historiqueList.get(historiqueList.size() - 1);
        assertThat(testHistorique.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testHistorique.getDateRejet()).isEqualTo(DEFAULT_DATE_REJET);
        assertThat(testHistorique.getDateRejet2()).isEqualTo(DEFAULT_DATE_REJET_2);
        assertThat(testHistorique.getDateMiseValidation()).isEqualTo(UPDATED_DATE_MISE_VALIDATION);
        assertThat(testHistorique.getCommentaireRejet()).isEqualTo(UPDATED_COMMENTAIRE_REJET);
        assertThat(testHistorique.getCommentaireMiseValidation()).isEqualTo(UPDATED_COMMENTAIRE_MISE_VALIDATION);
        assertThat(testHistorique.getFichierMiseValidation()).isEqualTo(UPDATED_FICHIER_MISE_VALIDATION);
        assertThat(testHistorique.getFichierMiseValidationContentType()).isEqualTo(UPDATED_FICHIER_MISE_VALIDATION_CONTENT_TYPE);
        assertThat(testHistorique.getFichierRejet()).isEqualTo(UPDATED_FICHIER_REJET);
        assertThat(testHistorique.getFichierRejetContentType()).isEqualTo(UPDATED_FICHIER_REJET_CONTENT_TYPE);
        assertThat(testHistorique.getEtat2()).isEqualTo(DEFAULT_ETAT_2);
        assertThat(testHistorique.getCommentaireRejet2()).isEqualTo(DEFAULT_COMMENTAIRE_REJET_2);
        assertThat(testHistorique.getFichierRejet2()).isEqualTo(DEFAULT_FICHIER_REJET_2);
        assertThat(testHistorique.getFichierRejet2ContentType()).isEqualTo(DEFAULT_FICHIER_REJET_2_CONTENT_TYPE);
    }

    @Test
    void fullUpdateHistoriqueWithPatch() throws Exception {
        // Initialize the database
        historiqueRepository.save(historique).block();

        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();

        // Update the historique using partial update
        Historique partialUpdatedHistorique = new Historique();
        partialUpdatedHistorique.setId(historique.getId());

        partialUpdatedHistorique
            .etat(UPDATED_ETAT)
            .dateRejet(UPDATED_DATE_REJET)
            .dateRejet2(UPDATED_DATE_REJET_2)
            .dateMiseValidation(UPDATED_DATE_MISE_VALIDATION)
            .commentaireRejet(UPDATED_COMMENTAIRE_REJET)
            .commentaireMiseValidation(UPDATED_COMMENTAIRE_MISE_VALIDATION)
            .fichierMiseValidation(UPDATED_FICHIER_MISE_VALIDATION)
            .fichierMiseValidationContentType(UPDATED_FICHIER_MISE_VALIDATION_CONTENT_TYPE)
            .fichierRejet(UPDATED_FICHIER_REJET)
            .fichierRejetContentType(UPDATED_FICHIER_REJET_CONTENT_TYPE)
            .etat2(UPDATED_ETAT_2)
            .commentaireRejet2(UPDATED_COMMENTAIRE_REJET_2)
            .fichierRejet2(UPDATED_FICHIER_REJET_2)
            .fichierRejet2ContentType(UPDATED_FICHIER_REJET_2_CONTENT_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedHistorique.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedHistorique))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
        Historique testHistorique = historiqueList.get(historiqueList.size() - 1);
        assertThat(testHistorique.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testHistorique.getDateRejet()).isEqualTo(UPDATED_DATE_REJET);
        assertThat(testHistorique.getDateRejet2()).isEqualTo(UPDATED_DATE_REJET_2);
        assertThat(testHistorique.getDateMiseValidation()).isEqualTo(UPDATED_DATE_MISE_VALIDATION);
        assertThat(testHistorique.getCommentaireRejet()).isEqualTo(UPDATED_COMMENTAIRE_REJET);
        assertThat(testHistorique.getCommentaireMiseValidation()).isEqualTo(UPDATED_COMMENTAIRE_MISE_VALIDATION);
        assertThat(testHistorique.getFichierMiseValidation()).isEqualTo(UPDATED_FICHIER_MISE_VALIDATION);
        assertThat(testHistorique.getFichierMiseValidationContentType()).isEqualTo(UPDATED_FICHIER_MISE_VALIDATION_CONTENT_TYPE);
        assertThat(testHistorique.getFichierRejet()).isEqualTo(UPDATED_FICHIER_REJET);
        assertThat(testHistorique.getFichierRejetContentType()).isEqualTo(UPDATED_FICHIER_REJET_CONTENT_TYPE);
        assertThat(testHistorique.getEtat2()).isEqualTo(UPDATED_ETAT_2);
        assertThat(testHistorique.getCommentaireRejet2()).isEqualTo(UPDATED_COMMENTAIRE_REJET_2);
        assertThat(testHistorique.getFichierRejet2()).isEqualTo(UPDATED_FICHIER_REJET_2);
        assertThat(testHistorique.getFichierRejet2ContentType()).isEqualTo(UPDATED_FICHIER_REJET_2_CONTENT_TYPE);
    }

    @Test
    void patchNonExistingHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();
        historique.setId(count.incrementAndGet());

        // Create the Historique
        HistoriqueDTO historiqueDTO = historiqueMapper.toDto(historique);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, historiqueDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(historiqueDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();
        historique.setId(count.incrementAndGet());

        // Create the Historique
        HistoriqueDTO historiqueDTO = historiqueMapper.toDto(historique);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(historiqueDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();
        historique.setId(count.incrementAndGet());

        // Create the Historique
        HistoriqueDTO historiqueDTO = historiqueMapper.toDto(historique);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(historiqueDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteHistorique() {
        // Initialize the database
        historiqueRepository.save(historique).block();

        int databaseSizeBeforeDelete = historiqueRepository.findAll().collectList().block().size();

        // Delete the historique
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, historique.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
