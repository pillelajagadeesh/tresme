package com.tresbu.tresiot.web.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.tresbu.tresiot.domain.SensorLineGraph;
import com.tresbu.tresiot.security.AuthoritiesConstants;
import com.tresbu.tresiot.service.GraphService;
import com.tresbu.tresiot.service.dto.EventBarGraphDTO;
import com.tresbu.tresiot.service.dto.SensorBarGraphDTO;
import com.tresbu.tresiot.service.dto.SensorLineGraphDTO;

@RestController
@RequestMapping("/api")
public class GraphResource {
	
	
	 private final Logger log = LoggerFactory.getLogger(GraphResource.class);
	 
	 @Inject
	 private GraphService graphService;
	 
	 
	 
	    @PostMapping(value = {"/sensorlinegraph"}, produces = MediaType.APPLICATION_JSON_VALUE)
		@Timed
		@Secured(AuthoritiesConstants.ADMIN)
	    public ResponseEntity<List<SensorLineGraph>> sensorLineGraphData(HttpServletRequest request, @RequestBody SensorLineGraphDTO sensorLineGraphDTO) throws URISyntaxException {
	    	log.debug("Rest API to get sensor line graph, applicationId : {}, fromDate : {}, toTime : {}, clientid {}, sensorid: {}",
	    			sensorLineGraphDTO.getApplicationId(), sensorLineGraphDTO.getFromTime(), sensorLineGraphDTO.getToTime(),sensorLineGraphDTO.getClientId(), sensorLineGraphDTO.getSensorId());
			return ResponseEntity.ok(graphService.getSensorLineGraphData(sensorLineGraphDTO));
		}
	 
	 @PostMapping(value = {"/sensorbargraph"}, produces = MediaType.APPLICATION_JSON_VALUE)
		@Timed
		@Secured(AuthoritiesConstants.ADMIN)
	    public ResponseEntity<?> sensorBarGraphData(HttpServletRequest request, @RequestBody SensorBarGraphDTO sensorBarGraphDTO) throws URISyntaxException {
	    	log.debug("Rest API to get sensor bar graph, clientId : {}, application id: {}, client id: {}",sensorBarGraphDTO.getClientId(),sensorBarGraphDTO.getClientId(),
	    			sensorBarGraphDTO.getSensorId());
			return ResponseEntity.ok(graphService.getSensorBarGraphData(sensorBarGraphDTO));
		}

	 @PostMapping(value = {"/eventbargraph"}, produces = MediaType.APPLICATION_JSON_VALUE)
		@Timed
		@Secured(AuthoritiesConstants.ADMIN)
	    public ResponseEntity<?> eventBarGraphData(HttpServletRequest request, @RequestBody EventBarGraphDTO eventBarGraphDTO) throws URISyntaxException {
	    	log.debug("Rest API to get sensor bar graph, clientId : {}, application id {}, event id {}",eventBarGraphDTO.getClientId(), eventBarGraphDTO.getApplicationId(),
	    			eventBarGraphDTO.getEventId());
			return ResponseEntity.ok(graphService.getEventBarGraphData(eventBarGraphDTO));
		}
	 
	 @GetMapping(value = {"/sensorclientidlist"}, produces = MediaType.APPLICATION_JSON_VALUE)
		@Timed
		@Secured(AuthoritiesConstants.ADMIN)
	    public ResponseEntity<?> getSensorClientIdList() {
	    	log.debug("Rest API to get sensor clientId list to show in dropdown");
			return ResponseEntity.ok(graphService.getSensorClientIdList());
		}
	 
	 @GetMapping(value = {"/eventclientidlist"}, produces = MediaType.APPLICATION_JSON_VALUE)
		@Timed
		@Secured(AuthoritiesConstants.ADMIN)
	    public ResponseEntity<?> getEventClientIdList() {
	    	log.debug("Rest API to get event clientId list to show in dropdown");
			return ResponseEntity.ok(graphService.getEventClientIdList());
		}
	 
	 @GetMapping(value = {"/applicationlist"}, produces = MediaType.APPLICATION_JSON_VALUE)
		@Timed
		@Secured(AuthoritiesConstants.ADMIN)
	    public ResponseEntity<?> getApplicationList() {
	    	log.debug("Rest API to get application list to show in dropdown");
			return ResponseEntity.ok(graphService.getApplicationList());
		}
	 
	 @GetMapping(value = {"/sensorlist"}, produces = MediaType.APPLICATION_JSON_VALUE)
		@Timed
		@Secured(AuthoritiesConstants.ADMIN)
	    public ResponseEntity<?> getSensorList() {
	    	log.debug("Rest API to get sensor list to show in dropdown");
			return ResponseEntity.ok(graphService.getSenorList());
		}
	 
	 @GetMapping(value = {"/eventlist"}, produces = MediaType.APPLICATION_JSON_VALUE)
		@Timed
		@Secured(AuthoritiesConstants.ADMIN)
	    public ResponseEntity<?> getEventList() {
	    	log.debug("Rest API to get event list to show in dropdown");
			return ResponseEntity.ok(graphService.getEventList());
		}
	 
	 
}
