package com.ubs.emp.engagement.repository;

import com.ubs.emp.engagement.domain.AppSubjectConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppSubjectConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppSubjectConfigRepository extends JpaRepository<AppSubjectConfig, Long> {}
