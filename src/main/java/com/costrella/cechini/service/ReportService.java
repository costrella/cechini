package com.costrella.cechini.service;

import com.costrella.cechini.domain.Report;
import com.costrella.cechini.domain.Store;
import com.costrella.cechini.domain.Worker;
import com.costrella.cechini.repository.ReportRepository;
import com.costrella.cechini.service.dto.ReportDTO;
import com.costrella.cechini.service.mapper.ReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Report}.
 */
@Service
@Transactional
public class ReportService {

    private final Logger log = LoggerFactory.getLogger(ReportService.class);

    private final ReportRepository reportRepository;

    private final ReportMapper reportMapper;

    public ReportService(ReportRepository reportRepository, ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
    }

    /**
     * Save a report.
     *
     * @param reportDTO the entity to save.
     * @return the persisted entity.
     */
    public ReportDTO save(ReportDTO reportDTO) {
        log.debug("Request to save Report : {}", reportDTO);
        Report report = reportMapper.toEntity(reportDTO);
        report = reportRepository.save(report);
        return reportMapper.toDto(report);
    }

    /**
     * Get all the reports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reports");
        return reportRepository.findAll(pageable)
            .map(reportMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ReportDTO> findAllByWorkerId(Pageable pageable, Long id) {
        log.debug("Request to get all Reports by worker id");
        return reportRepository.findAllByWorkerId(id, pageable)
            .map(reportMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ReportDTO> findAllByWorkerIdAndStoreId(Pageable pageable, Long id, Long storeId) {
        return reportRepository.findAllByWorkerIdAndStoreId(id, storeId,  pageable)
            .map(reportMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ReportDTO> findAllByExample(Pageable pageable, Long workerId, Long storeId, Long warehouseId) {
        Report report = new Report();
        report.setStore(new Store().id(storeId));
        report.setWorker(new Worker().id(workerId));
        return reportRepository.findAll(Example.of(report), pageable).map(reportMapper::toDto);
//        return reportRepository.findAllByWorkerIdAndStoreId(id, storeId,  pageable)
//            .map(reportMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ReportDTO> findAllByStoreId(Pageable pageable, Long id) {
        log.debug("Request to get all Reports by store id");
        return reportRepository.findAllByStoreId(id, pageable)
            .map(reportMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ReportDTO> findAllByNumber(Pageable pageable, String number) {
        log.debug("Request to get all Reports by number");
        return reportRepository.findAllByNumber(number, pageable)
            .map(reportMapper::toDto);
    }

    /**
     * Get one report by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReportDTO> findOne(Long id) {
        log.debug("Request to get Report : {}", id);
        return reportRepository.findById(id)
            .map(reportMapper::toDto);
    }

    /**
     * Delete the report by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Report : {}", id);
        reportRepository.deleteById(id);
    }
}
