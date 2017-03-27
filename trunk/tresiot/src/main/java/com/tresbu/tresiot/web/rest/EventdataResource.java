package com.tresbu.tresiot.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tresbu.tresiot.service.EventdataService;
import com.tresbu.tresiot.web.rest.util.HeaderUtil;
import com.tresbu.tresiot.web.rest.util.PaginationUtil;
import com.tresbu.tresiot.service.dto.EventdataDTO;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Eventdata.
 */
@RestController
@RequestMapping("/api")
public class EventdataResource {

    private final Logger log = LoggerFactory.getLogger(EventdataResource.class);
        
    @Inject
    private EventdataService eventdataService;

    /**
     * POST  /eventdata : Create a new eventdata.
     *
     * @param eventdataDTO the eventdataDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eventdataDTO, or with status 400 (Bad Request) if the eventdata has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/eventdata")
    @Timed
    public ResponseEntity<EventdataDTO> createEventdata(@Valid @RequestBody EventdataDTO eventdataDTO) throws URISyntaxException {
        log.debug("REST request to save Eventdata : {}", eventdataDTO);
        if (eventdataDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("eventdata", "idexists", "A new eventdata cannot already have an ID")).body(null);
        }
        EventdataDTO result = eventdataService.save(eventdataDTO);
        return ResponseEntity.created(new URI("/api/eventdata/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("eventdata", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eventdata : Updates an existing eventdata.
     *
     * @param eventdataDTO the eventdataDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eventdataDTO,
     * or with status 400 (Bad Request) if the eventdataDTO is not valid,
     * or with status 500 (Internal Server Error) if the eventdataDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/eventdata")
    @Timed
    public ResponseEntity<EventdataDTO> updateEventdata(@Valid @RequestBody EventdataDTO eventdataDTO) throws URISyntaxException {
        log.debug("REST request to update Eventdata : {}", eventdataDTO);
        if (eventdataDTO.getId() == null) {
            return createEventdata(eventdataDTO);
        }
        EventdataDTO result = eventdataService.save(eventdataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("eventdata", eventdataDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eventdata : get all the eventdata.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of eventdata in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/eventdata")
    @Timed
    public ResponseEntity<List<EventdataDTO>> getAllEventdata(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Eventdata");
        Page<EventdataDTO> page = eventdataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eventdata");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /eventdata/:id : get the "id" eventdata.
     *
     * @param id the id of the eventdataDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eventdataDTO, or with status 404 (Not Found)
     */
    @GetMapping("/eventdata/{id}")
    @Timed
    public ResponseEntity<EventdataDTO> getEventdata(@PathVariable Long id) {
        log.debug("REST request to get Eventdata : {}", id);
        EventdataDTO eventdataDTO = eventdataService.findOne(id);
        return Optional.ofNullable(eventdataDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /eventdata/:id : delete the "id" eventdata.
     *
     * @param id the id of the eventdataDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/eventdata/{id}")
    @Timed
    public ResponseEntity<Void> deleteEventdata(@PathVariable Long id) {
        log.debug("REST request to delete Eventdata : {}", id);
        eventdataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("eventdata", id.toString())).build();
    }

}
