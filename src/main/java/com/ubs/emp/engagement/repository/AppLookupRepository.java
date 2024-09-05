package com.ubs.emp.engagement.repository;

import com.ubs.emp.engagement.domain.AppLookup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppLookup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppLookupRepository extends JpaRepository<AppLookup, Long> {}
