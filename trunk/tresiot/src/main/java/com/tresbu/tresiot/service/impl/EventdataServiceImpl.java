package com.tresbu.tresiot.service.impl;

import com.tresbu.tresiot.service.EventdataService;
import com.tresbu.tresiot.domain.Eventdata;
import com.tresbu.tresiot.repository.EventdataRepository;
import com.tresbu.tresiot.service.dto.EventdataDTO;
import com.tresbu.tresiot.service.mapper.EventdataMapper;
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
 * Service Implementation for managing Eventdata.
 */
@Service
@Transactional
public class EventdataServiceImpl implements EventdataService{

    private final Logger log = LoggerFactory.getLogger(EventdataServiceImpl.class);
    
    @Inject
    private EventdataRepository eventdataRepository;

    @Inject
    private EventdataMapper eventdataMapper;

    /**
     * Save a eventdata.
     *
     * @param eventdataDTO the entity to save
     * @return the persisted entity
     */
    public EventdataDTO save(EventdataDTO eventdataDTO) {
        log.debug("Request to save Eventdata : {}", eventdataDTO);
        Eventdata eventdata = eventdataMapper.eventdataDTOToEventdata(eventdataDTO);
        eventdata = eventdataRepository.save(eventdata);
        EventdataDTO result = eventdataMapper.eventdataToEventdataDTO(eventdata);
        return result;
    }

    /**
     *  Get all the eventdata.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<EventdataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Eventdata");
        Page<Eventdata> result = eventdataRepository.findAll(pageable);
        return result.map(eventdata -> eventdataMapper.eventdataToEventdataDTO(eventdata));
    }

    /**
     *  Get one eventdata by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public EventdataDTO findOne(Long id) {
        log.debug("Request to get Eventdata : {}", id);
        Eventdata eventdata = eventdataRepository.findOne(id);
        EventdataDTO eventdataDTO = eventdataMapper.eventdataToEventdataDTO(eventdata);
        return eventdataDTO;
    }

    /**
     *  Delete the  eventdata by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Eventdata : {}", id);
        eventdataRepository.delete(id);
    }
}
