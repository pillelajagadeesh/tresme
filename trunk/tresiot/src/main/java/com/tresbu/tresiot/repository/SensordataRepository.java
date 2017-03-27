package com.tresbu.tresiot.repository;

import com.tresbu.tresiot.domain.SensorLineGraph;
import com.tresbu.tresiot.domain.Sensordata;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Sensordata entity.
 */

public interface SensordataRepository extends JpaRepository<Sensordata,Long> {
	
	@Query("SELECT count(distinct s.id) FROM Sensordata s where s.sensor.id= :sensorId")
	Long sensorDataCountBySensor(@Param(value = "sensorId") Long sensorId);
	
	
	@Query(nativeQuery = true)
    List<SensorLineGraph> findSensordataByApplicationId( @Param("applicationId") Long applicationId,@Param("fromTime") Long fromTime,@Param("toTime") Long toTime,
    		@Param("clientId") Long clientId,@Param("sensorId") Long sensorId);
	
	@Query("SELECT distinct sd.clientid FROM Sensordata sd")
	List<String> findSensorClientIdList();

}
