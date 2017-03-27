package com.tresbu.tresiot.web.rest;

import com.tresbu.tresiot.TresiotApp;

import com.tresbu.tresiot.domain.Eventdata;
import com.tresbu.tresiot.domain.Application;
import com.tresbu.tresiot.domain.Applicationevent;
import com.tresbu.tresiot.domain.Applicationview;
import com.tresbu.tresiot.repository.EventdataRepository;
import com.tresbu.tresiot.service.EventdataService;
import com.tresbu.tresiot.service.dto.EventdataDTO;
import com.tresbu.tresiot.service.mapper.EventdataMapper;

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
 * Test class for the EventdataResource REST controller.
 *
 * @see EventdataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TresiotApp.class)
public class EventdataResourceIntTest {

    private static final String DEFAULT_CLIENTID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENTOS = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTOS = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICEMAKE = "AAAAAAAAAA";
    private static final String UPDATED_DEVICEMAKE = "BBBBBBBBBB";

    private static final String DEFAULT_APPVERSION = "AAAAAAAAAA";
    private static final String UPDATED_APPVERSION = "BBBBBBBBBB";

    private static final Long DEFAULT_SESSIONDURATION = 1L;
    private static final Long UPDATED_SESSIONDURATION = 2L;

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    @Inject
    private EventdataRepository eventdataRepository;

    @Inject
    private EventdataMapper eventdataMapper;

    @Inject
    private EventdataService eventdataService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEventdataMockMvc;

