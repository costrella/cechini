package com.costrella.cechini.repository;

import com.costrella.cechini.domain.Report;
import com.costrella.cechini.domain.Store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Store entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Page<Store> findAllByWorkerId(Long id, Pageable pageable);

}
