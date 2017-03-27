package com.tresbu.tresiot.domain;

import java.io.Serializable;

public class SensorLineGraph implements  Serializable{

	
	private static final long serialVersionUID = 677752000145846804L;
	public SensorLineGraph(){
		
	}
	
	public SensorLineGraph( Long createTime, String sensorName, Long sensorValue) {
		super();
		this.createTime = createTime;
		this.sensorName = sensorName;
		this.sensorValue = sensorValue;
		
	}
	private Long createTime;
	private String sensorName;
	private Long sensorValue;

	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getSensorName() {
		return sensorName;
	}
	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public Long getSensorValue() {
		return sensorValue;
	}

	public void setSensorValue(Long sensorValue) {
		this.sensorValue = sensorValue;
	}
	
	
	

}
