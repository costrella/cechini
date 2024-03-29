package com.costrella.cechini.service;

import com.costrella.cechini.domain.Report;
import com.costrella.cechini.repository.ReportRepository;
import com.costrella.cechini.service.dto.ReportDTO;
import com.costrella.cechini.service.dto.ReportDTOSimple;
import com.costrella.cechini.service.dto.ReportDTOWithPhotos;
import com.costrella.cechini.service.dto.WarehouseDTO;
import com.costrella.cechini.service.mapper.OrderItemMapper;
import com.costrella.cechini.service.mapper.OrderMapper;
import com.costrella.cechini.service.mapper.PhotoFileMapper;
import com.costrella.cechini.service.mapper.ReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
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

    private final PhotoFileMapper photoFileMapper;

    private final MailService mailService;

    private final OrderCSVFileService orderCSVFileService;

    private final OrderExcelFileService orderExcelFileService;

    private final WarehouseService warehouseService;

    public ReportService(ReportRepository reportRepository, ReportMapper reportMapper, OrderMapper orderMapper, OrderItemMapper orderItemMapper, PhotoFileMapper photoFileMapper, MailService mailService, OrderCSVFileService orderCSVFileService, OrderExcelFileService orderExcelFileService, WarehouseService warehouseService) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.photoFileMapper = photoFileMapper;
        this.mailService = mailService;
        this.orderCSVFileService = orderCSVFileService;
        this.orderExcelFileService = orderExcelFileService;
        this.warehouseService = warehouseService;
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

    public ReportDTO setReportReadByManager(Long reportId, Boolean read) {
        log.debug("Request setReportReadByManager : {}", reportId);
        Optional<Report> optionalReport = reportRepository.findById(reportId);
        if(optionalReport.isPresent()){
            Report report = optionalReport.get();
            report.setReadByManager(read);
            report = reportRepository.save(report);
            return reportMapper.toDto(report);
        } else {
            return null;
        }
    }

    public ReportDTO setReportReadByWorker(Long reportId, Boolean read) {
        log.debug("Request setReportReadByWorker : {}", reportId);
        Optional<Report> optionalReport = reportRepository.findById(reportId);
        if(optionalReport.isPresent()){
            Report report = optionalReport.get();
            report.setReadByWorker(read);
            report = reportRepository.save(report);
            return reportMapper.toDto(report);
        } else {
            return null;
        }
    }

    public ReportDTO save(Report report) {
        report = reportRepository.save(report);
        return reportMapper.toDto(report);
    }

    public ReportDTO saveWithPhotos(ReportDTOWithPhotos reportDTO) {
        log.debug("Request to save Report : {}", reportDTO);
        Report report = reportMapper.toEntityWithPhotos(reportDTO);
        report = reportRepository.save(report);

        if (report.getOrder() != null
            && report.getOrder().getWarehouse() != null
            && report.getOrder().getWarehouse().getId() != null) {
            Optional<WarehouseDTO> warehouse = warehouseService.findOne(report.getOrder().getWarehouse().getId());
            if (warehouse.isPresent() && warehouse.get().getMail() != null) {
                try {
                    File file;
                    if (warehouse.get().getOrderFileType() == null) {
                        file = orderExcelFileService.generateFile(report);
                    } else {
                        switch (warehouse.get().getOrderFileType()) {
                            case CSV:
                                file = orderCSVFileService.generateFile(report);
                                break;
                            case EXCEL:
                            default:
                                file = orderExcelFileService.generateFile(report);
                                break;
                        }
                    }
                    mailService.sendEmailWithOrder(report.getOrder().getNumber(), warehouse.get().getMail(), warehouse.get().getOrderFileType(), file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
        return reportRepository
            .findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "reportDate")))
            .map(reportMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ReportDTO> findAllByWorkerId(Pageable pageable, Long id) {
        log.debug("Request to get all Reports by worker id");
        return reportRepository.findAllByWorkerIdOrderByReportDateDesc(id, pageable)
            .map(reportMapper::toDto);
    }

    @Transactional(readOnly = true)//mobile
    public List<ReportDTOSimple> findAllByWorkerIdLastMonthMobile(Long id, Instant from, Instant to) {
        return reportRepository.customByWorkerIdAndReportDateBetweenOrderByReportDateDesc(id, from, to)
            .map(reportMapper::toDtoMobile).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReportDTOSimple> findAllByWorkerIdAndReadByWorkerIsFalseOrderByReportDateDesc(Long id) {
        return reportRepository.customFindAllByWorkerIdAndReadByWorkerIsFalseOrderByReportDateDesc(id)
            .map(report -> reportMapper.toDtoMobile(report)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReportDTOSimple> findAllByWorkerIdAndStoreId(Long workerId, Long storeId) {
        return reportRepository.customFindAllByWorkerIdAndStoreIdOrderByReportDateDesc(workerId, storeId)
            .map(reportMapper::toDtoMobile).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ReportDTO> findAllByWorkerIdAndStoreId(Pageable pageable, Long id, Long storeId) {
        return reportRepository.findAllByWorkerIdAndStoreIdOrderByReportDateDesc(id, storeId, pageable)
            .map(reportMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ReportDTO> findByStoreAndWorkerAndDate(Pageable pageable, Long workerId, Long storeId, Instant from, Instant to) {
        if (storeId != 0 && workerId != 0) { //worker i store NIEPUSTE
            return reportRepository.findAllByStoreIdAndWorkerIdAndReportDateBetween(storeId, workerId, from, to,
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "reportDate"))
            ).map(reportMapper::toDto);
        }
        if (storeId == 0 && workerId == 0) { //worker i store PUSTE
            return reportRepository.findAllByReportDateBetween(from, to,
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "reportDate"))
            ).map(reportMapper::toDto);
        }
        if (workerId != 0 && storeId == 0) { //storeId PUSTE
            return reportRepository.findAllByWorkerIdAndReportDateBetween(workerId, from, to,
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "reportDate"))
            ).map(reportMapper::toDto);
        }
        if (storeId != 0 && workerId == 0) { //workerId PUSTE
            return reportRepository.findAllByStoreIdAndReportDateBetween(storeId, from, to,
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "reportDate"))
            ).map(reportMapper::toDto);
        }

        return reportRepository.findAllByReportDateBetween(from, to,
            PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "reportDate"))
        ).map(reportMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ReportDTO> findAllByStoreId(Pageable pageable, Long id) {
        log.debug("Request to get all Reports by store id");
        return reportRepository.findAllByStoreIdOrderByReportDateDesc(id, pageable)
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
            .map(report -> reportMapper.toDtoWithPhotos(report, orderMapper, orderItemMapper, photoFileMapper));
    }

    @Transactional(readOnly = true)
    public Optional<Report> findOneEntity(Long id) {
        log.debug("Request to get Report : {}", id);
        return Optional.ofNullable(reportRepository.customFindById(id));

        //        return Optional.of(reportRepository.findById(id)
        //            .map(reportMapper::getReportWithAll).get());
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

    @Transactional(readOnly = true)
    public List<ReportDTO> findReportsByStoreIdAndXMonthAgo(Long id, int monthsAgo) {
        Instant lastXMonths = Instant.now();
        return reportRepository.findAllByStoreIdAndReportDateBetweenOrderByReportDateDesc(id, lastXMonths.minusSeconds(60 * 60 * 24 * 31 * monthsAgo), Instant.now())
            .stream()
            .map(report -> reportMapper.toDtoWithPhotos(report, orderMapper, orderItemMapper, photoFileMapper))
            .collect(Collectors.toList());
    }
}
