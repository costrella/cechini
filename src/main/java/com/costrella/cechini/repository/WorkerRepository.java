package com.costrella.cechini.repository;

import com.costrella.cechini.domain.Worker;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Worker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Optional<Worker> findByLogin(String login);

    Optional<Worker> findByLoginAndPassword(String login, String password);

    String currentMonth = "SELECT count(r.id) from report r where r.worker_id = :id " +
        "and date_part('month', r.report_date) = date_part('month', current_date) " +
        "and date_part('year', r.report_date) = date_part('year', current_date)";
    String oneMonth = "SELECT count(r.id) from report r where r.worker_id = :id " +
        "and date_part('month', r.report_date) = date_part('month', current_date - INTERVAL '1 month') " +
        "and date_part('year', r.report_date) = date_part('year', current_date - INTERVAL '1 month')";
    String twoMonth = "SELECT count(r.id) from report r where r.worker_id = :id " +
        "and date_part('month', r.report_date) = date_part('month', current_date - INTERVAL '2 month') " +
        "and date_part('year', r.report_date) = date_part('year', current_date - INTERVAL '2 month')";
    String threeMonth = "SELECT count(r.id) from report r where r.worker_id = :id " +
        "and date_part('month', r.report_date) = date_part('month', current_date - INTERVAL '3 month') " +
        "and date_part('year', r.report_date) = date_part('year', current_date - INTERVAL '3 month')";


    @Query(value = currentMonth, nativeQuery = true)
    Integer getNumberOfReportsFromCurrentMonthAgo(@Param("id") Long id);

    @Query(value = oneMonth, nativeQuery = true)
    Integer getNumberOfReportsFromOneMonthAgo(@Param("id") Long id);

    @Query(value = twoMonth, nativeQuery = true)
    Integer getNumberOfReportsFromTwoMonthAgo(@Param("id") Long id);

    @Query(value = threeMonth, nativeQuery = true)
    Integer getNumberOfReportsFromThreeMonthAgo(@Param("id") Long id);
}
