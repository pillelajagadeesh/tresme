package com.tresbu.tresiot.web.rest;

import com.tresbu.tresiot.TresiotApp;

import com.tresbu.tresiot.domain.Applicationview;
import com.tresbu.tresiot.domain.Application;
import com.tresbu.tresiot.repository.ApplicationviewRepository;
import com.tresbu.tresiot.service.ApplicationviewService;
import com.tresbu.tresiot.service.dto.ApplicationviewDTO;
import com.tresbu.tresiot.service.mapper.ApplicationviewMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ApplicationviewResource REST controller.
 *
 * @see ApplicationviewResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TresiotApp.class)
public class ApplicationviewResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private ApplicationviewRepository applicationviewRepository;

    @Inject
    private ApplicationviewMapper applicationviewMapper;

    @Inject
    private ApplicationviewService applicationviewService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restApplicationviewMockMvc;

    private Applicationview applicationview;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApplicationviewResource applicationviewResource = new ApplicationviewResource();
        ReflectionTestUtils.setField(applicationviewResource, "applicationviewService", applicationviewService);
        this.restApplicationviewMockMvc = MockMvcBuilders.standaloneSetup(applicationviewResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Applicationview createEntity(EntityManager em) {
        Applicationview applicationview = new Applicationview()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Application application = ApplicationResourceIntTest.createEntity(em);
        em.persist(application);
        em.flush();
        applicationview.setApplication(application);
        return applicationview;
    }

    @Before
    public void initTest() {
        applicationview = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationview() throws Exception {
        int databaseSizeBeforeCreate = applicationviewRepository.findAll().size();

        // Create the Applicationview
        ApplicationviewDTO applicationviewDTO = applicationviewMapper.applicationviewToApplicationviewDTO(applicationview);

        restApplicationviewMockMvc.perform(post("/api/applicationviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationviewDTO)))
            .andExpect(status().isCreated());

        // Validate the Applicationview in the database
        List<Applicationview> applicationviewList = applicationviewRepository.findAll();
        assertThat(applicationviewList).hasSize(databaseSizeBeforeCreate + 1);
        Applicationview testApplicationview = applicationviewList.get(applicationviewList.size() - 1);
        assertThat(testApplicationview.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApplicationview.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createApplicationviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationviewRepository.findAll().size();

        // Create the Applicationview with an existing ID
        Applicationview existingApplicationview = new Applicationview();
        existingApplicationview.setId(1L);
        ApplicationviewDTO existingApplicationviewDTO = applicationviewMapper.applicationviewToApplicationviewDTO(existingApplicationview);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationviewMockMvc.perform(post("/api/applicationviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingApplicationviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Applicationview> applicationviewList = applicationviewRepository.findAll();
        assertThat(applicationviewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationviewRepository.findAll().size();
        // set the field null
        applicationview.setName(null);

        // Create the Applicationview, which fails.
        ApplicationviewDTO applicationviewDTO = applicationviewMapper.applicationviewToApplicationviewDTO(applicationview);

        restApplicationviewMockMvc.perform(post("/api/applicationviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationviewDTO)))
            .andExpect(status().isBadRequest());

        List<Applicationview> applicationviewList = applicationviewRepository.findAll();
        assertThat(applicationviewList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationviews() throws Exception {
        // Initialize the database
        applicationviewRepository.saveAndFlush(applicationview);

        // Get all the applicationviewList
        restApplicationviewMockMvc.perform(get("/api/applicationviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationview.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getApplicationview() throws Exception {
        // Initialize the database
        applicationviewRepository.saveAndFlush(applicationview);

        // Get the applicationview
        restApplicationviewMockMvc.perform(get("/api/applicationviews/{id}", applicationview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationview.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplicationview() throws Exception {
        // Get the applicationview
        restApplicationviewMockMvc.perform(get("/api/applicationviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationview() throws Exception {
        // Initialize the database
        applicationviewRepository.saveAndFlush(applicationview);
        int databaseSizeBeforeUpdate = applicationviewRepository.findAll().size();

        // Update the applicationview
        Applicationview updatedApplicationview = applicationviewRepository.findOne(applicationview.getId());
        updatedApplicationview
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        ApplicationviewDTO applicationviewDTO = applicationviewMapper.applicationviewToApplicationviewDTO(updatedApplicationview);

        restApplicationviewMockMvc.perform(put("/api/applicationviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationviewDTO)))
            .andExpect(status().isOk());

        // Validate the Applicationview in the database
        List<Applicationview> applicationviewList = applicationviewRepository.findAll();
        assertThat(applicationviewList).hasSize(databaseSizeBeforeUpdate);
        Applicationview testApplicationview = applicationviewList.get(applicationviewList.size() - 1);
        assertThat(testApplicationview.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApplicationview.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationview() throws Exception {
        int databaseSizeBeforeUpdate = applicationviewRepository.findAll().size();

        // Create the Applicationview
        ApplicationviewDTO applicationviewDTO = applicationviewMapper.applicationviewToApplicationviewDTO(applicationview);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApplicationviewMockMvc.perform(put("/api/applicationviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationviewDTO)))
            .andExpect(status().isCreated());

        // Validate the Applicationview in the database
        List<Applicationview> applicationviewList = applicationviewRepository.findAll();
        assertThat(applicationviewList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteApplicationview() throws Exception {
        // Initialize the database
        applicationviewRepository.saveAndFlush(applicationview);
        int databaseSizeBeforeDelete = applicationviewRepository.findAll().size();

        // Get the applicationview
        restApplicationviewMockMvc.perform(delete("/api/applicationviews/{id}", applicationview.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Applicationview> applicationviewList = applicationviewRepository.findAll();
        assertThat(applicationviewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
