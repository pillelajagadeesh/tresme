package com.tresbu.tresiot.repository;

import com.tresbu.tresiot.domain.Application;
import com.tresbu.tresiot.domain.Sensor;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Sensor entity.
 */

public interface SensorRepository extends JpaRepository<Sensor,Long> {
	
	@Query(value="SELECT * FROM tresiot.tresiot_sensor s "
	+"where s.id "
	+"in (SELECT distinct(sd.sensor_id) FROM tresiot.tresiot_sensordata sd where sd.clientid = :clientId "
	+"OR (SELECT distinct(sd.sensor_id) FROM tresiot.tresiot_sensordata sd where sd.application_id = :applicationId) "
	+"OR (SELECT distinct(sd.sensor_id) FROM tresiot.tresiot_sensordata sd where sd.sensor_id = :sensorId))",nativeQuery=true)
    List<Sensor> findSensorByClientId(@Param("clientId")String clientId, @Param("applicationId")String applicationId, @Param("sensorId")String sensorId);
	
	/*SELECT * FROM tresiot.tresiot_sensor s 
	where s.id 
	in (SELECT distinct(sd.sensor_id) FROM tresiot.tresiot_sensordata sd where sd.clientid = '1001'
	OR (SELECT distinct(sd.sensor_id) FROM tresiot.tresiot_sensordata sd where sd.application_id = '1001')
	OR (SELECT distinct(sd.sensor_id) FROM tresiot.tresiot_sensordata sd where sd.sensor_id = '1003'));*/
	
	
	@Query("SELECT sensor FROM Sensor sensor")
	List<Sensor> findSensorList();

}
