package com.tresbu.tresiot.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tresbu.tresiot.service.ApplicationeventService;
import com.tresbu.tresiot.web.rest.util.HeaderUtil;
import com.tresbu.tresiot.web.rest.util.PaginationUtil;
import com.tresbu.tresiot.service.dto.ApplicationeventDTO;

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
 * REST controller for managing Applicationevent.
 */
@RestController
@RequestMapping("/api")
public class ApplicationeventResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationeventResource.class);
        
    @Inject
    private ApplicationeventService applicationeventService;

    /**
     * POST  /applicationevents : Create a new applicationevent.
     *
     * @param applicationeventDTO the applicationeventDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicationeventDTO, or with status 400 (Bad Request) if the applicationevent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/applicationevents")
    @Timed
    public ResponseEntity<ApplicationeventDTO> createApplicationevent(@Valid @RequestBody ApplicationeventDTO applicationeventDTO) throws URISyntaxException {
        log.debug("REST request to save Applicationevent : {}", applicationeventDTO);
        if (applicationeventDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("applicationevent", "idexists", "A new applicationevent cannot already have an ID")).body(null);
        }
        ApplicationeventDTO result = applicationeventService.save(applicationeventDTO);
        return ResponseEntity.created(new URI("/api/applicationevents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("applicationevent", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /applicationevents : Updates an existing applicationevent.
     *
     * @param applicationeventDTO the applicationeventDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicationeventDTO,
     * or with status 400 (Bad Request) if the applicationeventDTO is not valid,
     * or with status 500 (Internal Server Error) if the applicationeventDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/applicationevents")
    @Timed
    public ResponseEntity<ApplicationeventDTO> updateApplicationevent(@Valid @RequestBody ApplicationeventDTO applicationeventDTO) throws URISyntaxException {
        log.debug("REST request to update Applicationevent : {}", applicationeventDTO);
        if (applicationeventDTO.getId() == null) {
            return createApplicationevent(applicationeventDTO);
        }
        ApplicationeventDTO result = applicationeventService.save(applicationeventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("applicationevent", applicationeventDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /applicationevents : get all the applicationevents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of applicationevents in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/applicationevents")
    @Timed
    public ResponseEntity<List<ApplicationeventDTO>> getAllApplicationevents(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Applicationevents");
        Page<ApplicationeventDTO> page = applicationeventService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/applicationevents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /applicationevents/:id : get the "id" applicationevent.
     *
     * @param id the id of the applicationeventDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationeventDTO, or with status 404 (Not Found)
     */
    @GetMapping("/applicationevents/{id}")
    @Timed
    public ResponseEntity<ApplicationeventDTO> getApplicationevent(@PathVariable Long id) {
        log.debug("REST request to get Applicationevent : {}", id);
        ApplicationeventDTO applicationeventDTO = applicationeventService.findOne(id);
        return Optional.ofNullable(applicationeventDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /applicationevents/:id : delete the "id" applicationevent.
     *
     * @param id the id of the applicationeventDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/applicationevents/{id}")
    @Timed
    public ResponseEntity<Void> deleteApplicationevent(@PathVariable Long id) {
        log.debug("REST request to delete Applicationevent : {}", id);
        applicationeventService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("applicationevent", id.toString())).build();
    }

}
