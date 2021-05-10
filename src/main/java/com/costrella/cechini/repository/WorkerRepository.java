package com.costrella.cechini.repository;

import com.costrella.cechini.domain.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
