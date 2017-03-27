package com.tresbu.tresiot.service.impl;

import com.tresbu.tresiot.service.ApplicationviewService;
import com.tresbu.tresiot.domain.Applicationview;
import com.tresbu.tresiot.repository.ApplicationviewRepository;
import com.tresbu.tresiot.service.dto.ApplicationviewDTO;
import com.tresbu.tresiot.service.mapper.ApplicationviewMapper;
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
 * Service Implementation for managing Applicationview.
 */
@Service
@Transactional
public class ApplicationviewServiceImpl implements ApplicationviewService{

    private final Logger log = LoggerFactory.getLogger(ApplicationviewServiceImpl.class);
    
    @Inject
    private ApplicationviewRepository applicationviewRepository;

    @Inject
    private ApplicationviewMapper applicationviewMapper;

    /**
     * Save a applicationview.
     *
     * @param applicationviewDTO the entity to save
     * @return the persisted entity
     */
    public ApplicationviewDTO save(ApplicationviewDTO applicationviewDTO) {
        log.debug("Request to save Applicationview : {}", applicationviewDTO);
        Applicationview applicationview = applicationviewMapper.applicationviewDTOToApplicationview(applicationviewDTO);
        applicationview = applicationviewRepository.save(applicationview);
        ApplicationviewDTO result = applicationviewMapper.applicationviewToApplicationviewDTO(applicationview);
        return result;
    }

    /**
     *  Get all the applicationviews.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ApplicationviewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Applicationviews");
        Page<Applicationview> result = applicationviewRepository.findAll(pageable);
        return result.map(applicationview -> applicationviewMapper.applicationviewToApplicationviewDTO(applicationview));
    }

    /**
     *  Get one applicationview by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ApplicationviewDTO findOne(Long id) {
        log.debug("Request to get Applicationview : {}", id);
        Applicationview applicationview = applicationviewRepository.findOne(id);
        ApplicationviewDTO applicationviewDTO = applicationviewMapper.applicationviewToApplicationviewDTO(applicationview);
        return applicationviewDTO;
    }

    /**
     *  Delete the  applicationview by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Applicationview : {}", id);
        applicationviewRepository.delete(id);
    }
}
