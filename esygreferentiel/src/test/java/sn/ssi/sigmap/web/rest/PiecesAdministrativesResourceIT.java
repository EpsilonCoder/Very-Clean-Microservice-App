package sn.ssi.sigmap.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import sn.ssi.sigmap.domain.PiecesAdministratives;
import sn.ssi.sigmap.domain.enumeration.enumLocalisation;
import sn.ssi.sigmap.repository.PiecesAdministrativesRepository;

/**
 * Integration tests for the {@link PiecesAdministrativesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PiecesAdministrativesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final enumLocalisation DEFAULT_LOCALISATION = enumLocalisation.LOCALES;
    private static final enumLocalisation UPDATED_LOCALISATION = enumLocalisation.ETRANGERES;

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final String ENTITY_API_URL = "/api/pieces-administratives";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PiecesAdministrativesRepository piecesAdministrativesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPiecesAdministrativesMockMvc;

    private PiecesAdministratives piecesAdministratives;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PiecesAdministratives createEntity(EntityManager em) {
        PiecesAdministratives piecesAdministratives = new PiecesAdministratives()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .description(DEFAULT_DESCRIPTION)
            .localisation(DEFAULT_LOCALISATION)
            .type(DEFAULT_TYPE);
        return piecesAdministratives;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PiecesAdministratives createUpdatedEntity(EntityManager em) {
        PiecesAdministratives piecesAdministratives = new PiecesAdministratives()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .localisation(UPDATED_LOCALISATION)
            .type(UPDATED_TYPE);
        return piecesAdministratives;
    }

    @BeforeEach
    public void initTest() {
        piecesAdministratives = createEntity(em);
    }

    @Test
    @Transactional
    void createPiecesAdministratives() throws Exception {
        int databaseSizeBeforeCreate = piecesAdministrativesRepository.findAll().size();
        // Create the PiecesAdministratives
        restPiecesAdministrativesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(piecesAdministratives))
            )
            .andExpect(status().isCreated());

        // Validate the PiecesAdministratives in the database
        List<PiecesAdministratives> piecesAdministrativesList = piecesAdministrativesRepository.findAll();
        assertThat(piecesAdministrativesList).hasSize(databaseSizeBeforeCreate + 1);
        PiecesAdministratives testPiecesAdministratives = piecesAdministrativesList.get(piecesAdministrativesList.size() - 1);
        assertThat(testPiecesAdministratives.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPiecesAdministratives.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testPiecesAdministratives.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPiecesAdministratives.getLocalisation()).isEqualTo(DEFAULT_LOCALISATION);
        assertThat(testPiecesAdministratives.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createPiecesAdministrativesWithExistingId() throws Exception {
        // Create the PiecesAdministratives with an existing ID
        piecesAdministratives.setId(1L);

        int databaseSizeBeforeCreate = piecesAdministrativesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPiecesAdministrativesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(piecesAdministratives))
            )
            .andExpect(status().isBadRequest());

        // Validate the PiecesAdministratives in the database
        List<PiecesAdministratives> piecesAdministrativesList = piecesAdministrativesRepository.findAll();
        assertThat(piecesAdministrativesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = piecesAdministrativesRepository.findAll().size();
        // set the field null
        piecesAdministratives.setCode(null);

        // Create the PiecesAdministratives, which fails.

        restPiecesAdministrativesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(piecesAdministratives))
            )
            .andExpect(status().isBadRequest());

        List<PiecesAdministratives> piecesAdministrativesList = piecesAdministrativesRepository.findAll();
        assertThat(piecesAdministrativesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = piecesAdministrativesRepository.findAll().size();
        // set the field null
        piecesAdministratives.setLibelle(null);

        // Create the PiecesAdministratives, which fails.

        restPiecesAdministrativesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(piecesAdministratives))
            )
            .andExpect(status().isBadRequest());

        List<PiecesAdministratives> piecesAdministrativesList = piecesAdministrativesRepository.findAll();
        assertThat(piecesAdministrativesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLocalisationIsRequired() throws Exception {
        int databaseSizeBeforeTest = piecesAdministrativesRepository.findAll().size();
        // set the field null
        piecesAdministratives.setLocalisation(null);

        // Create the PiecesAdministratives, which fails.

        restPiecesAdministrativesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(piecesAdministratives))
            )
            .andExpect(status().isBadRequest());

        List<PiecesAdministratives> piecesAdministrativesList = piecesAdministrativesRepository.findAll();
        assertThat(piecesAdministrativesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPiecesAdministratives() throws Exception {
        // Initialize the database
        piecesAdministrativesRepository.saveAndFlush(piecesAdministratives);

        // Get all the piecesAdministrativesList
        restPiecesAdministrativesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(piecesAdministratives.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].localisation").value(hasItem(DEFAULT_LOCALISATION.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getPiecesAdministratives() throws Exception {
        // Initialize the database
        piecesAdministrativesRepository.saveAndFlush(piecesAdministratives);

        // Get the piecesAdministratives
        restPiecesAdministrativesMockMvc
            .perform(get(ENTITY_API_URL_ID, piecesAdministratives.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(piecesAdministratives.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.localisation").value(DEFAULT_LOCALISATION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingPiecesAdministratives() throws Exception {
        // Get the piecesAdministratives
        restPiecesAdministrativesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPiecesAdministratives() throws Exception {
        // Initialize the database
        piecesAdministrativesRepository.saveAndFlush(piecesAdministratives);

        int databaseSizeBeforeUpdate = piecesAdministrativesRepository.findAll().size();

        // Update the piecesAdministratives
        PiecesAdministratives updatedPiecesAdministratives = piecesAdministrativesRepository.findById(piecesAdministratives.getId()).get();
        // Disconnect from session so that the updates on updatedPiecesAdministratives are not directly saved in db
        em.detach(updatedPiecesAdministratives);
        updatedPiecesAdministratives
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .localisation(UPDATED_LOCALISATION)
            .type(UPDATED_TYPE);

        restPiecesAdministrativesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPiecesAdministratives.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPiecesAdministratives))
            )
            .andExpect(status().isOk());

        // Validate the PiecesAdministratives in the database
        List<PiecesAdministratives> piecesAdministrativesList = piecesAdministrativesRepository.findAll();
        assertThat(piecesAdministrativesList).hasSize(databaseSizeBeforeUpdate);
        PiecesAdministratives testPiecesAdministratives = piecesAdministrativesList.get(piecesAdministrativesList.size() - 1);
        assertThat(testPiecesAdministratives.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPiecesAdministratives.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testPiecesAdministratives.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPiecesAdministratives.getLocalisation()).isEqualTo(UPDATED_LOCALISATION);
        assertThat(testPiecesAdministratives.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingPiecesAdministratives() throws Exception {
        int databaseSizeBeforeUpdate = piecesAdministrativesRepository.findAll().size();
        piecesAdministratives.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPiecesAdministrativesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, piecesAdministratives.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(piecesAdministratives))
            )
            .andExpect(status().isBadRequest());

        // Validate the PiecesAdministratives in the database
        List<PiecesAdministratives> piecesAdministrativesList = piecesAdministrativesRepository.findAll();
        assertThat(piecesAdministrativesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPiecesAdministratives() throws Exception {
        int databaseSizeBeforeUpdate = piecesAdministrativesRepository.findAll().size();
        piecesAdministratives.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPiecesAdministrativesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(piecesAdministratives))
            )
            .andExpect(status().isBadRequest());

        // Validate the PiecesAdministratives in the database
        List<PiecesAdministratives> piecesAdministrativesList = piecesAdministrativesRepository.findAll();
        assertThat(piecesAdministrativesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPiecesAdministratives() throws Exception {
        int databaseSizeBeforeUpdate = piecesAdministrativesRepository.findAll().size();
        piecesAdministratives.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPiecesAdministrativesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(piecesAdministratives))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PiecesAdministratives in the database
        List<PiecesAdministratives> piecesAdministrativesList = piecesAdministrativesRepository.findAll();
        assertThat(piecesAdministrativesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePiecesAdministrativesWithPatch() throws Exception {
        // Initialize the database
        piecesAdministrativesRepository.saveAndFlush(piecesAdministratives);

        int databaseSizeBeforeUpdate = piecesAdministrativesRepository.findAll().size();

        // Update the piecesAdministratives using partial update
        PiecesAdministratives partialUpdatedPiecesAdministratives = new PiecesAdministratives();
        partialUpdatedPiecesAdministratives.setId(piecesAdministratives.getId());

        partialUpdatedPiecesAdministratives.libelle(UPDATED_LIBELLE).description(UPDATED_DESCRIPTION).type(UPDATED_TYPE);

        restPiecesAdministrativesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPiecesAdministratives.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPiecesAdministratives))
            )
            .andExpect(status().isOk());

        // Validate the PiecesAdministratives in the database
        List<PiecesAdministratives> piecesAdministrativesList = piecesAdministrativesRepository.findAll();
        assertThat(piecesAdministrativesList).hasSize(databaseSizeBeforeUpdate);
        PiecesAdministratives testPiecesAdministratives = piecesAdministrativesList.get(piecesAdministrativesList.size() - 1);
        assertThat(testPiecesAdministratives.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPiecesAdministratives.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testPiecesAdministratives.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPiecesAdministratives.getLocalisation()).isEqualTo(DEFAULT_LOCALISATION);
        assertThat(testPiecesAdministratives.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void fullUpdatePiecesAdministrativesWithPatch() throws Exception {
        // Initialize the database
        piecesAdministrativesRepository.saveAndFlush(piecesAdministratives);

        int databaseSizeBeforeUpdate = piecesAdministrativesRepository.findAll().size();

        // Update the piecesAdministratives using partial update
        PiecesAdministratives partialUpdatedPiecesAdministratives = new PiecesAdministratives();
        partialUpdatedPiecesAdministratives.setId(piecesAdministratives.getId());

        partialUpdatedPiecesAdministratives
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .localisation(UPDATED_LOCALISATION)
            .type(UPDATED_TYPE);

        restPiecesAdministrativesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPiecesAdministratives.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPiecesAdministratives))
            )
            .andExpect(status().isOk());

        // Validate the PiecesAdministratives in the database
        List<PiecesAdministratives> piecesAdministrativesList = piecesAdministrativesRepository.findAll();
        assertThat(piecesAdministrativesList).hasSize(databaseSizeBeforeUpdate);
        PiecesAdministratives testPiecesAdministratives = piecesAdministrativesList.get(piecesAdministrativesList.size() - 1);
        assertThat(testPiecesAdministratives.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPiecesAdministratives.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testPiecesAdministratives.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPiecesAdministratives.getLocalisation()).isEqualTo(UPDATED_LOCALISATION);
        assertThat(testPiecesAdministratives.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingPiecesAdministratives() throws Exception {
        int databaseSizeBeforeUpdate = piecesAdministrativesRepository.findAll().size();
        piecesAdministratives.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPiecesAdministrativesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, piecesAdministratives.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(piecesAdministratives))
            )
            .andExpect(status().isBadRequest());

        // Validate the PiecesAdministratives in the database
        List<PiecesAdministratives> piecesAdministrativesList = piecesAdministrativesRepository.findAll();
        assertThat(piecesAdministrativesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPiecesAdministratives() throws Exception {
        int databaseSizeBeforeUpdate = piecesAdministrativesRepository.findAll().size();
        piecesAdministratives.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPiecesAdministrativesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(piecesAdministratives))
            )
            .andExpect(status().isBadRequest());

        // Validate the PiecesAdministratives in the database
        List<PiecesAdministratives> piecesAdministrativesList = piecesAdministrativesRepository.findAll();
        assertThat(piecesAdministrativesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPiecesAdministratives() throws Exception {
        int databaseSizeBeforeUpdate = piecesAdministrativesRepository.findAll().size();
        piecesAdministratives.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPiecesAdministrativesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(piecesAdministratives))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PiecesAdministratives in the database
        List<PiecesAdministratives> piecesAdministrativesList = piecesAdministrativesRepository.findAll();
        assertThat(piecesAdministrativesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePiecesAdministratives() throws Exception {
        // Initialize the database
        piecesAdministrativesRepository.saveAndFlush(piecesAdministratives);

        int databaseSizeBeforeDelete = piecesAdministrativesRepository.findAll().size();

        // Delete the piecesAdministratives
        restPiecesAdministrativesMockMvc
            .perform(delete(ENTITY_API_URL_ID, piecesAdministratives.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PiecesAdministratives> piecesAdministrativesList = piecesAdministrativesRepository.findAll();
        assertThat(piecesAdministrativesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
