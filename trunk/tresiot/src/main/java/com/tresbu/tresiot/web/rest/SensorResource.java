package com.tresbu.tresiot.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tresbu.tresiot.service.SensorService;
import com.tresbu.tresiot.web.rest.util.HeaderUtil;
import com.tresbu.tresiot.web.rest.util.PaginationUtil;
import com.tresbu.tresiot.service.dto.SensorDTO;

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
 * REST controller for managing Sensor.
 */
@RestController
@RequestMapping("/api")
public class SensorResource {

    private final Logger log = LoggerFactory.getLogger(SensorResource.class);
        
    @Inject
    private SensorService sensorService;

    /**
     * POST  /sensors : Create a new sensor.
     *
     * @param sensorDTO the sensorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sensorDTO, or with status 400 (Bad Request) if the sensor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sensors")
    @Timed
    public ResponseEntity<SensorDTO> createSensor(@Valid @RequestBody SensorDTO sensorDTO) throws URISyntaxException {
        log.debug("REST request to save Sensor : {}", sensorDTO);
        if (sensorDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sensor", "idexists", "A new sensor cannot already have an ID")).body(null);
        }
        SensorDTO result = sensorService.save(sensorDTO);
        return ResponseEntity.created(new URI("/api/sensors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sensor", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sensors : Updates an existing sensor.
     *
     * @param sensorDTO the sensorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sensorDTO,
     * or with status 400 (Bad Request) if the sensorDTO is not valid,
     * or with status 500 (Internal Server Error) if the sensorDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sensors")
    @Timed
    public ResponseEntity<SensorDTO> updateSensor(@Valid @RequestBody SensorDTO sensorDTO) throws URISyntaxException {
        log.debug("REST request to update Sensor : {}", sensorDTO);
        if (sensorDTO.getId() == null) {
            return createSensor(sensorDTO);
        }
        SensorDTO result = sensorService.save(sensorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sensor", sensorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sensors : get all the sensors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sensors in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sensors")
    @Timed
    public ResponseEntity<List<SensorDTO>> getAllSensors(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Sensors");
        Page<SensorDTO> page = sensorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sensors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sensors/:id : get the "id" sensor.
     *
     * @param id the id of the sensorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sensorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sensors/{id}")
    @Timed
    public ResponseEntity<SensorDTO> getSensor(@PathVariable Long id) {
        log.debug("REST request to get Sensor : {}", id);
        SensorDTO sensorDTO = sensorService.findOne(id);
        return Optional.ofNullable(sensorDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sensors/:id : delete the "id" sensor.
     *
     * @param id the id of the sensorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sensors/{id}")
    @Timed
    public ResponseEntity<Void> deleteSensor(@PathVariable Long id) {
        log.debug("REST request to delete Sensor : {}", id);
        sensorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sensor", id.toString())).build();
    }

}
