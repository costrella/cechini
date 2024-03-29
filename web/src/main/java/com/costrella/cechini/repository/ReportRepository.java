package com.costrella.cechini.repository;

import com.costrella.cechini.domain.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

/**
 * Spring Data  repository for the Report entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {


    @Query("select r from Report r left join fetch r.notes where r.id = :id")
    Report customFindById(@Param("id") Long id);

    @Query("select r from Report r join fetch r.store left join fetch r.order left join fetch r.notes where r.readByWorker = false and r.worker.id = :id order by r.reportDate desc")
    Stream<Report> customFindAllByWorkerIdAndReadByWorkerIsFalseOrderByReportDateDesc(@Param("id") Long id);

    @Query("select r from Report r join fetch r.store left join fetch r.order left join fetch r.notes where r.worker.id = :id and r.reportDate > :from and r.reportDate < :to order by r.reportDate desc")
    Stream<Report> customByWorkerIdAndReportDateBetweenOrderByReportDateDesc(@Param("id") Long id, @Param("from") Instant from, @Param("to") Instant to);

    List<Report> findAllByWorkerIdAndReportDateBetweenOrderByReportDateDesc(Long id, Instant fromDate, Instant toDate);

    List<Report> findAllByStoreIdAndReportDateBetweenOrderByReportDateDesc(Long id, Instant fromDate, Instant toDate);

    @Query("select r from Report r join fetch r.store left join fetch r.order left join fetch r.notes where r.worker.id = :id and r.store.id = :storeId order by r.reportDate desc")
    Stream<Report> customFindAllByWorkerIdAndStoreIdOrderByReportDateDesc(@Param("id") Long id, @Param("storeId") Long storeId);

    Page<Report> findAllByWorkerIdOrderByReportDateDesc(Long id, Pageable pageable);

    Page<Report> findAllByWorkerIdAndStoreIdOrderByReportDateDesc(Long id, Long storeId, Pageable pageable);

    Page<Report> findAllByStoreIdOrderByReportDateDesc(Long id, Pageable pageable);


    Page<Report> findAllByReportDateBetween(Instant fromDate, Instant toDate, Pageable pageable);

    Page<Report> findAllByStoreIdAndReportDateBetween(Long id, Instant fromDate, Instant toDate, Pageable pageable);

    Page<Report> findAllByWorkerIdAndReportDateBetween(Long id, Instant fromDate, Instant toDate, Pageable pageable);

    Page<Report> findAllByStoreIdAndWorkerIdAndReportDateBetween(Long storeId, Long workerId, Instant fromDate, Instant toDate, Pageable pageable);

}
