package sn.ssi.sigmap.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static sn.ssi.sigmap.web.rest.TestUtil.sameNumber;

import java.math.BigDecimal;
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
import sn.ssi.sigmap.domain.PlanPassation;
import sn.ssi.sigmap.domain.Realisation;
import sn.ssi.sigmap.repository.EntityManager;
import sn.ssi.sigmap.repository.RealisationRepository;
import sn.ssi.sigmap.service.dto.RealisationDTO;
import sn.ssi.sigmap.service.mapper.RealisationMapper;

/**
 * Integration tests for the {@link RealisationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class RealisationResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ATTRIBUTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ATTRIBUTION = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DELAIEXECUTION = 1;
    private static final Integer UPDATED_DELAIEXECUTION = 2;

    private static final String DEFAULT_OBJET = "AAAAAAAAAA";
    private static final String UPDATED_OBJET = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MONTANT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT = new BigDecimal(2);

    private static final Integer DEFAULT_EXAMEN_DNCMP = 1;
    private static final Integer UPDATED_EXAMEN_DNCMP = 2;

    private static final Integer DEFAULT_EXAMEN_PTF = 1;
    private static final Integer UPDATED_EXAMEN_PTF = 2;

    private static final String DEFAULT_CHAPITRE_IMPUTATION = "AAAAAAAAAA";
    private static final String UPDATED_CHAPITRE_IMPUTATION = "BBBBBBBBBB";

    private static final String DEFAULT_AUTORISATION_ENGAGEMENT = "AAAAAAAAAA";
    private static final String UPDATED_AUTORISATION_ENGAGEMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_RECEPTION_DOSSIER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RECEPTION_DOSSIER = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_NON_OBJECTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NON_OBJECTION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_AUTORISATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_AUTORISATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_RECEP_NON_OBJECTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RECEP_NON_OBJECTION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_RECEP_DOSS_CORRIGE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RECEP_DOSS_CORRIGE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_PUB_PAR_PRMP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PUB_PAR_PRMP = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_OUVERTURE_PLIS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OUVERTURE_PLIS = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_RECEP_NON_OBJECT_OCMP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RECEP_NON_OBJECT_OCMP = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_RECEP_RAPPORT_EVA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RECEP_RAPPORT_EVA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_RECEP_NON_OBJECT_PTF = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RECEP_NON_OBJECT_PTF = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_EXAMEN_JURIDIQUE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EXAMEN_JURIDIQUE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_NOTIF_CONTRAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NOTIF_CONTRAT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_APPROBATION_CONTRAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_APPROBATION_CONTRAT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/realisations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RealisationRepository realisationRepository;

    @Autowired
    private RealisationMapper realisationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Realisation realisation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Realisation createEntity(EntityManager em) {
        Realisation realisation = new Realisation()
            .libelle(DEFAULT_LIBELLE)
            .dateAttribution(DEFAULT_DATE_ATTRIBUTION)
            .delaiexecution(DEFAULT_DELAIEXECUTION)
            .objet(DEFAULT_OBJET)
            .montant(DEFAULT_MONTANT)
            .examenDncmp(DEFAULT_EXAMEN_DNCMP)
            .examenPtf(DEFAULT_EXAMEN_PTF)
            .chapitreImputation(DEFAULT_CHAPITRE_IMPUTATION)
            .autorisationEngagement(DEFAULT_AUTORISATION_ENGAGEMENT)
            .dateReceptionDossier(DEFAULT_DATE_RECEPTION_DOSSIER)
            .dateNonObjection(DEFAULT_DATE_NON_OBJECTION)
            .dateAutorisation(DEFAULT_DATE_AUTORISATION)
            .dateRecepNonObjection(DEFAULT_DATE_RECEP_NON_OBJECTION)
            .dateRecepDossCorrige(DEFAULT_DATE_RECEP_DOSS_CORRIGE)
            .datePubParPrmp(DEFAULT_DATE_PUB_PAR_PRMP)
            .dateOuverturePlis(DEFAULT_DATE_OUVERTURE_PLIS)
            .dateRecepNonObjectOcmp(DEFAULT_DATE_RECEP_NON_OBJECT_OCMP)
            .dateRecepRapportEva(DEFAULT_DATE_RECEP_RAPPORT_EVA)
            .dateRecepNonObjectPtf(DEFAULT_DATE_RECEP_NON_OBJECT_PTF)
            .dateExamenJuridique(DEFAULT_DATE_EXAMEN_JURIDIQUE)
            .dateNotifContrat(DEFAULT_DATE_NOTIF_CONTRAT)
            .dateApprobationContrat(DEFAULT_DATE_APPROBATION_CONTRAT);
        // Add required entity
        PlanPassation planPassation;
        planPassation = em.insert(PlanPassationResourceIT.createEntity(em)).block();
        realisation.setPlanPassation(planPassation);
        return realisation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Realisation createUpdatedEntity(EntityManager em) {
        Realisation realisation = new Realisation()
            .libelle(UPDATED_LIBELLE)
            .dateAttribution(UPDATED_DATE_ATTRIBUTION)
            .delaiexecution(UPDATED_DELAIEXECUTION)
            .objet(UPDATED_OBJET)
            .montant(UPDATED_MONTANT)
            .examenDncmp(UPDATED_EXAMEN_DNCMP)
            .examenPtf(UPDATED_EXAMEN_PTF)
            .chapitreImputation(UPDATED_CHAPITRE_IMPUTATION)
            .autorisationEngagement(UPDATED_AUTORISATION_ENGAGEMENT)
            .dateReceptionDossier(UPDATED_DATE_RECEPTION_DOSSIER)
            .dateNonObjection(UPDATED_DATE_NON_OBJECTION)
            .dateAutorisation(UPDATED_DATE_AUTORISATION)
            .dateRecepNonObjection(UPDATED_DATE_RECEP_NON_OBJECTION)
            .dateRecepDossCorrige(UPDATED_DATE_RECEP_DOSS_CORRIGE)
            .datePubParPrmp(UPDATED_DATE_PUB_PAR_PRMP)
            .dateOuverturePlis(UPDATED_DATE_OUVERTURE_PLIS)
            .dateRecepNonObjectOcmp(UPDATED_DATE_RECEP_NON_OBJECT_OCMP)
            .dateRecepRapportEva(UPDATED_DATE_RECEP_RAPPORT_EVA)
            .dateRecepNonObjectPtf(UPDATED_DATE_RECEP_NON_OBJECT_PTF)
            .dateExamenJuridique(UPDATED_DATE_EXAMEN_JURIDIQUE)
            .dateNotifContrat(UPDATED_DATE_NOTIF_CONTRAT)
            .dateApprobationContrat(UPDATED_DATE_APPROBATION_CONTRAT);
        // Add required entity
        PlanPassation planPassation;
        planPassation = em.insert(PlanPassationResourceIT.createUpdatedEntity(em)).block();
        realisation.setPlanPassation(planPassation);
        return realisation;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Realisation.class).block();
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
        realisation = createEntity(em);
    }

    @Test
    void createRealisation() throws Exception {
        int databaseSizeBeforeCreate = realisationRepository.findAll().collectList().block().size();
        // Create the Realisation
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Realisation in the database
        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeCreate + 1);
        Realisation testRealisation = realisationList.get(realisationList.size() - 1);
        assertThat(testRealisation.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testRealisation.getDateAttribution()).isEqualTo(DEFAULT_DATE_ATTRIBUTION);
        assertThat(testRealisation.getDelaiexecution()).isEqualTo(DEFAULT_DELAIEXECUTION);
        assertThat(testRealisation.getObjet()).isEqualTo(DEFAULT_OBJET);
        assertThat(testRealisation.getMontant()).isEqualByComparingTo(DEFAULT_MONTANT);
        assertThat(testRealisation.getExamenDncmp()).isEqualTo(DEFAULT_EXAMEN_DNCMP);
        assertThat(testRealisation.getExamenPtf()).isEqualTo(DEFAULT_EXAMEN_PTF);
        assertThat(testRealisation.getChapitreImputation()).isEqualTo(DEFAULT_CHAPITRE_IMPUTATION);
        assertThat(testRealisation.getAutorisationEngagement()).isEqualTo(DEFAULT_AUTORISATION_ENGAGEMENT);
        assertThat(testRealisation.getDateReceptionDossier()).isEqualTo(DEFAULT_DATE_RECEPTION_DOSSIER);
        assertThat(testRealisation.getDateNonObjection()).isEqualTo(DEFAULT_DATE_NON_OBJECTION);
        assertThat(testRealisation.getDateAutorisation()).isEqualTo(DEFAULT_DATE_AUTORISATION);
        assertThat(testRealisation.getDateRecepNonObjection()).isEqualTo(DEFAULT_DATE_RECEP_NON_OBJECTION);
        assertThat(testRealisation.getDateRecepDossCorrige()).isEqualTo(DEFAULT_DATE_RECEP_DOSS_CORRIGE);
        assertThat(testRealisation.getDatePubParPrmp()).isEqualTo(DEFAULT_DATE_PUB_PAR_PRMP);
        assertThat(testRealisation.getDateOuverturePlis()).isEqualTo(DEFAULT_DATE_OUVERTURE_PLIS);
        assertThat(testRealisation.getDateRecepNonObjectOcmp()).isEqualTo(DEFAULT_DATE_RECEP_NON_OBJECT_OCMP);
        assertThat(testRealisation.getDateRecepRapportEva()).isEqualTo(DEFAULT_DATE_RECEP_RAPPORT_EVA);
        assertThat(testRealisation.getDateRecepNonObjectPtf()).isEqualTo(DEFAULT_DATE_RECEP_NON_OBJECT_PTF);
        assertThat(testRealisation.getDateExamenJuridique()).isEqualTo(DEFAULT_DATE_EXAMEN_JURIDIQUE);
        assertThat(testRealisation.getDateNotifContrat()).isEqualTo(DEFAULT_DATE_NOTIF_CONTRAT);
        assertThat(testRealisation.getDateApprobationContrat()).isEqualTo(DEFAULT_DATE_APPROBATION_CONTRAT);
    }

    @Test
    void createRealisationWithExistingId() throws Exception {
        // Create the Realisation with an existing ID
        realisation.setId(1L);
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        int databaseSizeBeforeCreate = realisationRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Realisation in the database
        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkDateAttributionIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setDateAttribution(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDelaiexecutionIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setDelaiexecution(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkExamenDncmpIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setExamenDncmp(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkExamenPtfIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setExamenPtf(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkChapitreImputationIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setChapitreImputation(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAutorisationEngagementIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setAutorisationEngagement(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDateReceptionDossierIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setDateReceptionDossier(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDateNonObjectionIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setDateNonObjection(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDateAutorisationIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setDateAutorisation(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDateRecepNonObjectionIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setDateRecepNonObjection(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDateRecepDossCorrigeIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setDateRecepDossCorrige(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDatePubParPrmpIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setDatePubParPrmp(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDateOuverturePlisIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setDateOuverturePlis(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDateRecepNonObjectOcmpIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setDateRecepNonObjectOcmp(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDateRecepRapportEvaIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setDateRecepRapportEva(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDateRecepNonObjectPtfIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setDateRecepNonObjectPtf(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDateExamenJuridiqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setDateExamenJuridique(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDateNotifContratIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setDateNotifContrat(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDateApprobationContratIsRequired() throws Exception {
        int databaseSizeBeforeTest = realisationRepository.findAll().collectList().block().size();
        // set the field null
        realisation.setDateApprobationContrat(null);

        // Create the Realisation, which fails.
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllRealisations() {
        // Initialize the database
        realisationRepository.save(realisation).block();

        // Get all the realisationList
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
            .value(hasItem(realisation.getId().intValue()))
            .jsonPath("$.[*].libelle")
            .value(hasItem(DEFAULT_LIBELLE))
            .jsonPath("$.[*].dateAttribution")
            .value(hasItem(DEFAULT_DATE_ATTRIBUTION.toString()))
            .jsonPath("$.[*].delaiexecution")
            .value(hasItem(DEFAULT_DELAIEXECUTION))
            .jsonPath("$.[*].objet")
            .value(hasItem(DEFAULT_OBJET))
            .jsonPath("$.[*].montant")
            .value(hasItem(sameNumber(DEFAULT_MONTANT)))
            .jsonPath("$.[*].examenDncmp")
            .value(hasItem(DEFAULT_EXAMEN_DNCMP))
            .jsonPath("$.[*].examenPtf")
            .value(hasItem(DEFAULT_EXAMEN_PTF))
            .jsonPath("$.[*].chapitreImputation")
            .value(hasItem(DEFAULT_CHAPITRE_IMPUTATION))
            .jsonPath("$.[*].autorisationEngagement")
            .value(hasItem(DEFAULT_AUTORISATION_ENGAGEMENT))
            .jsonPath("$.[*].dateReceptionDossier")
            .value(hasItem(DEFAULT_DATE_RECEPTION_DOSSIER.toString()))
            .jsonPath("$.[*].dateNonObjection")
            .value(hasItem(DEFAULT_DATE_NON_OBJECTION.toString()))
            .jsonPath("$.[*].dateAutorisation")
            .value(hasItem(DEFAULT_DATE_AUTORISATION.toString()))
            .jsonPath("$.[*].dateRecepNonObjection")
            .value(hasItem(DEFAULT_DATE_RECEP_NON_OBJECTION.toString()))
            .jsonPath("$.[*].dateRecepDossCorrige")
            .value(hasItem(DEFAULT_DATE_RECEP_DOSS_CORRIGE.toString()))
            .jsonPath("$.[*].datePubParPrmp")
            .value(hasItem(DEFAULT_DATE_PUB_PAR_PRMP.toString()))
            .jsonPath("$.[*].dateOuverturePlis")
            .value(hasItem(DEFAULT_DATE_OUVERTURE_PLIS.toString()))
            .jsonPath("$.[*].dateRecepNonObjectOcmp")
            .value(hasItem(DEFAULT_DATE_RECEP_NON_OBJECT_OCMP.toString()))
            .jsonPath("$.[*].dateRecepRapportEva")
            .value(hasItem(DEFAULT_DATE_RECEP_RAPPORT_EVA.toString()))
            .jsonPath("$.[*].dateRecepNonObjectPtf")
            .value(hasItem(DEFAULT_DATE_RECEP_NON_OBJECT_PTF.toString()))
            .jsonPath("$.[*].dateExamenJuridique")
            .value(hasItem(DEFAULT_DATE_EXAMEN_JURIDIQUE.toString()))
            .jsonPath("$.[*].dateNotifContrat")
            .value(hasItem(DEFAULT_DATE_NOTIF_CONTRAT.toString()))
            .jsonPath("$.[*].dateApprobationContrat")
            .value(hasItem(DEFAULT_DATE_APPROBATION_CONTRAT.toString()));
    }

    @Test
    void getRealisation() {
        // Initialize the database
        realisationRepository.save(realisation).block();

        // Get the realisation
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, realisation.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(realisation.getId().intValue()))
            .jsonPath("$.libelle")
            .value(is(DEFAULT_LIBELLE))
            .jsonPath("$.dateAttribution")
            .value(is(DEFAULT_DATE_ATTRIBUTION.toString()))
            .jsonPath("$.delaiexecution")
            .value(is(DEFAULT_DELAIEXECUTION))
            .jsonPath("$.objet")
            .value(is(DEFAULT_OBJET))
            .jsonPath("$.montant")
            .value(is(sameNumber(DEFAULT_MONTANT)))
            .jsonPath("$.examenDncmp")
            .value(is(DEFAULT_EXAMEN_DNCMP))
            .jsonPath("$.examenPtf")
            .value(is(DEFAULT_EXAMEN_PTF))
            .jsonPath("$.chapitreImputation")
            .value(is(DEFAULT_CHAPITRE_IMPUTATION))
            .jsonPath("$.autorisationEngagement")
            .value(is(DEFAULT_AUTORISATION_ENGAGEMENT))
            .jsonPath("$.dateReceptionDossier")
            .value(is(DEFAULT_DATE_RECEPTION_DOSSIER.toString()))
            .jsonPath("$.dateNonObjection")
            .value(is(DEFAULT_DATE_NON_OBJECTION.toString()))
            .jsonPath("$.dateAutorisation")
            .value(is(DEFAULT_DATE_AUTORISATION.toString()))
            .jsonPath("$.dateRecepNonObjection")
            .value(is(DEFAULT_DATE_RECEP_NON_OBJECTION.toString()))
            .jsonPath("$.dateRecepDossCorrige")
            .value(is(DEFAULT_DATE_RECEP_DOSS_CORRIGE.toString()))
            .jsonPath("$.datePubParPrmp")
            .value(is(DEFAULT_DATE_PUB_PAR_PRMP.toString()))
            .jsonPath("$.dateOuverturePlis")
            .value(is(DEFAULT_DATE_OUVERTURE_PLIS.toString()))
            .jsonPath("$.dateRecepNonObjectOcmp")
            .value(is(DEFAULT_DATE_RECEP_NON_OBJECT_OCMP.toString()))
            .jsonPath("$.dateRecepRapportEva")
            .value(is(DEFAULT_DATE_RECEP_RAPPORT_EVA.toString()))
            .jsonPath("$.dateRecepNonObjectPtf")
            .value(is(DEFAULT_DATE_RECEP_NON_OBJECT_PTF.toString()))
            .jsonPath("$.dateExamenJuridique")
            .value(is(DEFAULT_DATE_EXAMEN_JURIDIQUE.toString()))
            .jsonPath("$.dateNotifContrat")
            .value(is(DEFAULT_DATE_NOTIF_CONTRAT.toString()))
            .jsonPath("$.dateApprobationContrat")
            .value(is(DEFAULT_DATE_APPROBATION_CONTRAT.toString()));
    }

    @Test
    void getNonExistingRealisation() {
        // Get the realisation
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingRealisation() throws Exception {
        // Initialize the database
        realisationRepository.save(realisation).block();

        int databaseSizeBeforeUpdate = realisationRepository.findAll().collectList().block().size();

        // Update the realisation
        Realisation updatedRealisation = realisationRepository.findById(realisation.getId()).block();
        updatedRealisation
            .libelle(UPDATED_LIBELLE)
            .dateAttribution(UPDATED_DATE_ATTRIBUTION)
            .delaiexecution(UPDATED_DELAIEXECUTION)
            .objet(UPDATED_OBJET)
            .montant(UPDATED_MONTANT)
            .examenDncmp(UPDATED_EXAMEN_DNCMP)
            .examenPtf(UPDATED_EXAMEN_PTF)
            .chapitreImputation(UPDATED_CHAPITRE_IMPUTATION)
            .autorisationEngagement(UPDATED_AUTORISATION_ENGAGEMENT)
            .dateReceptionDossier(UPDATED_DATE_RECEPTION_DOSSIER)
            .dateNonObjection(UPDATED_DATE_NON_OBJECTION)
            .dateAutorisation(UPDATED_DATE_AUTORISATION)
            .dateRecepNonObjection(UPDATED_DATE_RECEP_NON_OBJECTION)
            .dateRecepDossCorrige(UPDATED_DATE_RECEP_DOSS_CORRIGE)
            .datePubParPrmp(UPDATED_DATE_PUB_PAR_PRMP)
            .dateOuverturePlis(UPDATED_DATE_OUVERTURE_PLIS)
            .dateRecepNonObjectOcmp(UPDATED_DATE_RECEP_NON_OBJECT_OCMP)
            .dateRecepRapportEva(UPDATED_DATE_RECEP_RAPPORT_EVA)
            .dateRecepNonObjectPtf(UPDATED_DATE_RECEP_NON_OBJECT_PTF)
            .dateExamenJuridique(UPDATED_DATE_EXAMEN_JURIDIQUE)
            .dateNotifContrat(UPDATED_DATE_NOTIF_CONTRAT)
            .dateApprobationContrat(UPDATED_DATE_APPROBATION_CONTRAT);
        RealisationDTO realisationDTO = realisationMapper.toDto(updatedRealisation);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, realisationDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Realisation in the database
        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeUpdate);
        Realisation testRealisation = realisationList.get(realisationList.size() - 1);
        assertThat(testRealisation.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testRealisation.getDateAttribution()).isEqualTo(UPDATED_DATE_ATTRIBUTION);
        assertThat(testRealisation.getDelaiexecution()).isEqualTo(UPDATED_DELAIEXECUTION);
        assertThat(testRealisation.getObjet()).isEqualTo(UPDATED_OBJET);
        assertThat(testRealisation.getMontant()).isEqualByComparingTo(UPDATED_MONTANT);
        assertThat(testRealisation.getExamenDncmp()).isEqualTo(UPDATED_EXAMEN_DNCMP);
        assertThat(testRealisation.getExamenPtf()).isEqualTo(UPDATED_EXAMEN_PTF);
        assertThat(testRealisation.getChapitreImputation()).isEqualTo(UPDATED_CHAPITRE_IMPUTATION);
        assertThat(testRealisation.getAutorisationEngagement()).isEqualTo(UPDATED_AUTORISATION_ENGAGEMENT);
        assertThat(testRealisation.getDateReceptionDossier()).isEqualTo(UPDATED_DATE_RECEPTION_DOSSIER);
        assertThat(testRealisation.getDateNonObjection()).isEqualTo(UPDATED_DATE_NON_OBJECTION);
        assertThat(testRealisation.getDateAutorisation()).isEqualTo(UPDATED_DATE_AUTORISATION);
        assertThat(testRealisation.getDateRecepNonObjection()).isEqualTo(UPDATED_DATE_RECEP_NON_OBJECTION);
        assertThat(testRealisation.getDateRecepDossCorrige()).isEqualTo(UPDATED_DATE_RECEP_DOSS_CORRIGE);
        assertThat(testRealisation.getDatePubParPrmp()).isEqualTo(UPDATED_DATE_PUB_PAR_PRMP);
        assertThat(testRealisation.getDateOuverturePlis()).isEqualTo(UPDATED_DATE_OUVERTURE_PLIS);
        assertThat(testRealisation.getDateRecepNonObjectOcmp()).isEqualTo(UPDATED_DATE_RECEP_NON_OBJECT_OCMP);
        assertThat(testRealisation.getDateRecepRapportEva()).isEqualTo(UPDATED_DATE_RECEP_RAPPORT_EVA);
        assertThat(testRealisation.getDateRecepNonObjectPtf()).isEqualTo(UPDATED_DATE_RECEP_NON_OBJECT_PTF);
        assertThat(testRealisation.getDateExamenJuridique()).isEqualTo(UPDATED_DATE_EXAMEN_JURIDIQUE);
        assertThat(testRealisation.getDateNotifContrat()).isEqualTo(UPDATED_DATE_NOTIF_CONTRAT);
        assertThat(testRealisation.getDateApprobationContrat()).isEqualTo(UPDATED_DATE_APPROBATION_CONTRAT);
    }

    @Test
    void putNonExistingRealisation() throws Exception {
        int databaseSizeBeforeUpdate = realisationRepository.findAll().collectList().block().size();
        realisation.setId(count.incrementAndGet());

        // Create the Realisation
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, realisationDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Realisation in the database
        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRealisation() throws Exception {
        int databaseSizeBeforeUpdate = realisationRepository.findAll().collectList().block().size();
        realisation.setId(count.incrementAndGet());

        // Create the Realisation
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Realisation in the database
        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRealisation() throws Exception {
        int databaseSizeBeforeUpdate = realisationRepository.findAll().collectList().block().size();
        realisation.setId(count.incrementAndGet());

        // Create the Realisation
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Realisation in the database
        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRealisationWithPatch() throws Exception {
        // Initialize the database
        realisationRepository.save(realisation).block();

        int databaseSizeBeforeUpdate = realisationRepository.findAll().collectList().block().size();

        // Update the realisation using partial update
        Realisation partialUpdatedRealisation = new Realisation();
        partialUpdatedRealisation.setId(realisation.getId());

        partialUpdatedRealisation
            .delaiexecution(UPDATED_DELAIEXECUTION)
            .examenPtf(UPDATED_EXAMEN_PTF)
            .chapitreImputation(UPDATED_CHAPITRE_IMPUTATION)
            .autorisationEngagement(UPDATED_AUTORISATION_ENGAGEMENT)
            .dateReceptionDossier(UPDATED_DATE_RECEPTION_DOSSIER)
            .dateRecepDossCorrige(UPDATED_DATE_RECEP_DOSS_CORRIGE)
            .datePubParPrmp(UPDATED_DATE_PUB_PAR_PRMP)
            .dateOuverturePlis(UPDATED_DATE_OUVERTURE_PLIS)
            .dateExamenJuridique(UPDATED_DATE_EXAMEN_JURIDIQUE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRealisation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRealisation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Realisation in the database
        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeUpdate);
        Realisation testRealisation = realisationList.get(realisationList.size() - 1);
        assertThat(testRealisation.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testRealisation.getDateAttribution()).isEqualTo(DEFAULT_DATE_ATTRIBUTION);
        assertThat(testRealisation.getDelaiexecution()).isEqualTo(UPDATED_DELAIEXECUTION);
        assertThat(testRealisation.getObjet()).isEqualTo(DEFAULT_OBJET);
        assertThat(testRealisation.getMontant()).isEqualByComparingTo(DEFAULT_MONTANT);
        assertThat(testRealisation.getExamenDncmp()).isEqualTo(DEFAULT_EXAMEN_DNCMP);
        assertThat(testRealisation.getExamenPtf()).isEqualTo(UPDATED_EXAMEN_PTF);
        assertThat(testRealisation.getChapitreImputation()).isEqualTo(UPDATED_CHAPITRE_IMPUTATION);
        assertThat(testRealisation.getAutorisationEngagement()).isEqualTo(UPDATED_AUTORISATION_ENGAGEMENT);
        assertThat(testRealisation.getDateReceptionDossier()).isEqualTo(UPDATED_DATE_RECEPTION_DOSSIER);
        assertThat(testRealisation.getDateNonObjection()).isEqualTo(DEFAULT_DATE_NON_OBJECTION);
        assertThat(testRealisation.getDateAutorisation()).isEqualTo(DEFAULT_DATE_AUTORISATION);
        assertThat(testRealisation.getDateRecepNonObjection()).isEqualTo(DEFAULT_DATE_RECEP_NON_OBJECTION);
        assertThat(testRealisation.getDateRecepDossCorrige()).isEqualTo(UPDATED_DATE_RECEP_DOSS_CORRIGE);
        assertThat(testRealisation.getDatePubParPrmp()).isEqualTo(UPDATED_DATE_PUB_PAR_PRMP);
        assertThat(testRealisation.getDateOuverturePlis()).isEqualTo(UPDATED_DATE_OUVERTURE_PLIS);
        assertThat(testRealisation.getDateRecepNonObjectOcmp()).isEqualTo(DEFAULT_DATE_RECEP_NON_OBJECT_OCMP);
        assertThat(testRealisation.getDateRecepRapportEva()).isEqualTo(DEFAULT_DATE_RECEP_RAPPORT_EVA);
        assertThat(testRealisation.getDateRecepNonObjectPtf()).isEqualTo(DEFAULT_DATE_RECEP_NON_OBJECT_PTF);
        assertThat(testRealisation.getDateExamenJuridique()).isEqualTo(UPDATED_DATE_EXAMEN_JURIDIQUE);
        assertThat(testRealisation.getDateNotifContrat()).isEqualTo(DEFAULT_DATE_NOTIF_CONTRAT);
        assertThat(testRealisation.getDateApprobationContrat()).isEqualTo(DEFAULT_DATE_APPROBATION_CONTRAT);
    }

    @Test
    void fullUpdateRealisationWithPatch() throws Exception {
        // Initialize the database
        realisationRepository.save(realisation).block();

        int databaseSizeBeforeUpdate = realisationRepository.findAll().collectList().block().size();

        // Update the realisation using partial update
        Realisation partialUpdatedRealisation = new Realisation();
        partialUpdatedRealisation.setId(realisation.getId());

        partialUpdatedRealisation
            .libelle(UPDATED_LIBELLE)
            .dateAttribution(UPDATED_DATE_ATTRIBUTION)
            .delaiexecution(UPDATED_DELAIEXECUTION)
            .objet(UPDATED_OBJET)
            .montant(UPDATED_MONTANT)
            .examenDncmp(UPDATED_EXAMEN_DNCMP)
            .examenPtf(UPDATED_EXAMEN_PTF)
            .chapitreImputation(UPDATED_CHAPITRE_IMPUTATION)
            .autorisationEngagement(UPDATED_AUTORISATION_ENGAGEMENT)
            .dateReceptionDossier(UPDATED_DATE_RECEPTION_DOSSIER)
            .dateNonObjection(UPDATED_DATE_NON_OBJECTION)
            .dateAutorisation(UPDATED_DATE_AUTORISATION)
            .dateRecepNonObjection(UPDATED_DATE_RECEP_NON_OBJECTION)
            .dateRecepDossCorrige(UPDATED_DATE_RECEP_DOSS_CORRIGE)
            .datePubParPrmp(UPDATED_DATE_PUB_PAR_PRMP)
            .dateOuverturePlis(UPDATED_DATE_OUVERTURE_PLIS)
            .dateRecepNonObjectOcmp(UPDATED_DATE_RECEP_NON_OBJECT_OCMP)
            .dateRecepRapportEva(UPDATED_DATE_RECEP_RAPPORT_EVA)
            .dateRecepNonObjectPtf(UPDATED_DATE_RECEP_NON_OBJECT_PTF)
            .dateExamenJuridique(UPDATED_DATE_EXAMEN_JURIDIQUE)
            .dateNotifContrat(UPDATED_DATE_NOTIF_CONTRAT)
            .dateApprobationContrat(UPDATED_DATE_APPROBATION_CONTRAT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRealisation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRealisation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Realisation in the database
        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeUpdate);
        Realisation testRealisation = realisationList.get(realisationList.size() - 1);
        assertThat(testRealisation.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testRealisation.getDateAttribution()).isEqualTo(UPDATED_DATE_ATTRIBUTION);
        assertThat(testRealisation.getDelaiexecution()).isEqualTo(UPDATED_DELAIEXECUTION);
        assertThat(testRealisation.getObjet()).isEqualTo(UPDATED_OBJET);
        assertThat(testRealisation.getMontant()).isEqualByComparingTo(UPDATED_MONTANT);
        assertThat(testRealisation.getExamenDncmp()).isEqualTo(UPDATED_EXAMEN_DNCMP);
        assertThat(testRealisation.getExamenPtf()).isEqualTo(UPDATED_EXAMEN_PTF);
        assertThat(testRealisation.getChapitreImputation()).isEqualTo(UPDATED_CHAPITRE_IMPUTATION);
        assertThat(testRealisation.getAutorisationEngagement()).isEqualTo(UPDATED_AUTORISATION_ENGAGEMENT);
        assertThat(testRealisation.getDateReceptionDossier()).isEqualTo(UPDATED_DATE_RECEPTION_DOSSIER);
        assertThat(testRealisation.getDateNonObjection()).isEqualTo(UPDATED_DATE_NON_OBJECTION);
        assertThat(testRealisation.getDateAutorisation()).isEqualTo(UPDATED_DATE_AUTORISATION);
        assertThat(testRealisation.getDateRecepNonObjection()).isEqualTo(UPDATED_DATE_RECEP_NON_OBJECTION);
        assertThat(testRealisation.getDateRecepDossCorrige()).isEqualTo(UPDATED_DATE_RECEP_DOSS_CORRIGE);
        assertThat(testRealisation.getDatePubParPrmp()).isEqualTo(UPDATED_DATE_PUB_PAR_PRMP);
        assertThat(testRealisation.getDateOuverturePlis()).isEqualTo(UPDATED_DATE_OUVERTURE_PLIS);
        assertThat(testRealisation.getDateRecepNonObjectOcmp()).isEqualTo(UPDATED_DATE_RECEP_NON_OBJECT_OCMP);
        assertThat(testRealisation.getDateRecepRapportEva()).isEqualTo(UPDATED_DATE_RECEP_RAPPORT_EVA);
        assertThat(testRealisation.getDateRecepNonObjectPtf()).isEqualTo(UPDATED_DATE_RECEP_NON_OBJECT_PTF);
        assertThat(testRealisation.getDateExamenJuridique()).isEqualTo(UPDATED_DATE_EXAMEN_JURIDIQUE);
        assertThat(testRealisation.getDateNotifContrat()).isEqualTo(UPDATED_DATE_NOTIF_CONTRAT);
        assertThat(testRealisation.getDateApprobationContrat()).isEqualTo(UPDATED_DATE_APPROBATION_CONTRAT);
    }

    @Test
    void patchNonExistingRealisation() throws Exception {
        int databaseSizeBeforeUpdate = realisationRepository.findAll().collectList().block().size();
        realisation.setId(count.incrementAndGet());

        // Create the Realisation
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, realisationDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Realisation in the database
        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRealisation() throws Exception {
        int databaseSizeBeforeUpdate = realisationRepository.findAll().collectList().block().size();
        realisation.setId(count.incrementAndGet());

        // Create the Realisation
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Realisation in the database
        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRealisation() throws Exception {
        int databaseSizeBeforeUpdate = realisationRepository.findAll().collectList().block().size();
        realisation.setId(count.incrementAndGet());

        // Create the Realisation
        RealisationDTO realisationDTO = realisationMapper.toDto(realisation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(realisationDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Realisation in the database
        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRealisation() {
        // Initialize the database
        realisationRepository.save(realisation).block();

        int databaseSizeBeforeDelete = realisationRepository.findAll().collectList().block().size();

        // Delete the realisation
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, realisation.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Realisation> realisationList = realisationRepository.findAll().collectList().block();
        assertThat(realisationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
