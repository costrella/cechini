package com.costrella.cechini.repository;

import com.costrella.cechini.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Order entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//    List<Order> findTop10AllByWorkerId(Long id);

//    List<Order> findTop10All(Example<Order> var1);

    Page<Order> findAllByTenantId(Long tenantId, Pageable pageable);

    List<Order> findAllByTenantId(Long tenantId);

    Order findByTenantIdAndId(Long tenantId, Long id);
}
