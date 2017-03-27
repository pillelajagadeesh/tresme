package com.tresbu.tresiot.web.rest;

import com.tresbu.tresiot.TresiotApp;

import com.tresbu.tresiot.domain.Applicationevent;
import com.tresbu.tresiot.domain.Application;
import com.tresbu.tresiot.repository.ApplicationeventRepository;
import com.tresbu.tresiot.service.ApplicationeventService;
import com.tresbu.tresiot.service.dto.ApplicationeventDTO;
import com.tresbu.tresiot.service.mapper.ApplicationeventMapper;

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
 * Test class for the ApplicationeventResource REST controller.
 *
 * @see ApplicationeventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TresiotApp.class)
public class ApplicationeventResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private ApplicationeventRepository applicationeventRepository;

    @Inject
    private ApplicationeventMapper applicationeventMapper;

    @Inject
    private ApplicationeventService applicationeventService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restApplicationeventMockMvc;

    private Applicationevent applicationevent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApplicationeventResource applicationeventResource = new ApplicationeventResource();
        ReflectionTestUtils.setField(applicationeventResource, "applicationeventService", applicationeventService);
        this.restApplicationeventMockMvc = MockMvcBuilders.standaloneSetup(applicationeventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Applicationevent createEntity(EntityManager em) {
        Applicationevent applicationevent = new Applicationevent()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Application application = ApplicationResourceIntTest.createEntity(em);
        em.persist(application);
        em.flush();
        applicationevent.setApplication(application);
        return applicationevent;
    }

    @Before
    public void initTest() {
        applicationevent = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationevent() throws Exception {
        int databaseSizeBeforeCreate = applicationeventRepository.findAll().size();

        // Create the Applicationevent
        ApplicationeventDTO applicationeventDTO = applicationeventMapper.applicationeventToApplicationeventDTO(applicationevent);

        restApplicationeventMockMvc.perform(post("/api/applicationevents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationeventDTO)))
            .andExpect(status().isCreated());

        // Validate the Applicationevent in the database
        List<Applicationevent> applicationeventList = applicationeventRepository.findAll();
        assertThat(applicationeventList).hasSize(databaseSizeBeforeCreate + 1);
        Applicationevent testApplicationevent = applicationeventList.get(applicationeventList.size() - 1);
        assertThat(testApplicationevent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApplicationevent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createApplicationeventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationeventRepository.findAll().size();

        // Create the Applicationevent with an existing ID
        Applicationevent existingApplicationevent = new Applicationevent();
        existingApplicationevent.setId(1L);
        ApplicationeventDTO existingApplicationeventDTO = applicationeventMapper.applicationeventToApplicationeventDTO(existingApplicationevent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationeventMockMvc.perform(post("/api/applicationevents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingApplicationeventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Applicationevent> applicationeventList = applicationeventRepository.findAll();
        assertThat(applicationeventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationeventRepository.findAll().size();
        // set the field null
        applicationevent.setName(null);

        // Create the Applicationevent, which fails.
        ApplicationeventDTO applicationeventDTO = applicationeventMapper.applicationeventToApplicationeventDTO(applicationevent);

        restApplicationeventMockMvc.perform(post("/api/applicationevents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationeventDTO)))
            .andExpect(status().isBadRequest());

        List<Applicationevent> applicationeventList = applicationeventRepository.findAll();
        assertThat(applicationeventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationevents() throws Exception {
        // Initialize the database
        applicationeventRepository.saveAndFlush(applicationevent);

        // Get all the applicationeventList
        restApplicationeventMockMvc.perform(get("/api/applicationevents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationevent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getApplicationevent() throws Exception {
        // Initialize the database
        applicationeventRepository.saveAndFlush(applicationevent);

        // Get the applicationevent
        restApplicationeventMockMvc.perform(get("/api/applicationevents/{id}", applicationevent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationevent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplicationevent() throws Exception {
        // Get the applicationevent
        restApplicationeventMockMvc.perform(get("/api/applicationevents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationevent() throws Exception {
        // Initialize the database
        applicationeventRepository.saveAndFlush(applicationevent);
        int databaseSizeBeforeUpdate = applicationeventRepository.findAll().size();

        // Update the applicationevent
        Applicationevent updatedApplicationevent = applicationeventRepository.findOne(applicationevent.getId());
        updatedApplicationevent
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        ApplicationeventDTO applicationeventDTO = applicationeventMapper.applicationeventToApplicationeventDTO(updatedApplicationevent);

        restApplicationeventMockMvc.perform(put("/api/applicationevents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationeventDTO)))
            .andExpect(status().isOk());

        // Validate the Applicationevent in the database
        List<Applicationevent> applicationeventList = applicationeventRepository.findAll();
        assertThat(applicationeventList).hasSize(databaseSizeBeforeUpdate);
        Applicationevent testApplicationevent = applicationeventList.get(applicationeventList.size() - 1);
        assertThat(testApplicationevent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApplicationevent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationevent() throws Exception {
        int databaseSizeBeforeUpdate = applicationeventRepository.findAll().size();

        // Create the Applicationevent
        ApplicationeventDTO applicationeventDTO = applicationeventMapper.applicationeventToApplicationeventDTO(applicationevent);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApplicationeventMockMvc.perform(put("/api/applicationevents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationeventDTO)))
            .andExpect(status().isCreated());

        // Validate the Applicationevent in the database
        List<Applicationevent> applicationeventList = applicationeventRepository.findAll();
        assertThat(applicationeventList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteApplicationevent() throws Exception {
        // Initialize the database
        applicationeventRepository.saveAndFlush(applicationevent);
        int databaseSizeBeforeDelete = applicationeventRepository.findAll().size();

        // Get the applicationevent
        restApplicationeventMockMvc.perform(delete("/api/applicationevents/{id}", applicationevent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Applicationevent> applicationeventList = applicationeventRepository.findAll();
        assertThat(applicationeventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
