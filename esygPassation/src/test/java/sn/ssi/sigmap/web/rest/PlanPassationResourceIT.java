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
import sn.ssi.sigmap.domain.PlanPassation;
import sn.ssi.sigmap.repository.EntityManager;
import sn.ssi.sigmap.repository.PlanPassationRepository;
import sn.ssi.sigmap.service.dto.PlanPassationDTO;
import sn.ssi.sigmap.service.mapper.PlanPassationMapper;

/**
 * Integration tests for the {@link PlanPassationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class PlanPassationResourceIT {

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANNEE = 1;
    private static final Integer UPDATED_ANNEE = 2;

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MISE_VALIDATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MISE_VALIDATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_VALIDATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_VALIDATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTAIRE_MISE_EN_VALIDATION_AC = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE_MISE_EN_VALIDATION_AC = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_MISE_VALIDATION_AC = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_MISE_VALIDATION_AC = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FICHIER_MISE_VALIDATION_AC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHIER_MISE_VALIDATION_AC = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHIER_MISE_VALIDATION_AC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHIER_MISE_VALIDATION_AC_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_DATE_MISE_EN_VALIDATION_CCMP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MISE_EN_VALIDATION_CCMP = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_FICHIER_MISE_VALIDATION_CCMP = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHIER_MISE_VALIDATION_CCMP = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHIER_MISE_VALIDATION_CCMP_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHIER_MISE_VALIDATION_CCMP_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_REFERENCE_MISE_VALIDATION_CCMP = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_MISE_VALIDATION_CCMP = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_VALIDATION_1 = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_VALIDATION_1 = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTAIRE_VALIDATION = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE_VALIDATION = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_VALIDATION = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_VALIDATION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FICHIER_VALIDATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHIER_VALIDATION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHIER_VALIDATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHIER_VALIDATION_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_DATE_VALIDATION_2 = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_VALIDATION_2 = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_REJET = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REJET = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_PUBLICATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PUBLICATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTAIRE_PUBLICATION = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE_PUBLICATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/plan-passations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlanPassationRepository planPassationRepository;

    @Autowired
    private PlanPassationMapper planPassationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private PlanPassation planPassation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanPassation createEntity(EntityManager em) {
        PlanPassation planPassation = new PlanPassation()
            .statut(DEFAULT_STATUT)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .commentaire(DEFAULT_COMMENTAIRE)
            .annee(DEFAULT_ANNEE)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateMiseValidation(DEFAULT_DATE_MISE_VALIDATION)
            .dateValidation(DEFAULT_DATE_VALIDATION)
            .commentaireMiseEnValidationAC(DEFAULT_COMMENTAIRE_MISE_EN_VALIDATION_AC)
            .referenceMiseValidationAC(DEFAULT_REFERENCE_MISE_VALIDATION_AC)
            .fichierMiseValidationAC(DEFAULT_FICHIER_MISE_VALIDATION_AC)
            .fichierMiseValidationACContentType(DEFAULT_FICHIER_MISE_VALIDATION_AC_CONTENT_TYPE)
            .dateMiseEnValidationCcmp(DEFAULT_DATE_MISE_EN_VALIDATION_CCMP)
            .fichierMiseValidationCcmp(DEFAULT_FICHIER_MISE_VALIDATION_CCMP)
            .fichierMiseValidationCcmpContentType(DEFAULT_FICHIER_MISE_VALIDATION_CCMP_CONTENT_TYPE)
            .referenceMiseValidationCcmp(DEFAULT_REFERENCE_MISE_VALIDATION_CCMP)
            .dateValidation1(DEFAULT_DATE_VALIDATION_1)
            .commentaireValidation(DEFAULT_COMMENTAIRE_VALIDATION)
            .referenceValidation(DEFAULT_REFERENCE_VALIDATION)
            .fichierValidation(DEFAULT_FICHIER_VALIDATION)
            .fichierValidationContentType(DEFAULT_FICHIER_VALIDATION_CONTENT_TYPE)
            .dateValidation2(DEFAULT_DATE_VALIDATION_2)
            .dateRejet(DEFAULT_DATE_REJET)
            .datePublication(DEFAULT_DATE_PUBLICATION)
            .commentairePublication(DEFAULT_COMMENTAIRE_PUBLICATION);
        return planPassation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanPassation createUpdatedEntity(EntityManager em) {
        PlanPassation planPassation = new PlanPassation()
            .statut(UPDATED_STATUT)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .commentaire(UPDATED_COMMENTAIRE)
            .annee(UPDATED_ANNEE)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateMiseValidation(UPDATED_DATE_MISE_VALIDATION)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .commentaireMiseEnValidationAC(UPDATED_COMMENTAIRE_MISE_EN_VALIDATION_AC)
            .referenceMiseValidationAC(UPDATED_REFERENCE_MISE_VALIDATION_AC)
            .fichierMiseValidationAC(UPDATED_FICHIER_MISE_VALIDATION_AC)
            .fichierMiseValidationACContentType(UPDATED_FICHIER_MISE_VALIDATION_AC_CONTENT_TYPE)
            .dateMiseEnValidationCcmp(UPDATED_DATE_MISE_EN_VALIDATION_CCMP)
            .fichierMiseValidationCcmp(UPDATED_FICHIER_MISE_VALIDATION_CCMP)
            .fichierMiseValidationCcmpContentType(UPDATED_FICHIER_MISE_VALIDATION_CCMP_CONTENT_TYPE)
            .referenceMiseValidationCcmp(UPDATED_REFERENCE_MISE_VALIDATION_CCMP)
            .dateValidation1(UPDATED_DATE_VALIDATION_1)
            .commentaireValidation(UPDATED_COMMENTAIRE_VALIDATION)
            .referenceValidation(UPDATED_REFERENCE_VALIDATION)
            .fichierValidation(UPDATED_FICHIER_VALIDATION)
            .fichierValidationContentType(UPDATED_FICHIER_VALIDATION_CONTENT_TYPE)
            .dateValidation2(UPDATED_DATE_VALIDATION_2)
            .dateRejet(UPDATED_DATE_REJET)
            .datePublication(UPDATED_DATE_PUBLICATION)
            .commentairePublication(UPDATED_COMMENTAIRE_PUBLICATION);
        return planPassation;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(PlanPassation.class).block();
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
        planPassation = createEntity(em);
    }

    @Test
    void createPlanPassation() throws Exception {
        int databaseSizeBeforeCreate = planPassationRepository.findAll().collectList().block().size();
        // Create the PlanPassation
        PlanPassationDTO planPassationDTO = planPassationMapper.toDto(planPassation);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(planPassationDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the PlanPassation in the database
        List<PlanPassation> planPassationList = planPassationRepository.findAll().collectList().block();
        assertThat(planPassationList).hasSize(databaseSizeBeforeCreate + 1);
        PlanPassation testPlanPassation = planPassationList.get(planPassationList.size() - 1);
        assertThat(testPlanPassation.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testPlanPassation.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testPlanPassation.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testPlanPassation.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testPlanPassation.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testPlanPassation.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testPlanPassation.getDateMiseValidation()).isEqualTo(DEFAULT_DATE_MISE_VALIDATION);
        assertThat(testPlanPassation.getDateValidation()).isEqualTo(DEFAULT_DATE_VALIDATION);
        assertThat(testPlanPassation.getCommentaireMiseEnValidationAC()).isEqualTo(DEFAULT_COMMENTAIRE_MISE_EN_VALIDATION_AC);
        assertThat(testPlanPassation.getReferenceMiseValidationAC()).isEqualTo(DEFAULT_REFERENCE_MISE_VALIDATION_AC);
        assertThat(testPlanPassation.getFichierMiseValidationAC()).isEqualTo(DEFAULT_FICHIER_MISE_VALIDATION_AC);
        assertThat(testPlanPassation.getFichierMiseValidationACContentType()).isEqualTo(DEFAULT_FICHIER_MISE_VALIDATION_AC_CONTENT_TYPE);
        assertThat(testPlanPassation.getDateMiseEnValidationCcmp()).isEqualTo(DEFAULT_DATE_MISE_EN_VALIDATION_CCMP);
        assertThat(testPlanPassation.getFichierMiseValidationCcmp()).isEqualTo(DEFAULT_FICHIER_MISE_VALIDATION_CCMP);
        assertThat(testPlanPassation.getFichierMiseValidationCcmpContentType())
            .isEqualTo(DEFAULT_FICHIER_MISE_VALIDATION_CCMP_CONTENT_TYPE);
        assertThat(testPlanPassation.getReferenceMiseValidationCcmp()).isEqualTo(DEFAULT_REFERENCE_MISE_VALIDATION_CCMP);
        assertThat(testPlanPassation.getDateValidation1()).isEqualTo(DEFAULT_DATE_VALIDATION_1);
        assertThat(testPlanPassation.getCommentaireValidation()).isEqualTo(DEFAULT_COMMENTAIRE_VALIDATION);
        assertThat(testPlanPassation.getReferenceValidation()).isEqualTo(DEFAULT_REFERENCE_VALIDATION);
        assertThat(testPlanPassation.getFichierValidation()).isEqualTo(DEFAULT_FICHIER_VALIDATION);
        assertThat(testPlanPassation.getFichierValidationContentType()).isEqualTo(DEFAULT_FICHIER_VALIDATION_CONTENT_TYPE);
        assertThat(testPlanPassation.getDateValidation2()).isEqualTo(DEFAULT_DATE_VALIDATION_2);
        assertThat(testPlanPassation.getDateRejet()).isEqualTo(DEFAULT_DATE_REJET);
        assertThat(testPlanPassation.getDatePublication()).isEqualTo(DEFAULT_DATE_PUBLICATION);
        assertThat(testPlanPassation.getCommentairePublication()).isEqualTo(DEFAULT_COMMENTAIRE_PUBLICATION);
    }

    @Test
    void createPlanPassationWithExistingId() throws Exception {
        // Create the PlanPassation with an existing ID
        planPassation.setId(1L);
        PlanPassationDTO planPassationDTO = planPassationMapper.toDto(planPassation);

        int databaseSizeBeforeCreate = planPassationRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(planPassationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PlanPassation in the database
        List<PlanPassation> planPassationList = planPassationRepository.findAll().collectList().block();
        assertThat(planPassationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkAnneeIsRequired() throws Exception {
        int databaseSizeBeforeTest = planPassationRepository.findAll().collectList().block().size();
        // set the field null
        planPassation.setAnnee(null);

        // Create the PlanPassation, which fails.
        PlanPassationDTO planPassationDTO = planPassationMapper.toDto(planPassation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(planPassationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<PlanPassation> planPassationList = planPassationRepository.findAll().collectList().block();
        assertThat(planPassationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDateCreationIsRequired() throws Exception {
        int databaseSizeBeforeTest = planPassationRepository.findAll().collectList().block().size();
        // set the field null
        planPassation.setDateCreation(null);

        // Create the PlanPassation, which fails.
        PlanPassationDTO planPassationDTO = planPassationMapper.toDto(planPassation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(planPassationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<PlanPassation> planPassationList = planPassationRepository.findAll().collectList().block();
        assertThat(planPassationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllPlanPassations() {
        // Initialize the database
        planPassationRepository.save(planPassation).block();

        // Get all the planPassationList
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
            .value(hasItem(planPassation.getId().intValue()))
            .jsonPath("$.[*].statut")
            .value(hasItem(DEFAULT_STATUT))
            .jsonPath("$.[*].dateDebut")
            .value(hasItem(DEFAULT_DATE_DEBUT.toString()))
            .jsonPath("$.[*].dateFin")
            .value(hasItem(DEFAULT_DATE_FIN.toString()))
            .jsonPath("$.[*].commentaire")
            .value(hasItem(DEFAULT_COMMENTAIRE))
            .jsonPath("$.[*].annee")
            .value(hasItem(DEFAULT_ANNEE))
            .jsonPath("$.[*].dateCreation")
            .value(hasItem(DEFAULT_DATE_CREATION.toString()))
            .jsonPath("$.[*].dateMiseValidation")
            .value(hasItem(DEFAULT_DATE_MISE_VALIDATION.toString()))
            .jsonPath("$.[*].dateValidation")
            .value(hasItem(DEFAULT_DATE_VALIDATION.toString()))
            .jsonPath("$.[*].commentaireMiseEnValidationAC")
            .value(hasItem(DEFAULT_COMMENTAIRE_MISE_EN_VALIDATION_AC))
            .jsonPath("$.[*].referenceMiseValidationAC")
            .value(hasItem(DEFAULT_REFERENCE_MISE_VALIDATION_AC))
            .jsonPath("$.[*].fichierMiseValidationACContentType")
            .value(hasItem(DEFAULT_FICHIER_MISE_VALIDATION_AC_CONTENT_TYPE))
            .jsonPath("$.[*].fichierMiseValidationAC")
            .value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER_MISE_VALIDATION_AC)))
            .jsonPath("$.[*].dateMiseEnValidationCcmp")
            .value(hasItem(DEFAULT_DATE_MISE_EN_VALIDATION_CCMP.toString()))
            .jsonPath("$.[*].fichierMiseValidationCcmpContentType")
            .value(hasItem(DEFAULT_FICHIER_MISE_VALIDATION_CCMP_CONTENT_TYPE))
            .jsonPath("$.[*].fichierMiseValidationCcmp")
            .value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER_MISE_VALIDATION_CCMP)))
            .jsonPath("$.[*].referenceMiseValidationCcmp")
            .value(hasItem(DEFAULT_REFERENCE_MISE_VALIDATION_CCMP))
            .jsonPath("$.[*].dateValidation1")
            .value(hasItem(DEFAULT_DATE_VALIDATION_1.toString()))
            .jsonPath("$.[*].commentaireValidation")
            .value(hasItem(DEFAULT_COMMENTAIRE_VALIDATION))
            .jsonPath("$.[*].referenceValidation")
            .value(hasItem(DEFAULT_REFERENCE_VALIDATION))
            .jsonPath("$.[*].fichierValidationContentType")
            .value(hasItem(DEFAULT_FICHIER_VALIDATION_CONTENT_TYPE))
            .jsonPath("$.[*].fichierValidation")
            .value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER_VALIDATION)))
            .jsonPath("$.[*].dateValidation2")
            .value(hasItem(DEFAULT_DATE_VALIDATION_2.toString()))
            .jsonPath("$.[*].dateRejet")
            .value(hasItem(DEFAULT_DATE_REJET.toString()))
            .jsonPath("$.[*].datePublication")
            .value(hasItem(DEFAULT_DATE_PUBLICATION.toString()))
            .jsonPath("$.[*].commentairePublication")
            .value(hasItem(DEFAULT_COMMENTAIRE_PUBLICATION));
    }

    @Test
    void getPlanPassation() {
        // Initialize the database
        planPassationRepository.save(planPassation).block();

        // Get the planPassation
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, planPassation.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(planPassation.getId().intValue()))
            .jsonPath("$.statut")
            .value(is(DEFAULT_STATUT))
            .jsonPath("$.dateDebut")
            .value(is(DEFAULT_DATE_DEBUT.toString()))
            .jsonPath("$.dateFin")
            .value(is(DEFAULT_DATE_FIN.toString()))
            .jsonPath("$.commentaire")
            .value(is(DEFAULT_COMMENTAIRE))
            .jsonPath("$.annee")
            .value(is(DEFAULT_ANNEE))
            .jsonPath("$.dateCreation")
            .value(is(DEFAULT_DATE_CREATION.toString()))
            .jsonPath("$.dateMiseValidation")
            .value(is(DEFAULT_DATE_MISE_VALIDATION.toString()))
            .jsonPath("$.dateValidation")
            .value(is(DEFAULT_DATE_VALIDATION.toString()))
            .jsonPath("$.commentaireMiseEnValidationAC")
            .value(is(DEFAULT_COMMENTAIRE_MISE_EN_VALIDATION_AC))
            .jsonPath("$.referenceMiseValidationAC")
            .value(is(DEFAULT_REFERENCE_MISE_VALIDATION_AC))
            .jsonPath("$.fichierMiseValidationACContentType")
            .value(is(DEFAULT_FICHIER_MISE_VALIDATION_AC_CONTENT_TYPE))
            .jsonPath("$.fichierMiseValidationAC")
            .value(is(Base64Utils.encodeToString(DEFAULT_FICHIER_MISE_VALIDATION_AC)))
            .jsonPath("$.dateMiseEnValidationCcmp")
            .value(is(DEFAULT_DATE_MISE_EN_VALIDATION_CCMP.toString()))
            .jsonPath("$.fichierMiseValidationCcmpContentType")
            .value(is(DEFAULT_FICHIER_MISE_VALIDATION_CCMP_CONTENT_TYPE))
            .jsonPath("$.fichierMiseValidationCcmp")
            .value(is(Base64Utils.encodeToString(DEFAULT_FICHIER_MISE_VALIDATION_CCMP)))
            .jsonPath("$.referenceMiseValidationCcmp")
            .value(is(DEFAULT_REFERENCE_MISE_VALIDATION_CCMP))
            .jsonPath("$.dateValidation1")
            .value(is(DEFAULT_DATE_VALIDATION_1.toString()))
            .jsonPath("$.commentaireValidation")
            .value(is(DEFAULT_COMMENTAIRE_VALIDATION))
            .jsonPath("$.referenceValidation")
            .value(is(DEFAULT_REFERENCE_VALIDATION))
            .jsonPath("$.fichierValidationContentType")
            .value(is(DEFAULT_FICHIER_VALIDATION_CONTENT_TYPE))
            .jsonPath("$.fichierValidation")
            .value(is(Base64Utils.encodeToString(DEFAULT_FICHIER_VALIDATION)))
            .jsonPath("$.dateValidation2")
            .value(is(DEFAULT_DATE_VALIDATION_2.toString()))
            .jsonPath("$.dateRejet")
            .value(is(DEFAULT_DATE_REJET.toString()))
            .jsonPath("$.datePublication")
            .value(is(DEFAULT_DATE_PUBLICATION.toString()))
            .jsonPath("$.commentairePublication")
            .value(is(DEFAULT_COMMENTAIRE_PUBLICATION));
    }

    @Test
    void getNonExistingPlanPassation() {
        // Get the planPassation
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingPlanPassation() throws Exception {
        // Initialize the database
        planPassationRepository.save(planPassation).block();

        int databaseSizeBeforeUpdate = planPassationRepository.findAll().collectList().block().size();

        // Update the planPassation
        PlanPassation updatedPlanPassation = planPassationRepository.findById(planPassation.getId()).block();
        updatedPlanPassation
            .statut(UPDATED_STATUT)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .commentaire(UPDATED_COMMENTAIRE)
            .annee(UPDATED_ANNEE)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateMiseValidation(UPDATED_DATE_MISE_VALIDATION)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .commentaireMiseEnValidationAC(UPDATED_COMMENTAIRE_MISE_EN_VALIDATION_AC)
            .referenceMiseValidationAC(UPDATED_REFERENCE_MISE_VALIDATION_AC)
            .fichierMiseValidationAC(UPDATED_FICHIER_MISE_VALIDATION_AC)
            .fichierMiseValidationACContentType(UPDATED_FICHIER_MISE_VALIDATION_AC_CONTENT_TYPE)
            .dateMiseEnValidationCcmp(UPDATED_DATE_MISE_EN_VALIDATION_CCMP)
            .fichierMiseValidationCcmp(UPDATED_FICHIER_MISE_VALIDATION_CCMP)
            .fichierMiseValidationCcmpContentType(UPDATED_FICHIER_MISE_VALIDATION_CCMP_CONTENT_TYPE)
            .referenceMiseValidationCcmp(UPDATED_REFERENCE_MISE_VALIDATION_CCMP)
            .dateValidation1(UPDATED_DATE_VALIDATION_1)
            .commentaireValidation(UPDATED_COMMENTAIRE_VALIDATION)
            .referenceValidation(UPDATED_REFERENCE_VALIDATION)
            .fichierValidation(UPDATED_FICHIER_VALIDATION)
            .fichierValidationContentType(UPDATED_FICHIER_VALIDATION_CONTENT_TYPE)
            .dateValidation2(UPDATED_DATE_VALIDATION_2)
            .dateRejet(UPDATED_DATE_REJET)
            .datePublication(UPDATED_DATE_PUBLICATION)
            .commentairePublication(UPDATED_COMMENTAIRE_PUBLICATION);
        PlanPassationDTO planPassationDTO = planPassationMapper.toDto(updatedPlanPassation);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, planPassationDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(planPassationDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PlanPassation in the database
        List<PlanPassation> planPassationList = planPassationRepository.findAll().collectList().block();
        assertThat(planPassationList).hasSize(databaseSizeBeforeUpdate);
        PlanPassation testPlanPassation = planPassationList.get(planPassationList.size() - 1);
        assertThat(testPlanPassation.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testPlanPassation.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testPlanPassation.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testPlanPassation.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testPlanPassation.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testPlanPassation.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testPlanPassation.getDateMiseValidation()).isEqualTo(UPDATED_DATE_MISE_VALIDATION);
        assertThat(testPlanPassation.getDateValidation()).isEqualTo(UPDATED_DATE_VALIDATION);
        assertThat(testPlanPassation.getCommentaireMiseEnValidationAC()).isEqualTo(UPDATED_COMMENTAIRE_MISE_EN_VALIDATION_AC);
        assertThat(testPlanPassation.getReferenceMiseValidationAC()).isEqualTo(UPDATED_REFERENCE_MISE_VALIDATION_AC);
        assertThat(testPlanPassation.getFichierMiseValidationAC()).isEqualTo(UPDATED_FICHIER_MISE_VALIDATION_AC);
        assertThat(testPlanPassation.getFichierMiseValidationACContentType()).isEqualTo(UPDATED_FICHIER_MISE_VALIDATION_AC_CONTENT_TYPE);
        assertThat(testPlanPassation.getDateMiseEnValidationCcmp()).isEqualTo(UPDATED_DATE_MISE_EN_VALIDATION_CCMP);
        assertThat(testPlanPassation.getFichierMiseValidationCcmp()).isEqualTo(UPDATED_FICHIER_MISE_VALIDATION_CCMP);
        assertThat(testPlanPassation.getFichierMiseValidationCcmpContentType())
            .isEqualTo(UPDATED_FICHIER_MISE_VALIDATION_CCMP_CONTENT_TYPE);
        assertThat(testPlanPassation.getReferenceMiseValidationCcmp()).isEqualTo(UPDATED_REFERENCE_MISE_VALIDATION_CCMP);
        assertThat(testPlanPassation.getDateValidation1()).isEqualTo(UPDATED_DATE_VALIDATION_1);
        assertThat(testPlanPassation.getCommentaireValidation()).isEqualTo(UPDATED_COMMENTAIRE_VALIDATION);
        assertThat(testPlanPassation.getReferenceValidation()).isEqualTo(UPDATED_REFERENCE_VALIDATION);
        assertThat(testPlanPassation.getFichierValidation()).isEqualTo(UPDATED_FICHIER_VALIDATION);
        assertThat(testPlanPassation.getFichierValidationContentType()).isEqualTo(UPDATED_FICHIER_VALIDATION_CONTENT_TYPE);
        assertThat(testPlanPassation.getDateValidation2()).isEqualTo(UPDATED_DATE_VALIDATION_2);
        assertThat(testPlanPassation.getDateRejet()).isEqualTo(UPDATED_DATE_REJET);
        assertThat(testPlanPassation.getDatePublication()).isEqualTo(UPDATED_DATE_PUBLICATION);
        assertThat(testPlanPassation.getCommentairePublication()).isEqualTo(UPDATED_COMMENTAIRE_PUBLICATION);
    }

    @Test
    void putNonExistingPlanPassation() throws Exception {
        int databaseSizeBeforeUpdate = planPassationRepository.findAll().collectList().block().size();
        planPassation.setId(count.incrementAndGet());

        // Create the PlanPassation
        PlanPassationDTO planPassationDTO = planPassationMapper.toDto(planPassation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, planPassationDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(planPassationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PlanPassation in the database
        List<PlanPassation> planPassationList = planPassationRepository.findAll().collectList().block();
        assertThat(planPassationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPlanPassation() throws Exception {
        int databaseSizeBeforeUpdate = planPassationRepository.findAll().collectList().block().size();
        planPassation.setId(count.incrementAndGet());

        // Create the PlanPassation
        PlanPassationDTO planPassationDTO = planPassationMapper.toDto(planPassation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(planPassationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PlanPassation in the database
        List<PlanPassation> planPassationList = planPassationRepository.findAll().collectList().block();
        assertThat(planPassationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPlanPassation() throws Exception {
        int databaseSizeBeforeUpdate = planPassationRepository.findAll().collectList().block().size();
        planPassation.setId(count.incrementAndGet());

        // Create the PlanPassation
        PlanPassationDTO planPassationDTO = planPassationMapper.toDto(planPassation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(planPassationDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the PlanPassation in the database
        List<PlanPassation> planPassationList = planPassationRepository.findAll().collectList().block();
        assertThat(planPassationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePlanPassationWithPatch() throws Exception {
        // Initialize the database
        planPassationRepository.save(planPassation).block();

        int databaseSizeBeforeUpdate = planPassationRepository.findAll().collectList().block().size();

        // Update the planPassation using partial update
        PlanPassation partialUpdatedPlanPassation = new PlanPassation();
        partialUpdatedPlanPassation.setId(planPassation.getId());

        partialUpdatedPlanPassation
            .statut(UPDATED_STATUT)
            .dateDebut(UPDATED_DATE_DEBUT)
            .commentaire(UPDATED_COMMENTAIRE)
            .annee(UPDATED_ANNEE)
            .dateMiseValidation(UPDATED_DATE_MISE_VALIDATION)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .referenceMiseValidationAC(UPDATED_REFERENCE_MISE_VALIDATION_AC)
            .fichierMiseValidationAC(UPDATED_FICHIER_MISE_VALIDATION_AC)
            .fichierMiseValidationACContentType(UPDATED_FICHIER_MISE_VALIDATION_AC_CONTENT_TYPE)
            .fichierMiseValidationCcmp(UPDATED_FICHIER_MISE_VALIDATION_CCMP)
            .fichierMiseValidationCcmpContentType(UPDATED_FICHIER_MISE_VALIDATION_CCMP_CONTENT_TYPE)
            .dateValidation2(UPDATED_DATE_VALIDATION_2)
            .dateRejet(UPDATED_DATE_REJET)
            .datePublication(UPDATED_DATE_PUBLICATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPlanPassation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanPassation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PlanPassation in the database
        List<PlanPassation> planPassationList = planPassationRepository.findAll().collectList().block();
        assertThat(planPassationList).hasSize(databaseSizeBeforeUpdate);
        PlanPassation testPlanPassation = planPassationList.get(planPassationList.size() - 1);
        assertThat(testPlanPassation.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testPlanPassation.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testPlanPassation.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testPlanPassation.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testPlanPassation.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testPlanPassation.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testPlanPassation.getDateMiseValidation()).isEqualTo(UPDATED_DATE_MISE_VALIDATION);
        assertThat(testPlanPassation.getDateValidation()).isEqualTo(UPDATED_DATE_VALIDATION);
        assertThat(testPlanPassation.getCommentaireMiseEnValidationAC()).isEqualTo(DEFAULT_COMMENTAIRE_MISE_EN_VALIDATION_AC);
        assertThat(testPlanPassation.getReferenceMiseValidationAC()).isEqualTo(UPDATED_REFERENCE_MISE_VALIDATION_AC);
        assertThat(testPlanPassation.getFichierMiseValidationAC()).isEqualTo(UPDATED_FICHIER_MISE_VALIDATION_AC);
        assertThat(testPlanPassation.getFichierMiseValidationACContentType()).isEqualTo(UPDATED_FICHIER_MISE_VALIDATION_AC_CONTENT_TYPE);
        assertThat(testPlanPassation.getDateMiseEnValidationCcmp()).isEqualTo(DEFAULT_DATE_MISE_EN_VALIDATION_CCMP);
        assertThat(testPlanPassation.getFichierMiseValidationCcmp()).isEqualTo(UPDATED_FICHIER_MISE_VALIDATION_CCMP);
        assertThat(testPlanPassation.getFichierMiseValidationCcmpContentType())
            .isEqualTo(UPDATED_FICHIER_MISE_VALIDATION_CCMP_CONTENT_TYPE);
        assertThat(testPlanPassation.getReferenceMiseValidationCcmp()).isEqualTo(DEFAULT_REFERENCE_MISE_VALIDATION_CCMP);
        assertThat(testPlanPassation.getDateValidation1()).isEqualTo(DEFAULT_DATE_VALIDATION_1);
        assertThat(testPlanPassation.getCommentaireValidation()).isEqualTo(DEFAULT_COMMENTAIRE_VALIDATION);
        assertThat(testPlanPassation.getReferenceValidation()).isEqualTo(DEFAULT_REFERENCE_VALIDATION);
        assertThat(testPlanPassation.getFichierValidation()).isEqualTo(DEFAULT_FICHIER_VALIDATION);
        assertThat(testPlanPassation.getFichierValidationContentType()).isEqualTo(DEFAULT_FICHIER_VALIDATION_CONTENT_TYPE);
        assertThat(testPlanPassation.getDateValidation2()).isEqualTo(UPDATED_DATE_VALIDATION_2);
        assertThat(testPlanPassation.getDateRejet()).isEqualTo(UPDATED_DATE_REJET);
        assertThat(testPlanPassation.getDatePublication()).isEqualTo(UPDATED_DATE_PUBLICATION);
        assertThat(testPlanPassation.getCommentairePublication()).isEqualTo(DEFAULT_COMMENTAIRE_PUBLICATION);
    }

    @Test
    void fullUpdatePlanPassationWithPatch() throws Exception {
        // Initialize the database
        planPassationRepository.save(planPassation).block();

        int databaseSizeBeforeUpdate = planPassationRepository.findAll().collectList().block().size();

        // Update the planPassation using partial update
        PlanPassation partialUpdatedPlanPassation = new PlanPassation();
        partialUpdatedPlanPassation.setId(planPassation.getId());

        partialUpdatedPlanPassation
            .statut(UPDATED_STATUT)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .commentaire(UPDATED_COMMENTAIRE)
            .annee(UPDATED_ANNEE)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateMiseValidation(UPDATED_DATE_MISE_VALIDATION)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .commentaireMiseEnValidationAC(UPDATED_COMMENTAIRE_MISE_EN_VALIDATION_AC)
            .referenceMiseValidationAC(UPDATED_REFERENCE_MISE_VALIDATION_AC)
            .fichierMiseValidationAC(UPDATED_FICHIER_MISE_VALIDATION_AC)
            .fichierMiseValidationACContentType(UPDATED_FICHIER_MISE_VALIDATION_AC_CONTENT_TYPE)
            .dateMiseEnValidationCcmp(UPDATED_DATE_MISE_EN_VALIDATION_CCMP)
            .fichierMiseValidationCcmp(UPDATED_FICHIER_MISE_VALIDATION_CCMP)
            .fichierMiseValidationCcmpContentType(UPDATED_FICHIER_MISE_VALIDATION_CCMP_CONTENT_TYPE)
            .referenceMiseValidationCcmp(UPDATED_REFERENCE_MISE_VALIDATION_CCMP)
            .dateValidation1(UPDATED_DATE_VALIDATION_1)
            .commentaireValidation(UPDATED_COMMENTAIRE_VALIDATION)
            .referenceValidation(UPDATED_REFERENCE_VALIDATION)
            .fichierValidation(UPDATED_FICHIER_VALIDATION)
            .fichierValidationContentType(UPDATED_FICHIER_VALIDATION_CONTENT_TYPE)
            .dateValidation2(UPDATED_DATE_VALIDATION_2)
            .dateRejet(UPDATED_DATE_REJET)
            .datePublication(UPDATED_DATE_PUBLICATION)
            .commentairePublication(UPDATED_COMMENTAIRE_PUBLICATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPlanPassation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanPassation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PlanPassation in the database
        List<PlanPassation> planPassationList = planPassationRepository.findAll().collectList().block();
        assertThat(planPassationList).hasSize(databaseSizeBeforeUpdate);
        PlanPassation testPlanPassation = planPassationList.get(planPassationList.size() - 1);
        assertThat(testPlanPassation.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testPlanPassation.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testPlanPassation.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testPlanPassation.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testPlanPassation.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testPlanPassation.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testPlanPassation.getDateMiseValidation()).isEqualTo(UPDATED_DATE_MISE_VALIDATION);
        assertThat(testPlanPassation.getDateValidation()).isEqualTo(UPDATED_DATE_VALIDATION);
        assertThat(testPlanPassation.getCommentaireMiseEnValidationAC()).isEqualTo(UPDATED_COMMENTAIRE_MISE_EN_VALIDATION_AC);
        assertThat(testPlanPassation.getReferenceMiseValidationAC()).isEqualTo(UPDATED_REFERENCE_MISE_VALIDATION_AC);
        assertThat(testPlanPassation.getFichierMiseValidationAC()).isEqualTo(UPDATED_FICHIER_MISE_VALIDATION_AC);
        assertThat(testPlanPassation.getFichierMiseValidationACContentType()).isEqualTo(UPDATED_FICHIER_MISE_VALIDATION_AC_CONTENT_TYPE);
        assertThat(testPlanPassation.getDateMiseEnValidationCcmp()).isEqualTo(UPDATED_DATE_MISE_EN_VALIDATION_CCMP);
        assertThat(testPlanPassation.getFichierMiseValidationCcmp()).isEqualTo(UPDATED_FICHIER_MISE_VALIDATION_CCMP);
        assertThat(testPlanPassation.getFichierMiseValidationCcmpContentType())
            .isEqualTo(UPDATED_FICHIER_MISE_VALIDATION_CCMP_CONTENT_TYPE);
        assertThat(testPlanPassation.getReferenceMiseValidationCcmp()).isEqualTo(UPDATED_REFERENCE_MISE_VALIDATION_CCMP);
        assertThat(testPlanPassation.getDateValidation1()).isEqualTo(UPDATED_DATE_VALIDATION_1);
        assertThat(testPlanPassation.getCommentaireValidation()).isEqualTo(UPDATED_COMMENTAIRE_VALIDATION);
        assertThat(testPlanPassation.getReferenceValidation()).isEqualTo(UPDATED_REFERENCE_VALIDATION);
        assertThat(testPlanPassation.getFichierValidation()).isEqualTo(UPDATED_FICHIER_VALIDATION);
        assertThat(testPlanPassation.getFichierValidationContentType()).isEqualTo(UPDATED_FICHIER_VALIDATION_CONTENT_TYPE);
        assertThat(testPlanPassation.getDateValidation2()).isEqualTo(UPDATED_DATE_VALIDATION_2);
        assertThat(testPlanPassation.getDateRejet()).isEqualTo(UPDATED_DATE_REJET);
        assertThat(testPlanPassation.getDatePublication()).isEqualTo(UPDATED_DATE_PUBLICATION);
        assertThat(testPlanPassation.getCommentairePublication()).isEqualTo(UPDATED_COMMENTAIRE_PUBLICATION);
    }

    @Test
    void patchNonExistingPlanPassation() throws Exception {
        int databaseSizeBeforeUpdate = planPassationRepository.findAll().collectList().block().size();
        planPassation.setId(count.incrementAndGet());

        // Create the PlanPassation
        PlanPassationDTO planPassationDTO = planPassationMapper.toDto(planPassation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, planPassationDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(planPassationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PlanPassation in the database
        List<PlanPassation> planPassationList = planPassationRepository.findAll().collectList().block();
        assertThat(planPassationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPlanPassation() throws Exception {
        int databaseSizeBeforeUpdate = planPassationRepository.findAll().collectList().block().size();
        planPassation.setId(count.incrementAndGet());

        // Create the PlanPassation
        PlanPassationDTO planPassationDTO = planPassationMapper.toDto(planPassation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(planPassationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PlanPassation in the database
        List<PlanPassation> planPassationList = planPassationRepository.findAll().collectList().block();
        assertThat(planPassationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPlanPassation() throws Exception {
        int databaseSizeBeforeUpdate = planPassationRepository.findAll().collectList().block().size();
        planPassation.setId(count.incrementAndGet());

        // Create the PlanPassation
        PlanPassationDTO planPassationDTO = planPassationMapper.toDto(planPassation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(planPassationDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the PlanPassation in the database
        List<PlanPassation> planPassationList = planPassationRepository.findAll().collectList().block();
        assertThat(planPassationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePlanPassation() {
        // Initialize the database
        planPassationRepository.save(planPassation).block();

        int databaseSizeBeforeDelete = planPassationRepository.findAll().collectList().block().size();

        // Delete the planPassation
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, planPassation.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<PlanPassation> planPassationList = planPassationRepository.findAll().collectList().block();
        assertThat(planPassationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
