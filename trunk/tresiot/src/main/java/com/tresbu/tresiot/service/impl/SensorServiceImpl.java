package com.tresbu.tresiot.service.impl;

import com.tresbu.tresiot.service.SensorService;
import com.tresbu.tresiot.domain.Sensor;
import com.tresbu.tresiot.repository.SensorRepository;
import com.tresbu.tresiot.service.dto.SensorDTO;
import com.tresbu.tresiot.service.mapper.SensorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Sensor.
 */
@Service
@Transactional
public class SensorServiceImpl implements SensorService{

    private final Logger log = LoggerFactory.getLogger(SensorServiceImpl.class);
    
    @Inject
    private SensorRepository sensorRepository;

    @Inject
    private SensorMapper sensorMapper;

    /**
     * Save a sensor.
     *
     * @param sensorDTO the entity to save
     * @return the persisted entity
     */
    public SensorDTO save(SensorDTO sensorDTO) {
        log.debug("Request to save Sensor : {}", sensorDTO);
        Sensor sensor = sensorMapper.sensorDTOToSensor(sensorDTO);
        sensor = sensorRepository.save(sensor);
        SensorDTO result = sensorMapper.sensorToSensorDTO(sensor);
        return result;
    }

    /**
     *  Get all the sensors.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SensorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sensors");
        Page<Sensor> result = sensorRepository.findAll(pageable);
        return result.map(sensor -> sensorMapper.sensorToSensorDTO(sensor));
    }

    /**
     *  Get one sensor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SensorDTO findOne(Long id) {
        log.debug("Request to get Sensor : {}", id);
        Sensor sensor = sensorRepository.findOne(id);
        SensorDTO sensorDTO = sensorMapper.sensorToSensorDTO(sensor);
        return sensorDTO;
    }

    /**
     *  Delete the  sensor by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Sensor : {}", id);
        sensorRepository.delete(id);
    }
}
