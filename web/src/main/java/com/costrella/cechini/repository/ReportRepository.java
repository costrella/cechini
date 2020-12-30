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

    List<Report> findAllByWorkerIdOrderByReportDateDesc(Long id);

    List<Report> findAllByWorkerIdAndStoreIdOrderByReportDateDesc(Long id, Long storeId);

    Page<Report> findAllByWorkerIdOrderByReportDateDesc(Long id, Pageable pageable);

    Page<Report> findAllByWorkerIdAndStoreIdOrderByReportDateDesc(Long id, Long storeId, Pageable pageable);

    Page<Report> findAllByStoreIdOrderByReportDateDesc(Long id, Pageable pageable);

}
