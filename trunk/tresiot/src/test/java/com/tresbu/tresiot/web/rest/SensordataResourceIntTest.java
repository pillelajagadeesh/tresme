package com.tresbu.tresiot.web.rest;

import com.tresbu.tresiot.TresiotApp;

import com.tresbu.tresiot.domain.Sensordata;
import com.tresbu.tresiot.domain.Application;
import com.tresbu.tresiot.domain.Sensor;
import com.tresbu.tresiot.repository.SensordataRepository;
import com.tresbu.tresiot.service.SensordataService;
import com.tresbu.tresiot.service.dto.SensordataDTO;
import com.tresbu.tresiot.service.mapper.SensordataMapper;

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
 * Test class for the SensordataResource REST controller.
 *
 * @see SensordataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TresiotApp.class)
public class SensordataResourceIntTest {

    private static final String DEFAULT_CLIENTID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTID = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_TIME = 1L;
    private static final Long UPDATED_CREATED_TIME = 2L;

    @Inject
    private SensordataRepository sensordataRepository;

    @Inject
    private SensordataMapper sensordataMapper;

    @Inject
    private SensordataService sensordataService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSensordataMockMvc;

    private Sensordata sensordata;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SensordataResource sensordataResource = new SensordataResource();
        ReflectionTestUtils.setField(sensordataResource, "sensordataService", sensordataService);
        this.restSensordataMockMvc = MockMvcBuilders.standaloneSetup(sensordataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sensordata createEntity(EntityManager em) {
        Sensordata sensordata = new Sensordata()
                .clientid(DEFAULT_CLIENTID)
                .value(DEFAULT_VALUE)
                .createdTime(DEFAULT_CREATED_TIME);
        // Add required entity
        Application application = ApplicationResourceIntTest.createEntity(em);
        em.persist(application);
        em.flush();
        sensordata.setApplication(application);
        // Add required entity
        Sensor sensor = SensorResourceIntTest.createEntity(em);
        em.persist(sensor);
        em.flush();
        sensordata.setSensor(sensor);
        return sensordata;
    }

    @Before
    public void initTest() {
        sensordata = createEntity(em);
    }

    @Test
    @Transactional
    public void createSensordata() throws Exception {
        int databaseSizeBeforeCreate = sensordataRepository.findAll().size();

        // Create the Sensordata
        SensordataDTO sensordataDTO = sensordataMapper.sensordataToSensordataDTO(sensordata);

        restSensordataMockMvc.perform(post("/api/sensordata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensordataDTO)))
            .andExpect(status().isCreated());

        // Validate the Sensordata in the database
        List<Sensordata> sensordataList = sensordataRepository.findAll();
        assertThat(sensordataList).hasSize(databaseSizeBeforeCreate + 1);
        Sensordata testSensordata = sensordataList.get(sensordataList.size() - 1);
        assertThat(testSensordata.getClientid()).isEqualTo(DEFAULT_CLIENTID);
        assertThat(testSensordata.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testSensordata.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
    }

    @Test
    @Transactional
    public void createSensordataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sensordataRepository.findAll().size();

        // Create the Sensordata with an existing ID
        Sensordata existingSensordata = new Sensordata();
        existingSensordata.setId(1L);
        SensordataDTO existingSensordataDTO = sensordataMapper.sensordataToSensordataDTO(existingSensordata);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSensordataMockMvc.perform(post("/api/sensordata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSensordataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Sensordata> sensordataList = sensordataRepository.findAll();
        assertThat(sensordataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkClientidIsRequired() throws Exception {
        int databaseSizeBeforeTest = sensordataRepository.findAll().size();
        // set the field null
        sensordata.setClientid(null);

        // Create the Sensordata, which fails.
        SensordataDTO sensordataDTO = sensordataMapper.sensordataToSensordataDTO(sensordata);

        restSensordataMockMvc.perform(post("/api/sensordata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensordataDTO)))
            .andExpect(status().isBadRequest());

        List<Sensordata> sensordataList = sensordataRepository.findAll();
        assertThat(sensordataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = sensordataRepository.findAll().size();
        // set the field null
        sensordata.setValue(null);

        // Create the Sensordata, which fails.
        SensordataDTO sensordataDTO = sensordataMapper.sensordataToSensordataDTO(sensordata);

        restSensordataMockMvc.perform(post("/api/sensordata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensordataDTO)))
            .andExpect(status().isBadRequest());

        List<Sensordata> sensordataList = sensordataRepository.findAll();
        assertThat(sensordataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sensordataRepository.findAll().size();
        // set the field null
        sensordata.setCreatedTime(null);

        // Create the Sensordata, which fails.
        SensordataDTO sensordataDTO = sensordataMapper.sensordataToSensordataDTO(sensordata);

        restSensordataMockMvc.perform(post("/api/sensordata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensordataDTO)))
            .andExpect(status().isBadRequest());

        List<Sensordata> sensordataList = sensordataRepository.findAll();
        assertThat(sensordataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSensordata() throws Exception {
        // Initialize the database
        sensordataRepository.saveAndFlush(sensordata);

        // Get all the sensordataList
        restSensordataMockMvc.perform(get("/api/sensordata?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sensordata.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientid").value(hasItem(DEFAULT_CLIENTID.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.intValue())));
    }

    @Test
    @Transactional
    public void getSensordata() throws Exception {
        // Initialize the database
        sensordataRepository.saveAndFlush(sensordata);

        // Get the sensordata
        restSensordataMockMvc.perform(get("/api/sensordata/{id}", sensordata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sensordata.getId().intValue()))
            .andExpect(jsonPath("$.clientid").value(DEFAULT_CLIENTID.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSensordata() throws Exception {
        // Get the sensordata
        restSensordataMockMvc.perform(get("/api/sensordata/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSensordata() throws Exception {
        // Initialize the database
        sensordataRepository.saveAndFlush(sensordata);
        int databaseSizeBeforeUpdate = sensordataRepository.findAll().size();

        // Update the sensordata
        Sensordata updatedSensordata = sensordataRepository.findOne(sensordata.getId());
        updatedSensordata
                .clientid(UPDATED_CLIENTID)
                .value(UPDATED_VALUE)
                .createdTime(UPDATED_CREATED_TIME);
        SensordataDTO sensordataDTO = sensordataMapper.sensordataToSensordataDTO(updatedSensordata);

        restSensordataMockMvc.perform(put("/api/sensordata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensordataDTO)))
            .andExpect(status().isOk());

        // Validate the Sensordata in the database
        List<Sensordata> sensordataList = sensordataRepository.findAll();
        assertThat(sensordataList).hasSize(databaseSizeBeforeUpdate);
        Sensordata testSensordata = sensordataList.get(sensordataList.size() - 1);
        assertThat(testSensordata.getClientid()).isEqualTo(UPDATED_CLIENTID);
        assertThat(testSensordata.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSensordata.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSensordata() throws Exception {
        int databaseSizeBeforeUpdate = sensordataRepository.findAll().size();

        // Create the Sensordata
        SensordataDTO sensordataDTO = sensordataMapper.sensordataToSensordataDTO(sensordata);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSensordataMockMvc.perform(put("/api/sensordata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensordataDTO)))
            .andExpect(status().isCreated());

        // Validate the Sensordata in the database
        List<Sensordata> sensordataList = sensordataRepository.findAll();
        assertThat(sensordataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSensordata() throws Exception {
        // Initialize the database
        sensordataRepository.saveAndFlush(sensordata);
        int databaseSizeBeforeDelete = sensordataRepository.findAll().size();

        // Get the sensordata
        restSensordataMockMvc.perform(delete("/api/sensordata/{id}", sensordata.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sensordata> sensordataList = sensordataRepository.findAll();
        assertThat(sensordataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
