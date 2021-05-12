package com.costrella.cechini.repository;

import com.costrella.cechini.domain.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends JpaRepository<Worker, Long> {

    String query = "SELECT count(r.id) from report r where r.worker_id = :id " +
        "and date_part('month', r.report_date) = date_part('month', current_date - ( :toTime || 'months')\\:\\:interval) " +
        "and date_part('year', r.report_date) = date_part('year', current_date - ( :toTime || 'months' )\\:\\:interval)";

    @Query(value = query, nativeQuery = true)
    Integer getNumberOfReportsFromXMonthAgo(@Param("id") Long id, @Param("toTime") String toTime);

    String queryOrders = "SELECT count(r.id) from report r where r.order_id is not null and r.worker_id = :id " +
        "and date_part('month', r.report_date) = date_part('month', current_date - ( :toTime || 'months')\\:\\:interval) " +
        "and date_part('year', r.report_date) = date_part('year', current_date - ( :toTime || 'months' )\\:\\:interval)";

    @Query(value = queryOrders, nativeQuery = true)
    Integer getNumberOfOrdersFromXMonthAgo(@Param("id") Long id, @Param("toTime") String toTime);

    @Query(value = "select sum(oi.pack_count) from report r inner join jhi_order o on r.order_id = o.id\n" +
        "inner join order_item oi on oi.order_id = o.id\n" +
        "inner join product p on oi.product_id = p.id where p.id = :id\n" +
        "and date_part('month', r.report_date) = date_part('month', current_date - ( :toTime || 'months')\\:\\:interval)\n" +
        "and date_part('year', r.report_date) = date_part('year', current_date - ( :toTime || 'months' )\\:\\:interval)\n" +
        "\n", nativeQuery = true)
    Integer getSumOfPackCountOfProduct(@Param("id") Long id, @Param("toTime") String toTime);

}
