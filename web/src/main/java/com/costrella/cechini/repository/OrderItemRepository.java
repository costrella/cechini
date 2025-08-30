package com.costrella.cechini.repository;

import com.costrella.cechini.domain.OrderItem;
import com.costrella.cechini.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the OrderItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Page<OrderItem> findAllByOrderId(Pageable pageable, Long orderId);

    Page<OrderItem> findAllByTenantId(Long tenantId, Pageable pageable);

    List<OrderItem> findAllByTenantId(Long tenantId);

    OrderItem findByTenantIdAndId(Long tenantId, Long id);
}
