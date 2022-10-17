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
import sn.ssi.sigmap.domain.NaturesGarantie;
import sn.ssi.sigmap.repository.NaturesGarantieRepository;

/**
 * Integration tests for the {@link NaturesGarantieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NaturesGarantieResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/natures-garanties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NaturesGarantieRepository naturesGarantieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNaturesGarantieMockMvc;

    private NaturesGarantie naturesGarantie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NaturesGarantie createEntity(EntityManager em) {
        NaturesGarantie naturesGarantie = new NaturesGarantie().libelle(DEFAULT_LIBELLE);
        return naturesGarantie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NaturesGarantie createUpdatedEntity(EntityManager em) {
        NaturesGarantie naturesGarantie = new NaturesGarantie().libelle(UPDATED_LIBELLE);
        return naturesGarantie;
    }

    @BeforeEach
    public void initTest() {
        naturesGarantie = createEntity(em);
    }

    @Test
    @Transactional
    void createNaturesGarantie() throws Exception {
        int databaseSizeBeforeCreate = naturesGarantieRepository.findAll().size();
        // Create the NaturesGarantie
        restNaturesGarantieMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(naturesGarantie))
            )
            .andExpect(status().isCreated());

        // Validate the NaturesGarantie in the database
        List<NaturesGarantie> naturesGarantieList = naturesGarantieRepository.findAll();
        assertThat(naturesGarantieList).hasSize(databaseSizeBeforeCreate + 1);
        NaturesGarantie testNaturesGarantie = naturesGarantieList.get(naturesGarantieList.size() - 1);
        assertThat(testNaturesGarantie.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void createNaturesGarantieWithExistingId() throws Exception {
        // Create the NaturesGarantie with an existing ID
        naturesGarantie.setId(1L);

        int databaseSizeBeforeCreate = naturesGarantieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNaturesGarantieMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(naturesGarantie))
            )
            .andExpect(status().isBadRequest());

        // Validate the NaturesGarantie in the database
        List<NaturesGarantie> naturesGarantieList = naturesGarantieRepository.findAll();
        assertThat(naturesGarantieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = naturesGarantieRepository.findAll().size();
        // set the field null
        naturesGarantie.setLibelle(null);

        // Create the NaturesGarantie, which fails.

        restNaturesGarantieMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(naturesGarantie))
            )
            .andExpect(status().isBadRequest());

        List<NaturesGarantie> naturesGarantieList = naturesGarantieRepository.findAll();
        assertThat(naturesGarantieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNaturesGaranties() throws Exception {
        // Initialize the database
        naturesGarantieRepository.saveAndFlush(naturesGarantie);

        // Get all the naturesGarantieList
        restNaturesGarantieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naturesGarantie.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getNaturesGarantie() throws Exception {
        // Initialize the database
        naturesGarantieRepository.saveAndFlush(naturesGarantie);

        // Get the naturesGarantie
        restNaturesGarantieMockMvc
            .perform(get(ENTITY_API_URL_ID, naturesGarantie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(naturesGarantie.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getNonExistingNaturesGarantie() throws Exception {
        // Get the naturesGarantie
        restNaturesGarantieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNaturesGarantie() throws Exception {
        // Initialize the database
        naturesGarantieRepository.saveAndFlush(naturesGarantie);

        int databaseSizeBeforeUpdate = naturesGarantieRepository.findAll().size();

        // Update the naturesGarantie
        NaturesGarantie updatedNaturesGarantie = naturesGarantieRepository.findById(naturesGarantie.getId()).get();
        // Disconnect from session so that the updates on updatedNaturesGarantie are not directly saved in db
        em.detach(updatedNaturesGarantie);
        updatedNaturesGarantie.libelle(UPDATED_LIBELLE);

        restNaturesGarantieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNaturesGarantie.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNaturesGarantie))
            )
            .andExpect(status().isOk());

        // Validate the NaturesGarantie in the database
        List<NaturesGarantie> naturesGarantieList = naturesGarantieRepository.findAll();
        assertThat(naturesGarantieList).hasSize(databaseSizeBeforeUpdate);
        NaturesGarantie testNaturesGarantie = naturesGarantieList.get(naturesGarantieList.size() - 1);
        assertThat(testNaturesGarantie.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void putNonExistingNaturesGarantie() throws Exception {
        int databaseSizeBeforeUpdate = naturesGarantieRepository.findAll().size();
        naturesGarantie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNaturesGarantieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, naturesGarantie.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(naturesGarantie))
            )
            .andExpect(status().isBadRequest());

        // Validate the NaturesGarantie in the database
        List<NaturesGarantie> naturesGarantieList = naturesGarantieRepository.findAll();
        assertThat(naturesGarantieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNaturesGarantie() throws Exception {
        int databaseSizeBeforeUpdate = naturesGarantieRepository.findAll().size();
        naturesGarantie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaturesGarantieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(naturesGarantie))
            )
            .andExpect(status().isBadRequest());

        // Validate the NaturesGarantie in the database
        List<NaturesGarantie> naturesGarantieList = naturesGarantieRepository.findAll();
        assertThat(naturesGarantieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNaturesGarantie() throws Exception {
        int databaseSizeBeforeUpdate = naturesGarantieRepository.findAll().size();
        naturesGarantie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaturesGarantieMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(naturesGarantie))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NaturesGarantie in the database
        List<NaturesGarantie> naturesGarantieList = naturesGarantieRepository.findAll();
        assertThat(naturesGarantieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNaturesGarantieWithPatch() throws Exception {
        // Initialize the database
        naturesGarantieRepository.saveAndFlush(naturesGarantie);

        int databaseSizeBeforeUpdate = naturesGarantieRepository.findAll().size();

        // Update the naturesGarantie using partial update
        NaturesGarantie partialUpdatedNaturesGarantie = new NaturesGarantie();
        partialUpdatedNaturesGarantie.setId(naturesGarantie.getId());

        partialUpdatedNaturesGarantie.libelle(UPDATED_LIBELLE);

        restNaturesGarantieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNaturesGarantie.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNaturesGarantie))
            )
            .andExpect(status().isOk());

        // Validate the NaturesGarantie in the database
        List<NaturesGarantie> naturesGarantieList = naturesGarantieRepository.findAll();
        assertThat(naturesGarantieList).hasSize(databaseSizeBeforeUpdate);
        NaturesGarantie testNaturesGarantie = naturesGarantieList.get(naturesGarantieList.size() - 1);
        assertThat(testNaturesGarantie.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void fullUpdateNaturesGarantieWithPatch() throws Exception {
        // Initialize the database
        naturesGarantieRepository.saveAndFlush(naturesGarantie);

        int databaseSizeBeforeUpdate = naturesGarantieRepository.findAll().size();

        // Update the naturesGarantie using partial update
        NaturesGarantie partialUpdatedNaturesGarantie = new NaturesGarantie();
        partialUpdatedNaturesGarantie.setId(naturesGarantie.getId());

        partialUpdatedNaturesGarantie.libelle(UPDATED_LIBELLE);

        restNaturesGarantieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNaturesGarantie.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNaturesGarantie))
            )
            .andExpect(status().isOk());

        // Validate the NaturesGarantie in the database
        List<NaturesGarantie> naturesGarantieList = naturesGarantieRepository.findAll();
        assertThat(naturesGarantieList).hasSize(databaseSizeBeforeUpdate);
        NaturesGarantie testNaturesGarantie = naturesGarantieList.get(naturesGarantieList.size() - 1);
        assertThat(testNaturesGarantie.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void patchNonExistingNaturesGarantie() throws Exception {
        int databaseSizeBeforeUpdate = naturesGarantieRepository.findAll().size();
        naturesGarantie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNaturesGarantieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, naturesGarantie.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(naturesGarantie))
            )
            .andExpect(status().isBadRequest());

        // Validate the NaturesGarantie in the database
        List<NaturesGarantie> naturesGarantieList = naturesGarantieRepository.findAll();
        assertThat(naturesGarantieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNaturesGarantie() throws Exception {
        int databaseSizeBeforeUpdate = naturesGarantieRepository.findAll().size();
        naturesGarantie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaturesGarantieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(naturesGarantie))
            )
            .andExpect(status().isBadRequest());

        // Validate the NaturesGarantie in the database
        List<NaturesGarantie> naturesGarantieList = naturesGarantieRepository.findAll();
        assertThat(naturesGarantieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNaturesGarantie() throws Exception {
        int databaseSizeBeforeUpdate = naturesGarantieRepository.findAll().size();
        naturesGarantie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaturesGarantieMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(naturesGarantie))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NaturesGarantie in the database
        List<NaturesGarantie> naturesGarantieList = naturesGarantieRepository.findAll();
        assertThat(naturesGarantieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNaturesGarantie() throws Exception {
        // Initialize the database
        naturesGarantieRepository.saveAndFlush(naturesGarantie);

        int databaseSizeBeforeDelete = naturesGarantieRepository.findAll().size();

        // Delete the naturesGarantie
        restNaturesGarantieMockMvc
            .perform(delete(ENTITY_API_URL_ID, naturesGarantie.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NaturesGarantie> naturesGarantieList = naturesGarantieRepository.findAll();
        assertThat(naturesGarantieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
