package com.tresbu.tresiot.service.impl;

import com.tresbu.tresiot.service.SensordataService;
import com.tresbu.tresiot.domain.Sensordata;
import com.tresbu.tresiot.repository.SensordataRepository;
import com.tresbu.tresiot.service.dto.SensordataDTO;
import com.tresbu.tresiot.service.mapper.SensordataMapper;
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
 * Service Implementation for managing Sensordata.
 */
@Service
@Transactional
public class SensordataServiceImpl implements SensordataService{

    private final Logger log = LoggerFactory.getLogger(SensordataServiceImpl.class);
    
    @Inject
    private SensordataRepository sensordataRepository;

    @Inject
    private SensordataMapper sensordataMapper;

    /**
     * Save a sensordata.
     *
     * @param sensordataDTO the entity to save
     * @return the persisted entity
     */
    public SensordataDTO save(SensordataDTO sensordataDTO) {
        log.debug("Request to save Sensordata : {}", sensordataDTO);
        Sensordata sensordata = sensordataMapper.sensordataDTOToSensordata(sensordataDTO);
        sensordata = sensordataRepository.save(sensordata);
        SensordataDTO result = sensordataMapper.sensordataToSensordataDTO(sensordata);
        return result;
    }

    /**
     *  Get all the sensordata.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SensordataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sensordata");
        Page<Sensordata> result = sensordataRepository.findAll(pageable);
        return result.map(sensordata -> sensordataMapper.sensordataToSensordataDTO(sensordata));
    }

    /**
     *  Get one sensordata by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SensordataDTO findOne(Long id) {
        log.debug("Request to get Sensordata : {}", id);
        Sensordata sensordata = sensordataRepository.findOne(id);
        SensordataDTO sensordataDTO = sensordataMapper.sensordataToSensordataDTO(sensordata);
        return sensordataDTO;
    }

    /**
     *  Delete the  sensordata by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Sensordata : {}", id);
        sensordataRepository.delete(id);
    }
}
