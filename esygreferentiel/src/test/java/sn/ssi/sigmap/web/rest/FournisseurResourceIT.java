package sn.ssi.sigmap.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.ssi.sigmap.web.rest.TestUtil.sameInstant;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sn.ssi.sigmap.IntegrationTest;
import sn.ssi.sigmap.domain.Fournisseur;
import sn.ssi.sigmap.repository.FournisseurRepository;

/**
 * Integration tests for the {@link FournisseurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FournisseurResourceIT {

    private static final String DEFAULT_RAISON_SOCIALE = "AAAAAAAAAA";
    private static final String UPDATED_RAISON_SOCIALE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PIECE_JOINTE = "AAAAAAAAAA";
    private static final String UPDATED_PIECE_JOINTE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_REGISTRE_COMMERCE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_REGISTRE_COMMERCE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_SIGLE = "AAAAAAAAAA";
    private static final String UPDATED_SIGLE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_IDENTITE_FISCALE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_IDENTITE_FISCALE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fournisseurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFournisseurMockMvc;

    private Fournisseur fournisseur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fournisseur createEntity(EntityManager em) {
        Fournisseur fournisseur = new Fournisseur()
            .raisonSociale(DEFAULT_RAISON_SOCIALE)
            .adresse(DEFAULT_ADRESSE)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .pieceJointe(DEFAULT_PIECE_JOINTE)
            .numeroRegistreCommerce(DEFAULT_NUMERO_REGISTRE_COMMERCE)
            .date(DEFAULT_DATE)
            .sigle(DEFAULT_SIGLE)
            .numeroIdentiteFiscale(DEFAULT_NUMERO_IDENTITE_FISCALE);
        return fournisseur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fournisseur createUpdatedEntity(EntityManager em) {
        Fournisseur fournisseur = new Fournisseur()
            .raisonSociale(UPDATED_RAISON_SOCIALE)
            .adresse(UPDATED_ADRESSE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .pieceJointe(UPDATED_PIECE_JOINTE)
            .numeroRegistreCommerce(UPDATED_NUMERO_REGISTRE_COMMERCE)
            .date(UPDATED_DATE)
            .sigle(UPDATED_SIGLE)
            .numeroIdentiteFiscale(UPDATED_NUMERO_IDENTITE_FISCALE);
        return fournisseur;
    }

    @BeforeEach
    public void initTest() {
        fournisseur = createEntity(em);
    }

    @Test
    @Transactional
    void createFournisseur() throws Exception {
        int databaseSizeBeforeCreate = fournisseurRepository.findAll().size();
        // Create the Fournisseur
        restFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isCreated());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeCreate + 1);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getRaisonSociale()).isEqualTo(DEFAULT_RAISON_SOCIALE);
        assertThat(testFournisseur.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testFournisseur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFournisseur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testFournisseur.getPieceJointe()).isEqualTo(DEFAULT_PIECE_JOINTE);
        assertThat(testFournisseur.getNumeroRegistreCommerce()).isEqualTo(DEFAULT_NUMERO_REGISTRE_COMMERCE);
        assertThat(testFournisseur.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testFournisseur.getSigle()).isEqualTo(DEFAULT_SIGLE);
        assertThat(testFournisseur.getNumeroIdentiteFiscale()).isEqualTo(DEFAULT_NUMERO_IDENTITE_FISCALE);
    }

    @Test
    @Transactional
    void createFournisseurWithExistingId() throws Exception {
        // Create the Fournisseur with an existing ID
        fournisseur.setId(1L);

        int databaseSizeBeforeCreate = fournisseurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRaisonSocialeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fournisseurRepository.findAll().size();
        // set the field null
        fournisseur.setRaisonSociale(null);

        // Create the Fournisseur, which fails.

        restFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = fournisseurRepository.findAll().size();
        // set the field null
        fournisseur.setAdresse(null);

        // Create the Fournisseur, which fails.

        restFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = fournisseurRepository.findAll().size();
        // set the field null
        fournisseur.setEmail(null);

        // Create the Fournisseur, which fails.

        restFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = fournisseurRepository.findAll().size();
        // set the field null
        fournisseur.setTelephone(null);

        // Create the Fournisseur, which fails.

        restFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fournisseurRepository.findAll().size();
        // set the field null
        fournisseur.setDate(null);

        // Create the Fournisseur, which fails.

        restFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFournisseurs() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList
        restFournisseurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fournisseur.getId().intValue())))
            .andExpect(jsonPath("$.[*].raisonSociale").value(hasItem(DEFAULT_RAISON_SOCIALE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].pieceJointe").value(hasItem(DEFAULT_PIECE_JOINTE)))
            .andExpect(jsonPath("$.[*].numeroRegistreCommerce").value(hasItem(DEFAULT_NUMERO_REGISTRE_COMMERCE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].sigle").value(hasItem(DEFAULT_SIGLE)))
            .andExpect(jsonPath("$.[*].numeroIdentiteFiscale").value(hasItem(DEFAULT_NUMERO_IDENTITE_FISCALE)));
    }

    @Test
    @Transactional
    void getFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get the fournisseur
        restFournisseurMockMvc
            .perform(get(ENTITY_API_URL_ID, fournisseur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fournisseur.getId().intValue()))
            .andExpect(jsonPath("$.raisonSociale").value(DEFAULT_RAISON_SOCIALE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.pieceJointe").value(DEFAULT_PIECE_JOINTE))
            .andExpect(jsonPath("$.numeroRegistreCommerce").value(DEFAULT_NUMERO_REGISTRE_COMMERCE))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.sigle").value(DEFAULT_SIGLE))
            .andExpect(jsonPath("$.numeroIdentiteFiscale").value(DEFAULT_NUMERO_IDENTITE_FISCALE));
    }

    @Test
    @Transactional
    void getNonExistingFournisseur() throws Exception {
        // Get the fournisseur
        restFournisseurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Update the fournisseur
        Fournisseur updatedFournisseur = fournisseurRepository.findById(fournisseur.getId()).get();
        // Disconnect from session so that the updates on updatedFournisseur are not directly saved in db
        em.detach(updatedFournisseur);
        updatedFournisseur
            .raisonSociale(UPDATED_RAISON_SOCIALE)
            .adresse(UPDATED_ADRESSE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .pieceJointe(UPDATED_PIECE_JOINTE)
            .numeroRegistreCommerce(UPDATED_NUMERO_REGISTRE_COMMERCE)
            .date(UPDATED_DATE)
            .sigle(UPDATED_SIGLE)
            .numeroIdentiteFiscale(UPDATED_NUMERO_IDENTITE_FISCALE);

        restFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFournisseur.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getRaisonSociale()).isEqualTo(UPDATED_RAISON_SOCIALE);
        assertThat(testFournisseur.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testFournisseur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFournisseur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testFournisseur.getPieceJointe()).isEqualTo(UPDATED_PIECE_JOINTE);
        assertThat(testFournisseur.getNumeroRegistreCommerce()).isEqualTo(UPDATED_NUMERO_REGISTRE_COMMERCE);
        assertThat(testFournisseur.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testFournisseur.getSigle()).isEqualTo(UPDATED_SIGLE);
        assertThat(testFournisseur.getNumeroIdentiteFiscale()).isEqualTo(UPDATED_NUMERO_IDENTITE_FISCALE);
    }

    @Test
    @Transactional
    void putNonExistingFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fournisseur.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFournisseurWithPatch() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Update the fournisseur using partial update
        Fournisseur partialUpdatedFournisseur = new Fournisseur();
        partialUpdatedFournisseur.setId(fournisseur.getId());

        partialUpdatedFournisseur
            .adresse(UPDATED_ADRESSE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .numeroIdentiteFiscale(UPDATED_NUMERO_IDENTITE_FISCALE);

        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFournisseur.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getRaisonSociale()).isEqualTo(DEFAULT_RAISON_SOCIALE);
        assertThat(testFournisseur.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testFournisseur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFournisseur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testFournisseur.getPieceJointe()).isEqualTo(DEFAULT_PIECE_JOINTE);
        assertThat(testFournisseur.getNumeroRegistreCommerce()).isEqualTo(DEFAULT_NUMERO_REGISTRE_COMMERCE);
        assertThat(testFournisseur.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testFournisseur.getSigle()).isEqualTo(DEFAULT_SIGLE);
        assertThat(testFournisseur.getNumeroIdentiteFiscale()).isEqualTo(UPDATED_NUMERO_IDENTITE_FISCALE);
    }

    @Test
    @Transactional
    void fullUpdateFournisseurWithPatch() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Update the fournisseur using partial update
        Fournisseur partialUpdatedFournisseur = new Fournisseur();
        partialUpdatedFournisseur.setId(fournisseur.getId());

        partialUpdatedFournisseur
            .raisonSociale(UPDATED_RAISON_SOCIALE)
            .adresse(UPDATED_ADRESSE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .pieceJointe(UPDATED_PIECE_JOINTE)
            .numeroRegistreCommerce(UPDATED_NUMERO_REGISTRE_COMMERCE)
            .date(UPDATED_DATE)
            .sigle(UPDATED_SIGLE)
            .numeroIdentiteFiscale(UPDATED_NUMERO_IDENTITE_FISCALE);

        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFournisseur.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getRaisonSociale()).isEqualTo(UPDATED_RAISON_SOCIALE);
        assertThat(testFournisseur.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testFournisseur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFournisseur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testFournisseur.getPieceJointe()).isEqualTo(UPDATED_PIECE_JOINTE);
        assertThat(testFournisseur.getNumeroRegistreCommerce()).isEqualTo(UPDATED_NUMERO_REGISTRE_COMMERCE);
        assertThat(testFournisseur.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testFournisseur.getSigle()).isEqualTo(UPDATED_SIGLE);
        assertThat(testFournisseur.getNumeroIdentiteFiscale()).isEqualTo(UPDATED_NUMERO_IDENTITE_FISCALE);
    }

    @Test
    @Transactional
    void patchNonExistingFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fournisseur.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeDelete = fournisseurRepository.findAll().size();

        // Delete the fournisseur
        restFournisseurMockMvc
            .perform(delete(ENTITY_API_URL_ID, fournisseur.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
