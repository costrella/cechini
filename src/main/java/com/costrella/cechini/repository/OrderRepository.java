package com.costrella.cechini.repository;

import com.costrella.cechini.domain.Order;

import com.costrella.cechini.domain.Report;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
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
}
