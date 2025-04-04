package com.costrella.cechini.repository;

import com.costrella.cechini.domain.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Warehouse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Page<Warehouse> findAllByTenantId(Long tenantId, Pageable pageable);

    List<Warehouse> findAllByTenantId(Long tenantId);

    Warehouse findByTenantIdAndId(Long tenantId, Long id);
}
