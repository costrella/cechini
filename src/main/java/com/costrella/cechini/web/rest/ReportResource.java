package com.costrella.cechini.web.rest;

import com.costrella.cechini.domain.Note;
import com.costrella.cechini.domain.Report;
import com.costrella.cechini.domain.Worker;
import com.costrella.cechini.domain.enumeration.NoteType;
import com.costrella.cechini.service.ReportService;
import com.costrella.cechini.service.dto.ReportDTO;
import com.costrella.cechini.service.dto.ReportDTOWithPhotos;
import com.costrella.cechini.service.dto.ReportsDTO;
import com.costrella.cechini.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.costrella.cechini.domain.Report}.
 */
@RestController
@RequestMapping("/api")
public class ReportResource {

    private final Logger log = LoggerFactory.getLogger(ReportResource.class);

    private static final String ENTITY_NAME = "report";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportService reportService;

    public ReportResource(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * {@code POST  /reports} : Create a new report.
     *
     * @param reportDTO the reportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportDTO, or with status {@code 400 (Bad Request)} if the report has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reports")
    public ResponseEntity<ReportDTO> createReport(@Valid @RequestBody ReportDTOWithPhotos reportDTO) throws URISyntaxException {
        log.debug("REST request to save Report : {}", reportDTO);
        if (reportDTO.getId() != null) {
            throw new BadRequestAlertException("A new report cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportDTO result = reportService.saveWithPhotos(reportDTO);
        return ResponseEntity.created(new URI("/api/reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @Transactional
    @PostMapping("/reports/many")
    public ResponseEntity createManyReports(@Valid @RequestBody ReportsDTO reportsDTO) {
        for (ReportDTOWithPhotos r : reportsDTO.getReportsDTOS()) {
            if (r.getId() != null) {
                throw new BadRequestAlertException("A new report cannot already have an ID", ENTITY_NAME, "idexists");
            }
            reportService.saveWithPhotos(r);
        }
        return ResponseEntity.ok().build();

    }

    /**
     * {@code PUT  /reports} : Updates an existing report.
     *
     * @param reportDTO the reportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportDTO,
     * or with status {@code 400 (Bad Request)} if the reportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reports")
    public ResponseEntity<ReportDTO> addCommentToReport(@Valid @RequestBody ReportDTO reportDTO) throws URISyntaxException { //todo update report, this is only desc edit
        log.debug("REST request to update Report : {}", reportDTO);
        Report reportEntity = addCommentToReportCommon(reportDTO, false);
        ReportDTO result = reportService.save(reportEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportDTO.getId().toString()))
            .body(result);
    }

    private Report addCommentToReportCommon(ReportDTO reportDTO, boolean fromMobile){
        if (reportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Report reportEntity = reportService.findOneEntity(reportDTO.getId()).orElseThrow(() -> new BadRequestAlertException("Cannot find report", ENTITY_NAME, "" + reportDTO.getId()));
        Note newNote = new Note();
        newNote.setReport(reportEntity);
        newNote.setDate(Instant.now());

//        if(fromMobile){
//            newNote.setValue();
//        }
//        newNote.setValue(reportDTO.getManagerNote());
        newNote.setValue("TEST");
        newNote.setNoteType(fromMobile ? NoteType.BY_WORKER : NoteType.BY_MANGER);
        reportEntity.setReadByManager(false);
        reportEntity.setReadByWorker(false);
        reportEntity.addNote(newNote);
        return reportEntity;
    }

    @PutMapping("/reports/addCommentToReportMobile")
    public ResponseEntity<ReportDTO> addCommentToReportMobile(@Valid @RequestBody ReportDTO reportDTO) throws URISyntaxException { //todo update report, this is only desc edit
        log.debug("REST request to update Report, addCommentToReportMobile : {}", reportDTO);
        Report reportEntity = addCommentToReportCommon(reportDTO, true);
        ReportDTO result = reportService.save(reportEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportDTO.getId().toString()))
            .body(result);
    }

    @Transactional
    @PostMapping("/reports/addCommentToReportMobile/many")
    public ResponseEntity addCommentToReportMobileMany(@Valid @RequestBody ReportsDTO reportsDTO) {
        for (ReportDTO r : reportsDTO.getReportsDTOS()) {
            Report reportEntity = addCommentToReportCommon(r, true);
            reportService.save(reportEntity);
        }
        return ResponseEntity.ok().build();

    }

    /**
     * {@code GET  /reports} : get all the reports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reports in body.
     */
    @GetMapping("/reports")
    public ResponseEntity<List<ReportDTO>> getAllReports(Pageable pageable) {
        log.debug("REST request to get a page of Reports");
        Page<ReportDTO> page = reportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/reports/worker/{id}")
    public ResponseEntity<List<ReportDTO>> getAllReportsByWorkerId(Pageable pageable, @PathVariable Long id) {
        Page<ReportDTO> page = reportService.findAllByWorkerId(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/reports/worker/{id}/all")
    public ResponseEntity<List<ReportDTO>> getAllReportsByWorkerId(@PathVariable Long id) {
        LocalDate from = YearMonth.from(Instant.now().atZone(ZoneId.systemDefault())).atDay(1);
        Instant instantFrom = from.atStartOfDay(ZoneId.systemDefault()).toInstant();

        List<ReportDTO> list = reportService.findAllByWorkerIdLastMonth(id, instantFrom, Instant.now());
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/reports/worker/{workerId}/store/{storeId}/all")
    public ResponseEntity<List<ReportDTO>>
    getAllReportsByWorkerIdAndStoreId(@PathVariable Long workerId, @PathVariable Long storeId) {
        List<ReportDTO> list = reportService.findAllByWorkerIdAndStoreId(workerId, storeId);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/reports/worker/{workerId}/store/{storeId}", params = {"fromDate", "toDate"})
    public ResponseEntity<List<ReportDTO>> getReportsByWorkerAndStore(Pageable pageable, @PathVariable Long workerId, @PathVariable Long storeId,
                                                                      @RequestParam(value = "fromDate") LocalDate fromDate, @RequestParam(value = "toDate") LocalDate toDate) {
        log.debug("from date:" + fromDate);
        log.debug("from todate:" + toDate);
        Instant from = fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant to = toDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant();

        Page<ReportDTO> page = reportService.findByStoreAndWorkerAndDate(pageable, workerId, storeId, from, to);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/reports/store/{id}")
    public ResponseEntity<List<ReportDTO>> getAllReportsByStoreId(Pageable pageable, @PathVariable Long id) {
        Page<ReportDTO> page = reportService.findAllByStoreId(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reports/:id} : get the "id" report.
     *
     * @param id the id of the reportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reports/{id}")
    public ResponseEntity<ReportDTO> getReport(@PathVariable Long id) {//todo tutaj dac readByManager na TRUE , tylko czy z tej samej metody korzusta mobilka?
        log.debug("REST request to get Report : {}", id);
        Optional<ReportDTO> reportDTO = reportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reportDTO);
    }

    @GetMapping("/reports/store/{id}/{months}")
    public ResponseEntity<List<ReportDTO>> getReportsByStore(@PathVariable Long id, @PathVariable int months) {
        List<ReportDTO> list = reportService.findReportsByStoreIdAndXMonthAgo(id, months);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code DELETE  /reports/:id} : delete the "id" report.
     *
     * @param id the id of the reportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reports/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        log.debug("REST request to delete Report : {}", id);
        reportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
