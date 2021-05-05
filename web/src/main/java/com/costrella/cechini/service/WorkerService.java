package com.costrella.cechini.service;

import com.costrella.cechini.domain.Worker;
import com.costrella.cechini.repository.WorkerRepository;
import com.costrella.cechini.service.dto.Chart01DTO;
import com.costrella.cechini.service.dto.ChartDetail01DTO;
import com.costrella.cechini.service.dto.WorkerDTO;
import com.costrella.cechini.service.mapper.WorkerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Worker}.
 */
@Service
@Transactional
public class WorkerService {

    private final Logger log = LoggerFactory.getLogger(WorkerService.class);

    private final WorkerRepository workerRepository;

    private final WorkerMapper workerMapper;

    public WorkerService(WorkerRepository workerRepository, WorkerMapper workerMapper) {
        this.workerRepository = workerRepository;
        this.workerMapper = workerMapper;
    }

    public Chart01DTO getChart01() {
        Chart01DTO chart = new Chart01DTO();
        chart.details = new ArrayList<>();
        List<Worker> workers = workerRepository.findAll();
        for (Worker worker : workers) {
            ChartDetail01DTO chartDetail = new ChartDetail01DTO();
            chartDetail.data = new ArrayList<>();
            chartDetail.data.add(workerRepository.getNumberOfReportsFromThreeMonthAgo(worker.getId()));
            chartDetail.data.add(workerRepository.getNumberOfReportsFromTwoMonthAgo(worker.getId()));
            chartDetail.data.add(workerRepository.getNumberOfReportsFromOneMonthAgo(worker.getId()));
            chartDetail.data.add(workerRepository.getNumberOfReportsFromCurrentMonthAgo(worker.getId()));
            chartDetail.label = worker.getSurname() + " " + worker.getName();
            chart.details.add(chartDetail);
        }
        LocalDate now = LocalDate.now();

        chart.monthsName = Arrays.asList(new String[]{
            now.minusMonths(3).getMonth().getDisplayName(
                TextStyle.FULL_STANDALONE,
                Locale.forLanguageTag("PL")
            ),
            now.minusMonths(2).getMonth().getDisplayName(
                TextStyle.FULL_STANDALONE,
                Locale.forLanguageTag("PL")
            ),
            now.minusMonths(1).getMonth().getDisplayName(
                TextStyle.FULL_STANDALONE,
                Locale.forLanguageTag("PL")
            ),
            now.getMonth().getDisplayName(
                TextStyle.FULL_STANDALONE,
                Locale.forLanguageTag("PL")
            )});

        return chart;
    }

    /**
     * Save a worker.
     *
     * @param workerDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkerDTO save(WorkerDTO workerDTO) {
        log.debug("Request to save Worker : {}", workerDTO);
        Worker worker = workerMapper.toEntity(workerDTO);
        worker = workerRepository.save(worker);
        return workerMapper.toDto(worker);
    }

    /**
     * Get all the workers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Workers");
        return workerRepository.findAll(pageable)
            .map(workerMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<WorkerDTO> findAll() {
        return workerRepository.findAll().stream()
            .map(workerMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Get one worker by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkerDTO> findOne(Long id) {
        log.debug("Request to get Worker : {}", id);
        return workerRepository.findById(id)
            .map(workerMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<WorkerDTO> findByLogin(String login) {
        return workerRepository.findByLogin(login)
            .map(workerMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<WorkerDTO> findByLoginAndPassword(String login, String password) {
        return workerRepository.findByLoginAndPassword(login, password)
            .map(workerMapper::toDto);
    }

    /**
     * Delete the worker by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Worker : {}", id);
        workerRepository.deleteById(id);
    }
}
