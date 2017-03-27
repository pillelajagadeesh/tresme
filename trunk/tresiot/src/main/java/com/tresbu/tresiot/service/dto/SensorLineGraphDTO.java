package com.tresbu.tresiot.service.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class SensorLineGraphDTO implements Serializable{
	
	private static final long serialVersionUID = 2430760235989682264L;
	
	
	 private Long applicationId;
	 
	 private Long clientId;
	 
	 private Long sensorId;

	    @NotNull
	    private long fromTime;
	    
	    @NotNull
	    private long toTime;

		

		public Long getApplicationId() {
			return applicationId;
		}

		public void setApplicationId(Long applicationId) {
			this.applicationId = applicationId;
		}

		public long getFromTime() {
			return fromTime;
		}

		public void setFromTime(long fromTime) {
			this.fromTime = fromTime;
		}

		public long getToTime() {
			return toTime;
		}

		public void setToTime(long toTime) {
			this.toTime = toTime;
		}

		public Long getClientId() {
			return clientId;
		}

		public void setClientId(Long clientId) {
			this.clientId = clientId;
		}

		public Long getSensorId() {
			return sensorId;
		}

		public void setSensorId(Long sensorId) {
			this.sensorId = sensorId;
		}
	    
	    
	    

}
