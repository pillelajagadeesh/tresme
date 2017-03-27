package com.tresbu.tresiot.service.mapper;

import com.tresbu.tresiot.domain.*;
import com.tresbu.tresiot.service.dto.SensorDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Sensor and its DTO SensorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SensorMapper {

    SensorDTO sensorToSensorDTO(Sensor sensor);

    List<SensorDTO> sensorsToSensorDTOs(List<Sensor> sensors);

    Sensor sensorDTOToSensor(SensorDTO sensorDTO);

    List<Sensor> sensorDTOsToSensors(List<SensorDTO> sensorDTOs);
}
