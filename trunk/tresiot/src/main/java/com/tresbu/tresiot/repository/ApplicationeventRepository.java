package com.tresbu.tresiot.repository;

import com.tresbu.tresiot.domain.Applicationevent;
import com.tresbu.tresiot.domain.Sensor;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Applicationevent entity.
 */
@SuppressWarnings("unused")
public interface ApplicationeventRepository extends JpaRepository<Applicationevent,Long> {
	
	//@Query("SELECT e FROM Applicationevent e where e.id in (SELECT distinct(ed.applicationevent.id) FROM Eventdata ed where ed.clientid = :clientId))")
	@Query(value="SELECT * FROM tresiot.tresiot_applicationevent e "
			+"where e.id in (SELECT distinct(ed.applicationevent_id) "
			+"FROM tresiot.tresiot_eventdata ed where (ed.clientid = :clientId or ed.application_id= :applicationId or ed.applicationevent_id= :eventId))",
			nativeQuery=true)
	List<Applicationevent> findEventByClientId(@Param("clientId")String clientId, @Param("applicationId")String applicationId, @Param("eventId")String eventId);
	
	
	@Query("SELECT applicationevent FROM Applicationevent applicationevent")
	List<Applicationevent> findApplicationeventList();

}
