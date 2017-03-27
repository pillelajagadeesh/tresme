package com.tresbu.tresiot.service.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class EventBarGraphDTO implements Serializable{
	
	private static final long serialVersionUID = 2430760235989682264L;
	
	 
	private String clientId;
	
	private String applicationId;
	
	private String eventId;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	 
	 
	 
	
	

}
