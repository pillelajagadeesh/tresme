package com.tresbu.tresiot.repository;

import com.tresbu.tresiot.domain.Applicationview;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Applicationview entity.
 */
@SuppressWarnings("unused")
public interface ApplicationviewRepository extends JpaRepository<Applicationview,Long> {

}
