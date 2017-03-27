package com.tresbu.tresiot.repository;

import com.tresbu.tresiot.domain.Application;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Application entity.
 */
@SuppressWarnings("unused")
public interface ApplicationRepository extends JpaRepository<Application,Long> {
	
	@Query("SELECT application FROM Application application")
	List<Application> findApplicationList();

}
