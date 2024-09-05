package com.ubs.emp.engagement.repository;

import com.ubs.emp.engagement.domain.AppUserGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppUserGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppUserGroupRepository extends JpaRepository<AppUserGroup, Long> {}
