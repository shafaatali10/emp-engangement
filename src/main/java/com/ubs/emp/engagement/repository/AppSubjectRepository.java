package com.ubs.emp.engagement.repository;

import com.ubs.emp.engagement.domain.AppSubject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppSubject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppSubjectRepository extends JpaRepository<AppSubject, Long> {}
