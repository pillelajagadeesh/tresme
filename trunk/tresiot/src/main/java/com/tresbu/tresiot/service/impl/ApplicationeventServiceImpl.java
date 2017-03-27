package com.tresbu.tresiot.service.impl;

import com.tresbu.tresiot.service.ApplicationeventService;
import com.tresbu.tresiot.domain.Applicationevent;
import com.tresbu.tresiot.repository.ApplicationeventRepository;
import com.tresbu.tresiot.service.dto.ApplicationeventDTO;
import com.tresbu.tresiot.service.mapper.ApplicationeventMapper;
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
 * Service Implementation for managing Applicationevent.
 */
@Service
@Transactional
public class ApplicationeventServiceImpl implements ApplicationeventService{

    private final Logger log = LoggerFactory.getLogger(ApplicationeventServiceImpl.class);
    
    @Inject
    private ApplicationeventRepository applicationeventRepository;

    @Inject
    private ApplicationeventMapper applicationeventMapper;

    /**
     * Save a applicationevent.
     *
     * @param applicationeventDTO the entity to save
     * @return the persisted entity
     */
    public ApplicationeventDTO save(ApplicationeventDTO applicationeventDTO) {
        log.debug("Request to save Applicationevent : {}", applicationeventDTO);
        Applicationevent applicationevent = applicationeventMapper.applicationeventDTOToApplicationevent(applicationeventDTO);
        applicationevent = applicationeventRepository.save(applicationevent);
        ApplicationeventDTO result = applicationeventMapper.applicationeventToApplicationeventDTO(applicationevent);
        return result;
    }

    /**
     *  Get all the applicationevents.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ApplicationeventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Applicationevents");
        Page<Applicationevent> result = applicationeventRepository.findAll(pageable);
        return result.map(applicationevent -> applicationeventMapper.applicationeventToApplicationeventDTO(applicationevent));
    }

    /**
     *  Get one applicationevent by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ApplicationeventDTO findOne(Long id) {
        log.debug("Request to get Applicationevent : {}", id);
        Applicationevent applicationevent = applicationeventRepository.findOne(id);
        ApplicationeventDTO applicationeventDTO = applicationeventMapper.applicationeventToApplicationeventDTO(applicationevent);
        return applicationeventDTO;
    }

    /**
     *  Delete the  applicationevent by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Applicationevent : {}", id);
        applicationeventRepository.delete(id);
    }
}
