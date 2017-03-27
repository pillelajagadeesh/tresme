package com.tresbu.tresiot.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class GraphDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	
	private List<Map<String, Object>> sensorBarData;
	
	private List<Map<String, Object>> eventBarData;

	

	public List<Map<String, Object>> getSensorBarData() {
		return sensorBarData;
	}

	public void setSensorBarData(List<Map<String, Object>> sensorBarData) {
		this.sensorBarData = sensorBarData;
	}

	public List<Map<String, Object>> getEventBarData() {
		return eventBarData;
	}

	public void setEventBarData(List<Map<String, Object>> eventBarData) {
		this.eventBarData = eventBarData;
	}

	
	

}
