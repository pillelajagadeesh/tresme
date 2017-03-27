package com.tresbu.tresiot.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tresbu.tresiot.domain.Application;
import com.tresbu.tresiot.domain.Applicationevent;
import com.tresbu.tresiot.domain.Sensor;
import com.tresbu.tresiot.domain.SensorLineGraph;
import com.tresbu.tresiot.domain.Sensordata;
import com.tresbu.tresiot.repository.ApplicationRepository;
import com.tresbu.tresiot.repository.ApplicationeventRepository;
import com.tresbu.tresiot.repository.AuthorityRepository;
import com.tresbu.tresiot.repository.EventdataRepository;
import com.tresbu.tresiot.repository.SensorRepository;
import com.tresbu.tresiot.repository.SensordataRepository;
import com.tresbu.tresiot.repository.UserRepository;
import com.tresbu.tresiot.service.GraphService;
import com.tresbu.tresiot.service.dto.EventBarGraphDTO;
import com.tresbu.tresiot.service.dto.GraphDTO;
import com.tresbu.tresiot.service.dto.SensorBarGraphDTO;
import com.tresbu.tresiot.service.dto.SensorLineGraphDTO;
import com.tresbu.tresiot.web.rest.GraphResource;

@Service
@Transactional
public class GraphServiceImpl implements GraphService{
	
	 private final Logger log = LoggerFactory.getLogger(GraphServiceImpl.class);
	
	 @Inject
	    private SensorRepository sensorRepository;
	 
	 @Inject
	    private SensordataRepository sensordataRepository;
	 
	 @Inject
	    private ApplicationeventRepository applicationeventRepository;
	 
	 @Inject
	    private ApplicationRepository applicationRepository;
	 
	 @Inject
	    private EventdataRepository eventdataRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<SensorLineGraph> getSensorLineGraphData(SensorLineGraphDTO sensorLineGraphDTO) {
		log.debug("Query to fetch sensor data for applicationId {}, fromTime : {}, toTime : {}, client id{}, sensor id{}", sensorLineGraphDTO.getApplicationId(),
				sensorLineGraphDTO.getFromTime(), sensorLineGraphDTO.getToTime(),sensorLineGraphDTO.getClientId(), sensorLineGraphDTO.getSensorId());
		List<SensorLineGraph> findSensordataByApplicationId = sensordataRepository.findSensordataByApplicationId(sensorLineGraphDTO.getApplicationId(),
				sensorLineGraphDTO.getFromTime(), sensorLineGraphDTO.getToTime(),sensorLineGraphDTO.getClientId(), sensorLineGraphDTO.getSensorId());
		log.debug("Number of sensor date are [" + findSensordataByApplicationId.size() + "]");
		return findSensordataByApplicationId;
		
		/*SELECT sd.id,sd.created_time,sd.value, s.name FROM tresiot.tresiot_sensordata sd, tresiot.tresiot_sensor s
where s.id= sd.sensor_id and application_id=1002 and created_time between 1482928936354 and 1482929012291 ;*/
	}
	

	@Override
	@Transactional(readOnly = true)
	public GraphDTO getSensorBarGraphData(SensorBarGraphDTO sensorBarGraphDTO) {
		log.debug("Query to fetch sensor for clientId {}, application id {}, client id {}", sensorBarGraphDTO.getClientId(),sensorBarGraphDTO.getApplicationId(), 
				sensorBarGraphDTO.getSensorId());
		List<Sensor> findSensorByClientId = sensorRepository.findSensorByClientId(sensorBarGraphDTO.getClientId(), sensorBarGraphDTO.getApplicationId(),sensorBarGraphDTO.getSensorId());
		 List<Map<String, Object>> sensorDataList=new LinkedList<>();
		for (Sensor sensor : findSensorByClientId) {
			log.debug("Query to fetch sensor data count for sensor {}", sensor.getId());
			Long sensorDataCountBySensor = sensordataRepository.sensorDataCountBySensor(sensor.getId());
			Map<String, Object> sensorData = new HashMap<>();
			sensorData.put("sensorlabel", sensor.getName());
			sensorData.put("sensorcount",sensorDataCountBySensor);
			sensorDataList.add(sensorData);
		}
		 GraphDTO graphDTO= new GraphDTO();
		 graphDTO.setSensorBarData(sensorDataList);
		return graphDTO;
	}
	
	@Override
	@Transactional(readOnly = true)
	public GraphDTO getEventBarGraphData(EventBarGraphDTO eventBarGraphDTO) {
		log.debug("Query to fetch application event for clientId {}, application id {}, event id {}", eventBarGraphDTO.getClientId(),eventBarGraphDTO.getApplicationId(),
				eventBarGraphDTO.getEventId());
		List<Applicationevent> findEventByClientId = applicationeventRepository.findEventByClientId(eventBarGraphDTO.getClientId(), eventBarGraphDTO.getApplicationId(),
				eventBarGraphDTO.getEventId());
		 List<Map<String, Object>> eventDataList=new LinkedList<>();
		for (Applicationevent event : findEventByClientId) {
			log.debug("Query to fetch event data count for application eventId {}", event.getId());
			Long eventDataCountByEvent = eventdataRepository.eventDataCountByEvent(event.getId());
			Map<String, Object> eventData = new HashMap<>();
			eventData.put("eventlabel", event.getName());
			eventData.put("eventcount",eventDataCountByEvent);
			eventDataList.add(eventData);
		}
		 GraphDTO graphDTO= new GraphDTO();
		 graphDTO.setEventBarData(eventDataList);
		return graphDTO;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<String> getSensorClientIdList() {
		log.debug("Query to fetch sensor clientID list");
		List<String> findSensorClientList = sensordataRepository.findSensorClientIdList();
		return findSensorClientList;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<String> getEventClientIdList() {
		log.debug("Query to fetch event clientID list");
		List<String> findEventClientList = eventdataRepository.findEventClientIdList();
		return findEventClientList;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Application> getApplicationList() {
		log.debug("Query to fetch application list");
		List<Application> findApplicationList = applicationRepository.findApplicationList();
		return findApplicationList;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Sensor> getSenorList() {
		log.debug("Query to fetch sensor list");
		List<Sensor> findSenorList = sensorRepository.findSensorList();
		return findSenorList;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Applicationevent> getEventList() {
		log.debug("Query to fetch event list");
		List<Applicationevent> findSenorList = applicationeventRepository.findApplicationeventList();
		return findSenorList;
	}
	
	
}
