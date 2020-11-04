package com.costrella.cechini.repository;

import com.costrella.cechini.domain.StoreGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StoreGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreGroupRepository extends JpaRepository<StoreGroup, Long> {
}
