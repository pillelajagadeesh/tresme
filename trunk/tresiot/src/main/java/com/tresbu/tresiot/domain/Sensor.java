package com.tresbu.tresiot.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.tresbu.tresiot.domain.enumeration.SensorDataType;

/**
 * A Sensor.
 */
@Entity
@Table(name = "tresiot_sensor")
public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "datatype", nullable = false)
    private SensorDataType datatype;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Sensor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Sensor description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SensorDataType getDatatype() {
        return datatype;
    }

    public Sensor datatype(SensorDataType datatype) {
        this.datatype = datatype;
        return this;
    }

    public void setDatatype(SensorDataType datatype) {
        this.datatype = datatype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sensor sensor = (Sensor) o;
        if (sensor.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sensor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sensor{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", datatype='" + datatype + "'" +
            '}';
    }
}
