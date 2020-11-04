package com.costrella.cechini.repository;

import com.costrella.cechini.domain.PhotoFile;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PhotoFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhotoFileRepository extends JpaRepository<PhotoFile, Long> {
}
