package com.tresbu.tresiot.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tresbu.tresiot.service.ApplicationviewService;
import com.tresbu.tresiot.web.rest.util.HeaderUtil;
import com.tresbu.tresiot.web.rest.util.PaginationUtil;
import com.tresbu.tresiot.service.dto.ApplicationviewDTO;

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
 * REST controller for managing Applicationview.
 */
@RestController
@RequestMapping("/api")
public class ApplicationviewResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationviewResource.class);
        
    @Inject
    private ApplicationviewService applicationviewService;

    /**
     * POST  /applicationviews : Create a new applicationview.
     *
     * @param applicationviewDTO the applicationviewDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicationviewDTO, or with status 400 (Bad Request) if the applicationview has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/applicationviews")
    @Timed
    public ResponseEntity<ApplicationviewDTO> createApplicationview(@Valid @RequestBody ApplicationviewDTO applicationviewDTO) throws URISyntaxException {
        log.debug("REST request to save Applicationview : {}", applicationviewDTO);
        if (applicationviewDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("applicationview", "idexists", "A new applicationview cannot already have an ID")).body(null);
        }
        ApplicationviewDTO result = applicationviewService.save(applicationviewDTO);
        return ResponseEntity.created(new URI("/api/applicationviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("applicationview", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /applicationviews : Updates an existing applicationview.
     *
     * @param applicationviewDTO the applicationviewDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicationviewDTO,
     * or with status 400 (Bad Request) if the applicationviewDTO is not valid,
     * or with status 500 (Internal Server Error) if the applicationviewDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/applicationviews")
    @Timed
    public ResponseEntity<ApplicationviewDTO> updateApplicationview(@Valid @RequestBody ApplicationviewDTO applicationviewDTO) throws URISyntaxException {
        log.debug("REST request to update Applicationview : {}", applicationviewDTO);
        if (applicationviewDTO.getId() == null) {
            return createApplicationview(applicationviewDTO);
        }
        ApplicationviewDTO result = applicationviewService.save(applicationviewDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("applicationview", applicationviewDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /applicationviews : get all the applicationviews.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of applicationviews in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/applicationviews")
    @Timed
    public ResponseEntity<List<ApplicationviewDTO>> getAllApplicationviews(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Applicationviews");
        Page<ApplicationviewDTO> page = applicationviewService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/applicationviews");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /applicationviews/:id : get the "id" applicationview.
     *
     * @param id the id of the applicationviewDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationviewDTO, or with status 404 (Not Found)
     */
    @GetMapping("/applicationviews/{id}")
    @Timed
    public ResponseEntity<ApplicationviewDTO> getApplicationview(@PathVariable Long id) {
        log.debug("REST request to get Applicationview : {}", id);
        ApplicationviewDTO applicationviewDTO = applicationviewService.findOne(id);
        return Optional.ofNullable(applicationviewDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /applicationviews/:id : delete the "id" applicationview.
     *
     * @param id the id of the applicationviewDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/applicationviews/{id}")
    @Timed
    public ResponseEntity<Void> deleteApplicationview(@PathVariable Long id) {
        log.debug("REST request to delete Applicationview : {}", id);
        applicationviewService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("applicationview", id.toString())).build();
    }

}
