package com.tresiot;

import java.io.Serializable;

public class ResponseSensor extends SensorData implements Serializable{
	
	long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ResponseEvent [id=" + id + "]";
	}
	
	
	
}
