package com.costrella.cechini.repository;

import com.costrella.cechini.domain.Worker;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Worker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
}
