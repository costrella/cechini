package com.costrella.cechini.web.rest;

import com.costrella.cechini.service.StoreService;
import com.costrella.cechini.service.dto.Chart01DTO;
import com.costrella.cechini.service.dto.ChartDetail01DTO;
import com.costrella.cechini.service.dto.StoreDTO;
import com.costrella.cechini.service.dto.StoreDTOSimple;
import com.costrella.cechini.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.costrella.cechini.domain.Store}.
 */
@RestController
@RequestMapping("/api")
public class StoreResource {

    private final Logger log = LoggerFactory.getLogger(StoreResource.class);

    private static final String ENTITY_NAME = "store";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StoreService storeService;

    public StoreResource(StoreService storeService) {
        this.storeService = storeService;
    }


    @GetMapping("/stores/chart01")
    public ResponseEntity<Chart01DTO> getChart01() {
        Chart01DTO chart01DTO = new Chart01DTO();

        ChartDetail01DTO chartDetail01DTO = new ChartDetail01DTO();
        chartDetail01DTO.data = new ArrayList<>();
        chartDetail01DTO.data.add(100);
        chartDetail01DTO.data.add(200);
        chartDetail01DTO.data.add(400);
        chartDetail01DTO.label = "Marian 01";

        ChartDetail01DTO chartDetail02DTO = new ChartDetail01DTO();
        chartDetail02DTO.data = new ArrayList<>();
        chartDetail02DTO.data.add(300);
        chartDetail02DTO.data.add(400);
        chartDetail02DTO.data.add(100);
        chartDetail02DTO.label = "Marian 02";

        chart01DTO.details = new ArrayList<>();
        chart01DTO.details.add(chartDetail01DTO);
        chart01DTO.details.add(chartDetail02DTO);
        chart01DTO.monthsName = Arrays.asList(new String[]{"Lipiec", "Sierpien", "Wrzesien"});
        return ResponseUtil.wrapOrNotFound(Optional.of(chart01DTO));
    }

    /**
     * {@code POST  /stores} : Create a new store.
     *
     * @param storeDTO the storeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new storeDTO, or with status {@code 400 (Bad Request)} if the store has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stores")
    public ResponseEntity<StoreDTO> createStore(@Valid @RequestBody StoreDTO storeDTO) throws URISyntaxException {
        log.debug("REST request to save Store : {}", storeDTO);
        if (storeDTO.getId() != null) {
            throw new BadRequestAlertException("A new store cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StoreDTO result = storeService.save(storeDTO);
//        try {
//            result = storeService.save(storeDTO);
//        } catch (DataIntegrityViolationException e2) {
//            return ResponseEntity
//
//                .created(new URI("/api/stores/" + result.getId()))
//                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
//                .body(result);
//        }

        return ResponseEntity.created(new URI("/api/stores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stores} : Updates an existing store.
     *
     * @param storeDTO the storeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storeDTO,
     * or with status {@code 400 (Bad Request)} if the storeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the storeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stores")
    public ResponseEntity<StoreDTO> updateStore(@Valid @RequestBody StoreDTO storeDTO) throws URISyntaxException, PSQLException {
        log.debug("REST request to update Store : {}", storeDTO);
        if (storeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StoreDTO result = storeService.update(storeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, storeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stores} : get all the stores.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stores in body.
     */
    @GetMapping("/stores")
    public ResponseEntity<List<StoreDTO>> getAllStores(Pageable pageable) {
        log.debug("REST request to get a page of Stores");
        Page<StoreDTO> page = storeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/stores/worker/{id}")
    public ResponseEntity<List<StoreDTO>> getAllByWorkerId(Pageable pageable, @PathVariable Long id) {
        Page<StoreDTO> page = storeService.findAllByWorkerId(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/stores/worker/{id}/mystores")
    public ResponseEntity<List<StoreDTO>> getAllByWorkerId(@PathVariable Long id) {
        return ResponseEntity.ok().body(storeService.findAllByWorkerId(id));
    }

    @GetMapping("/stores/all")
    public ResponseEntity<List<StoreDTOSimple>> getAllStores() {
        log.debug("REST request to get a page of Workers");
        return ResponseEntity.ok().body(storeService.findAll());
    }

    /**
     * {@code GET  /stores/:id} : get the "id" store.
     *
     * @param id the id of the storeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the storeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stores/{id}")
    public ResponseEntity<StoreDTO> getStore(@PathVariable Long id) {
        log.debug("REST request to get Store : {}", id);
        Optional<StoreDTO> storeDTO = storeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(storeDTO);
    }

    /**
     * {@code DELETE  /stores/:id} : delete the "id" store.
     *
     * @param id the id of the storeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stores/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        log.debug("REST request to delete Store : {}", id);
        storeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
