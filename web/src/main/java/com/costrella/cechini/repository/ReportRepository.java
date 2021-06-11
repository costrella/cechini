package com.costrella.cechini.repository;

import com.costrella.cechini.domain.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data  repository for the Report entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findAllByWorkerIdAndReportDateBetweenOrderByReportDateDesc(Long id, Instant fromDate, Instant toDate);

    List<Report> findAllByStoreIdAndReportDateBetweenOrderByReportDateDesc(Long id, Instant fromDate, Instant toDate);

    List<Report> findAllByWorkerIdAndStoreIdOrderByReportDateDesc(Long id, Long storeId);

    Page<Report> findAllByWorkerIdOrderByReportDateDesc(Long id, Pageable pageable);

    Page<Report> findAllByWorkerIdAndStoreIdOrderByReportDateDesc(Long id, Long storeId, Pageable pageable);

    Page<Report> findAllByStoreIdOrderByReportDateDesc(Long id, Pageable pageable);


    Page<Report> findAllByReportDateBetween(Instant fromDate, Instant toDate, Pageable pageable);

    Page<Report> findAllByStoreIdAndReportDateBetween(Long id, Instant fromDate, Instant toDate, Pageable pageable);

    Page<Report> findAllByWorkerIdAndReportDateBetween(Long id, Instant fromDate, Instant toDate, Pageable pageable);

    Page<Report> findAllByStoreIdAndWorkerIdAndReportDateBetween(Long storeId, Long workerId, Instant fromDate, Instant toDate, Pageable pageable);

}
