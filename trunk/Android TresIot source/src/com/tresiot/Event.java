package com.tresiot;

import java.io.Serializable;

public class Event implements Serializable{
	
	String clientid;
	String clientos;
	String devicemake;
	String appversion;
	String location;
	long applicationId;
	long applicationeventId;
	long applicationviewId;
	long sessionduration;
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getClientos() {
		return clientos;
	}
	public void setClientos(String clientos) {
		this.clientos = clientos;
	}
	public String getDevicemake() {
		return devicemake;
	}
	public void setDevicemake(String devicemake) {
		this.devicemake = devicemake;
	}
	public String getAppversion() {
		return appversion;
	}
	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}
	public long getApplicationeventId() {
		return applicationeventId;
	}
	public void setApplicationeventId(long applicationeventId) {
		this.applicationeventId = applicationeventId;
	}
	public long getApplicationviewId() {
		return applicationviewId;
	}
	public void setApplicationviewId(long applicationviewId) {
		this.applicationviewId = applicationviewId;
	}
	public long getSessionduration() {
		return sessionduration;
	}
	public void setSessionduration(long sessionduration) {
		this.sessionduration = sessionduration;
	}

	
	
}
