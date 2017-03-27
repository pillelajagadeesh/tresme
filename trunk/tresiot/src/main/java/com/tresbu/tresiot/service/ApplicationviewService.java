package com.tresbu.tresiot.service;

import com.tresbu.tresiot.service.dto.ApplicationviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Applicationview.
 */
public interface ApplicationviewService {

    /**
     * Save a applicationview.
     *
     * @param applicationviewDTO the entity to save
     * @return the persisted entity
     */
    ApplicationviewDTO save(ApplicationviewDTO applicationviewDTO);

    /**
     *  Get all the applicationviews.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ApplicationviewDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" applicationview.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ApplicationviewDTO findOne(Long id);

    /**
     *  Delete the "id" applicationview.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
