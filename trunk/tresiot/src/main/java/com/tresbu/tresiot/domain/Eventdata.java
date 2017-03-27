package com.tresbu.tresiot.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Eventdata.
 */
@Entity
@Table(name = "tresiot_eventdata")
public class Eventdata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "clientid", length = 50, nullable = false)
    private String clientid;

    @Column(name = "clientos")
    private String clientos;

    @Column(name = "devicemake")
    private String devicemake;

    @Column(name = "appversion")
    private String appversion;

    @Column(name = "sessionduration")
    private Long sessionduration;

    @Column(name = "location")
    private String location;

    @ManyToOne
    @NotNull
    private Application application;

    @ManyToOne
    @NotNull
    private Applicationevent applicationevent;

    @ManyToOne
    @NotNull
    private Applicationview applicationview;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientid() {
        return clientid;
    }

    public Eventdata clientid(String clientid) {
        this.clientid = clientid;
        return this;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClientos() {
        return clientos;
    }

    public Eventdata clientos(String clientos) {
        this.clientos = clientos;
        return this;
    }

    public void setClientos(String clientos) {
        this.clientos = clientos;
    }

    public String getDevicemake() {
        return devicemake;
    }

    public Eventdata devicemake(String devicemake) {
        this.devicemake = devicemake;
        return this;
    }

    public void setDevicemake(String devicemake) {
        this.devicemake = devicemake;
    }

    public String getAppversion() {
        return appversion;
    }

    public Eventdata appversion(String appversion) {
        this.appversion = appversion;
        return this;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }

    public Long getSessionduration() {
        return sessionduration;
    }

    public Eventdata sessionduration(Long sessionduration) {
        this.sessionduration = sessionduration;
        return this;
    }

    public void setSessionduration(Long sessionduration) {
        this.sessionduration = sessionduration;
    }

    public String getLocation() {
        return location;
    }

    public Eventdata location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Application getApplication() {
        return application;
    }

    public Eventdata application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Applicationevent getApplicationevent() {
        return applicationevent;
    }

    public Eventdata applicationevent(Applicationevent applicationevent) {
        this.applicationevent = applicationevent;
        return this;
    }

    public void setApplicationevent(Applicationevent applicationevent) {
        this.applicationevent = applicationevent;
    }

    public Applicationview getApplicationview() {
        return applicationview;
    }

    public Eventdata applicationview(Applicationview applicationview) {
        this.applicationview = applicationview;
        return this;
    }

    public void setApplicationview(Applicationview applicationview) {
        this.applicationview = applicationview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Eventdata eventdata = (Eventdata) o;
        if (eventdata.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, eventdata.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Eventdata{" +
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
