package com.costrella.cechini.web.rest;

import com.costrella.cechini.domain.Worker;
import com.costrella.cechini.service.WorkerService;
import com.costrella.cechini.service.dto.Chart01DTO;
import com.costrella.cechini.service.dto.WorkerDTO;
import com.costrella.cechini.service.exceptions.WorkerLoginNotFoundException;
import com.costrella.cechini.service.exceptions.WorkerWrongPassException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.costrella.cechini.domain.Worker}.
 */
@RestController
@RequestMapping("/api")
public class WorkerResource {

    private final Logger log = LoggerFactory.getLogger(WorkerResource.class);

    private static final String ENTITY_NAME = "worker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkerService workerService;

    public WorkerResource(WorkerService workerService) {
        this.workerService = workerService;
    }

    /**
     * {@code POST  /workers} : Create a new worker.
     *
     * @param workerDTO the workerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workerDTO, or with status {@code 400 (Bad Request)} if the worker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/workers")
    public ResponseEntity<WorkerDTO> createWorker(@Valid @RequestBody WorkerDTO workerDTO) throws URISyntaxException {
        log.debug("REST request to save Worker : {}", workerDTO);
        if (workerDTO.getId() != null) {
            throw new BadRequestAlertException("A new worker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkerDTO result = workerService.save(workerDTO);
        return ResponseEntity.created(new URI("/api/workers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /workers} : Updates an existing worker.
     *
     * @param workerDTO the workerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workerDTO,
     * or with status {@code 400 (Bad Request)} if the workerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/workers")
    public ResponseEntity<WorkerDTO> updateWorker(@Valid @RequestBody WorkerDTO workerDTO) throws URISyntaxException {
        log.debug("REST request to update Worker : {}", workerDTO);
        if (workerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkerDTO result = workerService.save(workerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workerDTO.getId().toString()))
            .body(result);
    }

    @PutMapping("/worker/updateFwVersion/{workerId}/{fwVersion}")
    public ResponseEntity<WorkerDTO> updateWorkerFirmwareVersion(@PathVariable Long workerId, @PathVariable String fwVersion) {
        Optional<WorkerDTO> optional = workerService.findOne(workerId);
        if (optional.isPresent()) {
            WorkerDTO workerDTO = optional.get();
            workerDTO.setDesc(fwVersion);
            WorkerDTO result = workerService.save(workerDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workerDTO.getId().toString()))
                .body(result);
        } else {
            return null;
        }

    }

    /**
     * {@code GET  /workers} : get all the workers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workers in body.
     */
    @GetMapping("/workers")
    public ResponseEntity<List<WorkerDTO>> getAllWorkers(Pageable pageable) {
        log.debug("REST request to get a page of Workers");
        Page<WorkerDTO> page = workerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/workers/all")
    public ResponseEntity<List<WorkerDTO>> getAllWorkers() {
        log.debug("REST request to get a page of Workers");
        return ResponseEntity.ok().body(workerService.findAll());
    }

    /**
     * {@code GET  /workers/:id} : get the "id" worker.
     *
     * @param id the id of the workerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/workers/{id}")
    public ResponseEntity<WorkerDTO> getWorker(@PathVariable Long id) {
        log.debug("REST request to get Worker : {}", id);
        Optional<WorkerDTO> workerDTO = workerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workerDTO);
    }

    @GetMapping("/workers/chart01")
    public ResponseEntity<Chart01DTO> getChart01() {
        return ResponseUtil.wrapOrNotFound(Optional.of(workerService.getReportsChart(7)));
    }

    @GetMapping("/workers/chart02")
    public ResponseEntity<Chart01DTO> getOrdersChart() {
        return ResponseUtil.wrapOrNotFound(Optional.of(workerService.getOrdersChart(7)));
    }

    @PostMapping("/workers/login")
    public ResponseEntity<WorkerDTO> getWorker(@RequestBody Worker requestWorker) {
        if (!workerService.findByLogin(requestWorker.getLogin()).isPresent()) {
            throw new WorkerLoginNotFoundException();
        }
        Optional<WorkerDTO> worker = workerService.findByLoginAndPassword(requestWorker.getLogin(), requestWorker.getPassword());
        if (!worker.isPresent()) {
            throw new WorkerWrongPassException();
        } else {
            return ResponseUtil.wrapOrNotFound(worker);
        }
    }

    /**
     * {@code DELETE  /workers/:id} : delete the "id" worker.
     *
     * @param id the id of the workerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/workers/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable Long id) {
        log.debug("REST request to delete Worker : {}", id);
        workerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
