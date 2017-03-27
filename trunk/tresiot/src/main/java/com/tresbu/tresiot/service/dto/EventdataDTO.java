package com.tresbu.tresiot.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Eventdata entity.
 */
public class EventdataDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String clientid;

    private String clientos;

    private String devicemake;

    private String appversion;

    private Long sessionduration;

    private String location;


    private Long applicationId;
    
    private Long applicationeventId;
    
    private Long applicationviewId;
    
    private String applicationName;
    
    public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationeventName() {
		return applicationeventName;
	}

	public void setApplicationeventName(String applicationeventName) {
		this.applicationeventName = applicationeventName;
	}

	public String getApplicationviewName() {
		return applicationviewName;
	}

	public void setApplicationviewName(String applicationviewName) {
		this.applicationviewName = applicationviewName;
	}

	private String applicationeventName;
    
    private String applicationviewName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
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
    public Long getSessionduration() {
        return sessionduration;
    }

    public void setSessionduration(Long sessionduration) {
        this.sessionduration = sessionduration;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getApplicationeventId() {
        return applicationeventId;
    }

    public void setApplicationeventId(Long applicationeventId) {
        this.applicationeventId = applicationeventId;
    }

    public Long getApplicationviewId() {
        return applicationviewId;
    }

    public void setApplicationviewId(Long applicationviewId) {
        this.applicationviewId = applicationviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventdataDTO eventdataDTO = (EventdataDTO) o;

        if ( ! Objects.equals(id, eventdataDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EventdataDTO{" +
            "id=" + id +
            ", clientid='" + clientid + "'" +
            ", clientos='" + clientos + "'" +
            ", devicemake='" + devicemake + "'" +
            ", appversion='" + appversion + "'" +
            ", sessionduration='" + sessionduration + "'" +
            ", location='" + location + "'" +
            '}';
    }
}
