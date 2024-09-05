package com.ubs.emp.engagement.repository;

import com.ubs.emp.engagement.domain.AppTopicType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppTopicType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppTopicTypeRepository extends JpaRepository<AppTopicType, Long> {}
