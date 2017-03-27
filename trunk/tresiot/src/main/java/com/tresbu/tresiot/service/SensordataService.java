package com.tresbu.tresiot.service;

import com.tresbu.tresiot.service.dto.SensordataDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Sensordata.
 */
public interface SensordataService {

    /**
     * Save a sensordata.
     *
     * @param sensordataDTO the entity to save
     * @return the persisted entity
     */
    SensordataDTO save(SensordataDTO sensordataDTO);

    /**
     *  Get all the sensordata.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SensordataDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" sensordata.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SensordataDTO findOne(Long id);

    /**
     *  Delete the "id" sensordata.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
