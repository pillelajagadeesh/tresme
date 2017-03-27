package com.tresbu.tresiot.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Sensordata.
 */
@Entity
@Table(name = "tresiot_sensordata")

@SqlResultSetMapping(
	    name="sensorDataMapping",
	    classes={
	        @ConstructorResult(
	            targetClass=com.tresbu.tresiot.domain.SensorLineGraph.class,
	            columns={
	            		@ColumnResult(name = "createTime", type = Long.class),
	            		@ColumnResult(name = "sensorName", type = String.class),
	            		@ColumnResult(name = "sensorValue", type = Long.class),
	            }
	        )
	    }
	)

@NamedNativeQuery(name="Sensordata.findSensordataByApplicationId", query="SELECT sd.created_time as createTime,sd.value as sensorValue, s.name as sensorName FROM tresiot_sensordata sd, tresiot_sensor s"
		+ " where s.id= sd.sensor_id and (sd.application_id = :applicationId or sd.clientid=:clientId or sd.sensor_id= :sensorId) and sd.created_time between :fromTime and :toTime order by sd.created_time asc ", resultSetMapping="sensorDataMapping")

public class Sensordata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "clientid", length = 50, nullable = false)
    private String clientid;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @NotNull
    @Column(name = "created_time", nullable = false)
    private Long createdTime;

    @ManyToOne
    @NotNull
    private Application application;

    @ManyToOne
    @NotNull
    private Sensor sensor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientid() {
        return clientid;
    }

    public Sensordata clientid(String clientid) {
        this.clientid = clientid;
        return this;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getValue() {
        return value;
    }

    public Sensordata value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public Sensordata createdTime(Long createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Application getApplication() {
        return application;
    }

    public Sensordata application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public Sensordata sensor(Sensor sensor) {
        this.sensor = sensor;
        return this;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sensordata sensordata = (Sensordata) o;
        if (sensordata.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sensordata.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sensordata{" +
            "id=" + id +
            ", clientid='" + clientid + "'" +
            ", value='" + value + "'" +
            ", createdTime='" + createdTime + "'" +
            '}';
    }
}
