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
import sn.ssi.sigmap.domain.Categories;
import sn.ssi.sigmap.repository.CategoriesRepository;

/**
 * Integration tests for the {@link CategoriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategoriesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriesMockMvc;

    private Categories categories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categories createEntity(EntityManager em) {
        Categories categories = new Categories().code(DEFAULT_CODE).designation(DEFAULT_DESIGNATION).commentaire(DEFAULT_COMMENTAIRE);
        return categories;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categories createUpdatedEntity(EntityManager em) {
        Categories categories = new Categories().code(UPDATED_CODE).designation(UPDATED_DESIGNATION).commentaire(UPDATED_COMMENTAIRE);
        return categories;
    }

    @BeforeEach
    public void initTest() {
        categories = createEntity(em);
    }

    @Test
    @Transactional
    void createCategories() throws Exception {
        int databaseSizeBeforeCreate = categoriesRepository.findAll().size();
        // Create the Categories
        restCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categories))
            )
            .andExpect(status().isCreated());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeCreate + 1);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCategories.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testCategories.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    @Transactional
    void createCategoriesWithExistingId() throws Exception {
        // Create the Categories with an existing ID
        categories.setId(1L);

        int databaseSizeBeforeCreate = categoriesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categories))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setCode(null);

        // Create the Categories, which fails.

        restCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categories))
            )
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setDesignation(null);

        // Create the Categories, which fails.

        restCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categories))
            )
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList
        restCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categories.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)));
    }

    @Test
    @Transactional
    void getCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get the categories
        restCategoriesMockMvc
            .perform(get(ENTITY_API_URL_ID, categories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categories.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE));
    }

    @Test
    @Transactional
    void getNonExistingCategories() throws Exception {
        // Get the categories
        restCategoriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();

        // Update the categories
        Categories updatedCategories = categoriesRepository.findById(categories.getId()).get();
        // Disconnect from session so that the updates on updatedCategories are not directly saved in db
        em.detach(updatedCategories);
        updatedCategories.code(UPDATED_CODE).designation(UPDATED_DESIGNATION).commentaire(UPDATED_COMMENTAIRE);

        restCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCategories.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCategories))
            )
            .andExpect(status().isOk());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCategories.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testCategories.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    void putNonExistingCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categories.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categories))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categories))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categories))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoriesWithPatch() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();

        // Update the categories using partial update
        Categories partialUpdatedCategories = new Categories();
        partialUpdatedCategories.setId(categories.getId());

        partialUpdatedCategories.designation(UPDATED_DESIGNATION).commentaire(UPDATED_COMMENTAIRE);

        restCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategories.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategories))
            )
            .andExpect(status().isOk());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCategories.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testCategories.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    void fullUpdateCategoriesWithPatch() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();

        // Update the categories using partial update
        Categories partialUpdatedCategories = new Categories();
        partialUpdatedCategories.setId(categories.getId());

        partialUpdatedCategories.code(UPDATED_CODE).designation(UPDATED_DESIGNATION).commentaire(UPDATED_COMMENTAIRE);

        restCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategories.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategories))
            )
            .andExpect(status().isOk());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCategories.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testCategories.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    void patchNonExistingCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categories.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categories))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categories))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categories))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeDelete = categoriesRepository.findAll().size();

        // Delete the categories
        restCategoriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, categories.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
