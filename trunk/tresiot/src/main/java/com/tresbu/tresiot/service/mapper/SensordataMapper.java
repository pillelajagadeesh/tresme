package com.tresbu.tresiot.service.mapper;

import com.tresbu.tresiot.domain.*;
import com.tresbu.tresiot.service.dto.SensordataDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Sensordata and its DTO SensordataDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SensordataMapper {

    @Mapping(source = "application.id", target = "applicationId")
    @Mapping(source = "sensor.id", target = "sensorId")
    @Mapping(source = "application.name", target = "applicationName")
    @Mapping(source = "sensor.name", target = "sensorName")
    SensordataDTO sensordataToSensordataDTO(Sensordata sensordata);

    List<SensordataDTO> sensordataToSensordataDTOs(List<Sensordata> sensordata);

    @Mapping(source = "applicationId", target = "application")
    @Mapping(source = "sensorId", target = "sensor")
    Sensordata sensordataDTOToSensordata(SensordataDTO sensordataDTO);

    List<Sensordata> sensordataDTOsToSensordata(List<SensordataDTO> sensordataDTOs);

    default Application applicationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Application application = new Application();
        application.setId(id);
        return application;
    }

    default Sensor sensorFromId(Long id) {
        if (id == null) {
            return null;
        }
        Sensor sensor = new Sensor();
        sensor.setId(id);
        return sensor;
    }
}
