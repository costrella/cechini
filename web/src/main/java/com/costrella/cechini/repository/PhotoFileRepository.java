package com.costrella.cechini.repository;

import com.costrella.cechini.domain.PhotoFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the PhotoFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhotoFileRepository extends JpaRepository<PhotoFile, Long> {

    Page<PhotoFile> findAllByTenantId(Long tenantId, Pageable pageable);

    List<PhotoFile> findAllByTenantId(Long tenantId);

    PhotoFile findByTenantIdAndId(Long tenantId, Long id);
}
