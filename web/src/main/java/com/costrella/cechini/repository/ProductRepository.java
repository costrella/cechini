package com.costrella.cechini.repository;

import com.costrella.cechini.domain.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByTenantId(Long tenantId, Pageable pageable);

    List<Product> findAllByTenantId(Long tenantId);

    Product findByTenantIdAndId(Long tenantId, Long id);

}
