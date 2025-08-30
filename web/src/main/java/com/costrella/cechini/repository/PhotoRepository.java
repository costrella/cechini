package com.costrella.cechini.repository;

import com.costrella.cechini.domain.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Photo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Page<Photo> findAllByTenantId(Long tenantId, Pageable pageable);

    List<Photo> findAllByTenantId(Long tenantId);

    Photo findByTenantIdAndId(Long tenantId, Long id);
}
