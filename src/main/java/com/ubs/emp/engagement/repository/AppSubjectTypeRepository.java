package com.ubs.emp.engagement.repository;

import com.ubs.emp.engagement.domain.AppSubjectType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppSubjectType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppSubjectTypeRepository extends JpaRepository<AppSubjectType, Long> {}