    private Eventdata eventdata;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EventdataResource eventdataResource = new EventdataResource();
        ReflectionTestUtils.setField(eventdataResource, "eventdataService", eventdataService);
        this.restEventdataMockMvc = MockMvcBuilders.standaloneSetup(eventdataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eventdata createEntity(EntityManager em) {
        Eventdata eventdata = new Eventdata()
                .clientid(DEFAULT_CLIENTID)
                .clientos(DEFAULT_CLIENTOS)
                .devicemake(DEFAULT_DEVICEMAKE)
                .appversion(DEFAULT_APPVERSION)
                .sessionduration(DEFAULT_SESSIONDURATION)
                .location(DEFAULT_LOCATION);
        // Add required entity
        Application application = ApplicationResourceIntTest.createEntity(em);
        em.persist(application);
        em.flush();
        eventdata.setApplication(application);
        // Add required entity
        Applicationevent applicationevent = ApplicationeventResourceIntTest.createEntity(em);
        em.persist(applicationevent);
        em.flush();
        eventdata.setApplicationevent(applicationevent);
        // Add required entity
        Applicationview applicationview = ApplicationviewResourceIntTest.createEntity(em);
        em.persist(applicationview);
        em.flush();
        eventdata.setApplicationview(applicationview);
        return eventdata;
    }

    @Before
    public void initTest() {
        eventdata = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventdata() throws Exception {
        int databaseSizeBeforeCreate = eventdataRepository.findAll().size();

        // Create the Eventdata
        EventdataDTO eventdataDTO = eventdataMapper.eventdataToEventdataDTO(eventdata);

        restEventdataMockMvc.perform(post("/api/eventdata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventdataDTO)))
            .andExpect(status().isCreated());

        // Validate the Eventdata in the database
        List<Eventdata> eventdataList = eventdataRepository.findAll();
        assertThat(eventdataList).hasSize(databaseSizeBeforeCreate + 1);
        Eventdata testEventdata = eventdataList.get(eventdataList.size() - 1);
        assertThat(testEventdata.getClientid()).isEqualTo(DEFAULT_CLIENTID);
        assertThat(testEventdata.getClientos()).isEqualTo(DEFAULT_CLIENTOS);
        assertThat(testEventdata.getDevicemake()).isEqualTo(DEFAULT_DEVICEMAKE);
        assertThat(testEventdata.getAppversion()).isEqualTo(DEFAULT_APPVERSION);
        assertThat(testEventdata.getSessionduration()).isEqualTo(DEFAULT_SESSIONDURATION);
        assertThat(testEventdata.getLocation()).isEqualTo(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    public void createEventdataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventdataRepository.findAll().size();

        // Create the Eventdata with an existing ID
        Eventdata existingEventdata = new Eventdata();
        existingEventdata.setId(1L);
        EventdataDTO existingEventdataDTO = eventdataMapper.eventdataToEventdataDTO(existingEventdata);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventdataMockMvc.perform(post("/api/eventdata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEventdataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Eventdata> eventdataList = eventdataRepository.findAll();
        assertThat(eventdataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkClientidIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventdataRepository.findAll().size();
        // set the field null
        eventdata.setClientid(null);

        // Create the Eventdata, which fails.
        EventdataDTO eventdataDTO = eventdataMapper.eventdataToEventdataDTO(eventdata);

        restEventdataMockMvc.perform(post("/api/eventdata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventdataDTO)))
            .andExpect(status().isBadRequest());

        List<Eventdata> eventdataList = eventdataRepository.findAll();
        assertThat(eventdataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventdata() throws Exception {
        // Initialize the database
        eventdataRepository.saveAndFlush(eventdata);

        // Get all the eventdataList
        restEventdataMockMvc.perform(get("/api/eventdata?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventdata.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientid").value(hasItem(DEFAULT_CLIENTID.toString())))
            .andExpect(jsonPath("$.[*].clientos").value(hasItem(DEFAULT_CLIENTOS.toString())))
            .andExpect(jsonPath("$.[*].devicemake").value(hasItem(DEFAULT_DEVICEMAKE.toString())))
            .andExpect(jsonPath("$.[*].appversion").value(hasItem(DEFAULT_APPVERSION.toString())))
            .andExpect(jsonPath("$.[*].sessionduration").value(hasItem(DEFAULT_SESSIONDURATION.intValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())));
    }

    @Test
    @Transactional
    public void getEventdata() throws Exception {
        // Initialize the database
        eventdataRepository.saveAndFlush(eventdata);

        // Get the eventdata
        restEventdataMockMvc.perform(get("/api/eventdata/{id}", eventdata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eventdata.getId().intValue()))
            .andExpect(jsonPath("$.clientid").value(DEFAULT_CLIENTID.toString()))
            .andExpect(jsonPath("$.clientos").value(DEFAULT_CLIENTOS.toString()))
            .andExpect(jsonPath("$.devicemake").value(DEFAULT_DEVICEMAKE.toString()))
            .andExpect(jsonPath("$.appversion").value(DEFAULT_APPVERSION.toString()))
            .andExpect(jsonPath("$.sessionduration").value(DEFAULT_SESSIONDURATION.intValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEventdata() throws Exception {
        // Get the eventdata
        restEventdataMockMvc.perform(get("/api/eventdata/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventdata() throws Exception {
        // Initialize the database
        eventdataRepository.saveAndFlush(eventdata);
        int databaseSizeBeforeUpdate = eventdataRepository.findAll().size();

        // Update the eventdata
        Eventdata updatedEventdata = eventdataRepository.findOne(eventdata.getId());
        updatedEventdata
                .clientid(UPDATED_CLIENTID)
                .clientos(UPDATED_CLIENTOS)
                .devicemake(UPDATED_DEVICEMAKE)
                .appversion(UPDATED_APPVERSION)
                .sessionduration(UPDATED_SESSIONDURATION)
                .location(UPDATED_LOCATION);
        EventdataDTO eventdataDTO = eventdataMapper.eventdataToEventdataDTO(updatedEventdata);

        restEventdataMockMvc.perform(put("/api/eventdata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventdataDTO)))
            .andExpect(status().isOk());

        // Validate the Eventdata in the database
        List<Eventdata> eventdataList = eventdataRepository.findAll();
        assertThat(eventdataList).hasSize(databaseSizeBeforeUpdate);
        Eventdata testEventdata = eventdataList.get(eventdataList.size() - 1);
        assertThat(testEventdata.getClientid()).isEqualTo(UPDATED_CLIENTID);
        assertThat(testEventdata.getClientos()).isEqualTo(UPDATED_CLIENTOS);
        assertThat(testEventdata.getDevicemake()).isEqualTo(UPDATED_DEVICEMAKE);
        assertThat(testEventdata.getAppversion()).isEqualTo(UPDATED_APPVERSION);
        assertThat(testEventdata.getSessionduration()).isEqualTo(UPDATED_SESSIONDURATION);
        assertThat(testEventdata.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void updateNonExistingEventdata() throws Exception {
        int databaseSizeBeforeUpdate = eventdataRepository.findAll().size();

        // Create the Eventdata
        EventdataDTO eventdataDTO = eventdataMapper.eventdataToEventdataDTO(eventdata);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEventdataMockMvc.perform(put("/api/eventdata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventdataDTO)))
            .andExpect(status().isCreated());

        // Validate the Eventdata in the database
        List<Eventdata> eventdataList = eventdataRepository.findAll();
        assertThat(eventdataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEventdata() throws Exception {
        // Initialize the database
        eventdataRepository.saveAndFlush(eventdata);
        int databaseSizeBeforeDelete = eventdataRepository.findAll().size();

        // Get the eventdata
        restEventdataMockMvc.perform(delete("/api/eventdata/{id}", eventdata.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Eventdata> eventdataList = eventdataRepository.findAll();
        assertThat(eventdataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
