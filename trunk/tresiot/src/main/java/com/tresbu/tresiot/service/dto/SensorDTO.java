package com.tresbu.tresiot.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.tresbu.tresiot.domain.enumeration.SensorDataType;

/**
 * A DTO for the Sensor entity.
 */
public class SensorDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    private String description;

    @NotNull
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

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public SensorDataType getDatatype() {
        return datatype;
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

        SensorDTO sensorDTO = (SensorDTO) o;

        if ( ! Objects.equals(id, sensorDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SensorDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", datatype='" + datatype + "'" +
            '}';
    }
}
