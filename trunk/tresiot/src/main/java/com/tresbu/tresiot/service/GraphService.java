package com.tresbu.tresiot.service;

import java.util.List;



import com.tresbu.tresiot.domain.Application;
import com.tresbu.tresiot.domain.Applicationevent;
import com.tresbu.tresiot.domain.Sensor;
import com.tresbu.tresiot.domain.SensorLineGraph;
import com.tresbu.tresiot.service.dto.EventBarGraphDTO;
import com.tresbu.tresiot.service.dto.GraphDTO;
import com.tresbu.tresiot.service.dto.SensorBarGraphDTO;
import com.tresbu.tresiot.service.dto.SensorLineGraphDTO;


public interface GraphService {
	
	public List<SensorLineGraph> getSensorLineGraphData(SensorLineGraphDTO sensorLineGraphDTO);
	
	public GraphDTO getSensorBarGraphData(SensorBarGraphDTO sensorBarGraphDTO);
	
	public GraphDTO getEventBarGraphData(EventBarGraphDTO eventBarGraphDTO);
	
	public List<String> getSensorClientIdList();
	
	public List<String> getEventClientIdList();
	
	public List<Application> getApplicationList();
	
	public List<Sensor> getSenorList();
	
	public List<Applicationevent> getEventList();
	
	

}
