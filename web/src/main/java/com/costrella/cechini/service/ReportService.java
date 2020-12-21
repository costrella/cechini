package com.costrella.cechini.service;

import com.costrella.cechini.domain.Report;
import com.costrella.cechini.domain.Store;
import com.costrella.cechini.domain.Worker;
import com.costrella.cechini.repository.ReportRepository;
import com.costrella.cechini.service.dto.ReportDTO;
import com.costrella.cechini.service.dto.ReportDTOWithPhotos;
import com.costrella.cechini.service.mapper.OrderItemMapper;
import com.costrella.cechini.service.mapper.OrderMapper;
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
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Report}.
 */
@Service
@Transactional
public class ReportService {

    private final Logger log = LoggerFactory.getLogger(ReportService.class);

    private final ReportRepository reportRepository;

    private final ReportMapper reportMapper;

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    public ReportService(ReportRepository reportRepository, ReportMapper reportMapper, OrderMapper orderMapper, OrderItemMapper orderItemMapper) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
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

    public ReportDTO saveWithPhotos(ReportDTOWithPhotos reportDTO) {
        log.debug("Request to save Report : {}", reportDTO);
        Report report = reportMapper.toEntityWithPhotos(reportDTO);
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
    public List<ReportDTO> findAllByWorkerId(Long id) {
        return reportRepository.findAllByWorkerId(id).stream()
            .map(report -> reportMapper.toDtoWithOrders(report, orderMapper, orderItemMapper)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReportDTO> findAllByWorkerIdAndStoreId(Long workerId, Long storeId) {
        return reportRepository.findAllByWorkerIdAndStoreId(workerId, storeId).stream()
            .map(report -> reportMapper.toDtoWithOrders(report, orderMapper, orderItemMapper)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ReportDTO> findAllByWorkerIdAndStoreId(Pageable pageable, Long id, Long storeId) {
        return reportRepository.findAllByWorkerIdAndStoreId(id, storeId, pageable)
            .map(reportMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ReportDTO> findByStoreAndWorker(Pageable pageable, Long workerId, Long storeId) {
        Report report = new Report();
        if (storeId != 0) report.setStore(new Store().id(storeId));
        if (workerId != 0) report.setWorker(new Worker().id(workerId));
        return reportRepository.findAll(Example.of(report), pageable).map(reportMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ReportDTO> findAllByStoreId(Pageable pageable, Long id) {
        log.debug("Request to get all Reports by store id");
        return reportRepository.findAllByStoreId(id, pageable)
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
