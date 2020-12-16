package com.costrella.cechini.repository;

import com.costrella.cechini.domain.Report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Report entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

//    List<Report> findAllByNumber(String number);
//
//    List<Report> findByNumber(String number);

    List<Report> findAllByWorkerId(Long id);

    Page<Report> findAllByWorkerId(Long id, Pageable pageable);

    Page<Report> findAllByWorkerIdAndStoreId(Long id, Long storeId, Pageable pageable);

    Page<Report> findAllByStoreId(Long id, Pageable pageable);

}
