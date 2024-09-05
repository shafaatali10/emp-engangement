package com.ubs.emp.engagement.repository;

import com.ubs.emp.engagement.domain.AppTopicLookup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppTopicLookup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppTopicLookupRepository extends JpaRepository<AppTopicLookup, Long> {}
