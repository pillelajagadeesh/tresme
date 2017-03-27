package com.tresbu.tresiot.service;

import com.tresbu.tresiot.service.dto.ApplicationeventDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Applicationevent.
 */
public interface ApplicationeventService {

    /**
     * Save a applicationevent.
     *
     * @param applicationeventDTO the entity to save
     * @return the persisted entity
     */
    ApplicationeventDTO save(ApplicationeventDTO applicationeventDTO);

    /**
     *  Get all the applicationevents.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ApplicationeventDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" applicationevent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ApplicationeventDTO findOne(Long id);

    /**
     *  Delete the "id" applicationevent.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
