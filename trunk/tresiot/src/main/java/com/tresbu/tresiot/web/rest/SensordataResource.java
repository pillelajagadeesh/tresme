package com.tresbu.tresiot.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tresbu.tresiot.service.SensordataService;
import com.tresbu.tresiot.web.rest.util.HeaderUtil;
import com.tresbu.tresiot.web.rest.util.PaginationUtil;
import com.tresbu.tresiot.service.dto.SensordataDTO;

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
 * REST controller for managing Sensordata.
 */
@RestController
@RequestMapping("/api")
public class SensordataResource {

    private final Logger log = LoggerFactory.getLogger(SensordataResource.class);
        
    @Inject
    private SensordataService sensordataService;

    /**
     * POST  /sensordata : Create a new sensordata.
     *
     * @param sensordataDTO the sensordataDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sensordataDTO, or with status 400 (Bad Request) if the sensordata has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sensordata")
    @Timed
    public ResponseEntity<SensordataDTO> createSensordata(@Valid @RequestBody SensordataDTO sensordataDTO) throws URISyntaxException {
        log.debug("REST request to save Sensordata : {}", sensordataDTO);
        if (sensordataDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sensordata", "idexists", "A new sensordata cannot already have an ID")).body(null);
        }
        SensordataDTO result = sensordataService.save(sensordataDTO);
        return ResponseEntity.created(new URI("/api/sensordata/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sensordata", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sensordata : Updates an existing sensordata.
     *
     * @param sensordataDTO the sensordataDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sensordataDTO,
     * or with status 400 (Bad Request) if the sensordataDTO is not valid,
     * or with status 500 (Internal Server Error) if the sensordataDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sensordata")
    @Timed
    public ResponseEntity<SensordataDTO> updateSensordata(@Valid @RequestBody SensordataDTO sensordataDTO) throws URISyntaxException {
        log.debug("REST request to update Sensordata : {}", sensordataDTO);
        if (sensordataDTO.getId() == null) {
            return createSensordata(sensordataDTO);
        }
        SensordataDTO result = sensordataService.save(sensordataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sensordata", sensordataDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sensordata : get all the sensordata.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sensordata in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sensordata")
    @Timed
    public ResponseEntity<List<SensordataDTO>> getAllSensordata(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Sensordata");
        Page<SensordataDTO> page = sensordataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sensordata");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sensordata/:id : get the "id" sensordata.
     *
     * @param id the id of the sensordataDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sensordataDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sensordata/{id}")
    @Timed
    public ResponseEntity<SensordataDTO> getSensordata(@PathVariable Long id) {
        log.debug("REST request to get Sensordata : {}", id);
        SensordataDTO sensordataDTO = sensordataService.findOne(id);
        return Optional.ofNullable(sensordataDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sensordata/:id : delete the "id" sensordata.
     *
     * @param id the id of the sensordataDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sensordata/{id}")
    @Timed
    public ResponseEntity<Void> deleteSensordata(@PathVariable Long id) {
        log.debug("REST request to delete Sensordata : {}", id);
        sensordataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sensordata", id.toString())).build();
    }

}
