package com.tresbu.tresiot.repository;

import com.tresbu.tresiot.domain.Eventdata;
import com.tresbu.tresiot.domain.SensorLineGraph;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Eventdata entity.
 */
@SuppressWarnings("unused")
public interface EventdataRepository extends JpaRepository<Eventdata,Long> {
	
	@Query("SELECT count(distinct e.id) FROM Eventdata e where e.applicationevent.id= :eventId")
	Long eventDataCountByEvent(@Param(value = "eventId") Long eventId);
	
	@Query("SELECT distinct ed.clientid FROM Eventdata ed")
	List<String> findEventClientIdList();
	
	

}
