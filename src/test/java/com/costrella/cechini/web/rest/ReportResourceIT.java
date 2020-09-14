package com.costrella.cechini.web.rest;

import com.costrella.cechini.CechiniApp;
import com.costrella.cechini.domain.Report;
import com.costrella.cechini.repository.ReportRepository;
import com.costrella.cechini.service.ReportService;
import com.costrella.cechini.service.dto.ReportDTO;
import com.costrella.cechini.service.mapper.ReportMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReportResource} REST controller.
 */
@SpringBootTest(classes = CechiniApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReportResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REPORT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REPORT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_WORKER_DESC = "AAAAAAAAAA";
    private static final String UPDATED_WORKER_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_DESC = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_DESC = "BBBBBBBBBB";

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private ReportService reportService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReportMockMvc;

    private Report report;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Report createEntity(EntityManager em) {
        Report report = new Report()
            .number(DEFAULT_NUMBER)
            .reportDate(DEFAULT_REPORT_DATE)
            .workerDesc(DEFAULT_WORKER_DESC)
            .managerDesc(DEFAULT_MANAGER_DESC);
        return report;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Report createUpdatedEntity(EntityManager em) {
        Report report = new Report()
            .number(UPDATED_NUMBER)
            .reportDate(UPDATED_REPORT_DATE)
            .workerDesc(UPDATED_WORKER_DESC)
            .managerDesc(UPDATED_MANAGER_DESC);
        return report;
    }

    @BeforeEach
    public void initTest() {
        report = createEntity(em);
    }

    @Test
    @Transactional
    public void createReport() throws Exception {
        int databaseSizeBeforeCreate = reportRepository.findAll().size();
        // Create the Report
        ReportDTO reportDTO = reportMapper.toDto(report);
        restReportMockMvc.perform(post("/api/reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isCreated());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeCreate + 1);
        Report testReport = reportList.get(reportList.size() - 1);
        assertThat(testReport.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testReport.getReportDate()).isEqualTo(DEFAULT_REPORT_DATE);
        assertThat(testReport.getWorkerDesc()).isEqualTo(DEFAULT_WORKER_DESC);
        assertThat(testReport.getManagerDesc()).isEqualTo(DEFAULT_MANAGER_DESC);
    }

    @Test
    @Transactional
    public void createReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportRepository.findAll().size();

        // Create the Report with an existing ID
        report.setId(1L);
        ReportDTO reportDTO = reportMapper.toDto(report);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportMockMvc.perform(post("/api/reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReports() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList
        restReportMockMvc.perform(get("/api/reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(report.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].reportDate").value(hasItem(DEFAULT_REPORT_DATE.toString())))
            .andExpect(jsonPath("$.[*].workerDesc").value(hasItem(DEFAULT_WORKER_DESC)))
            .andExpect(jsonPath("$.[*].managerDesc").value(hasItem(DEFAULT_MANAGER_DESC)));
    }
    
    @Test
    @Transactional
    public void getReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get the report
        restReportMockMvc.perform(get("/api/reports/{id}", report.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(report.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.reportDate").value(DEFAULT_REPORT_DATE.toString()))
            .andExpect(jsonPath("$.workerDesc").value(DEFAULT_WORKER_DESC))
            .andExpect(jsonPath("$.managerDesc").value(DEFAULT_MANAGER_DESC));
    }
    @Test
    @Transactional
    public void getNonExistingReport() throws Exception {
        // Get the report
        restReportMockMvc.perform(get("/api/reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        int databaseSizeBeforeUpdate = reportRepository.findAll().size();

        // Update the report
        Report updatedReport = reportRepository.findById(report.getId()).get();
        // Disconnect from session so that the updates on updatedReport are not directly saved in db
        em.detach(updatedReport);
        updatedReport
            .number(UPDATED_NUMBER)
            .reportDate(UPDATED_REPORT_DATE)
            .workerDesc(UPDATED_WORKER_DESC)
            .managerDesc(UPDATED_MANAGER_DESC);
        ReportDTO reportDTO = reportMapper.toDto(updatedReport);

        restReportMockMvc.perform(put("/api/reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isOk());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeUpdate);
        Report testReport = reportList.get(reportList.size() - 1);
        assertThat(testReport.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testReport.getReportDate()).isEqualTo(UPDATED_REPORT_DATE);
        assertThat(testReport.getWorkerDesc()).isEqualTo(UPDATED_WORKER_DESC);
        assertThat(testReport.getManagerDesc()).isEqualTo(UPDATED_MANAGER_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingReport() throws Exception {
        int databaseSizeBeforeUpdate = reportRepository.findAll().size();

        // Create the Report
        ReportDTO reportDTO = reportMapper.toDto(report);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportMockMvc.perform(put("/api/reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        int databaseSizeBeforeDelete = reportRepository.findAll().size();

        // Delete the report
        restReportMockMvc.perform(delete("/api/reports/{id}", report.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
