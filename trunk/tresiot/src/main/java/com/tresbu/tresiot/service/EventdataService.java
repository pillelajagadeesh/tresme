package com.tresbu.tresiot.service;

import com.tresbu.tresiot.service.dto.EventdataDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Eventdata.
 */
public interface EventdataService {

    /**
     * Save a eventdata.
     *
     * @param eventdataDTO the entity to save
     * @return the persisted entity
     */
    EventdataDTO save(EventdataDTO eventdataDTO);

    /**
     *  Get all the eventdata.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EventdataDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" eventdata.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EventdataDTO findOne(Long id);

    /**
     *  Delete the "id" eventdata.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
