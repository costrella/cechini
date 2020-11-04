package com.costrella.cechini.web.rest;

import com.costrella.cechini.service.ManagerService;
import com.costrella.cechini.web.rest.errors.BadRequestAlertException;
import com.costrella.cechini.service.dto.ManagerDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.costrella.cechini.domain.Manager}.
 */
@RestController
@RequestMapping("/api")
public class ManagerResource {

    private final Logger log = LoggerFactory.getLogger(ManagerResource.class);

    private static final String ENTITY_NAME = "manager";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ManagerService managerService;

    public ManagerResource(ManagerService managerService) {
        this.managerService = managerService;
    }

    /**
     * {@code POST  /managers} : Create a new manager.
     *
     * @param managerDTO the managerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new managerDTO, or with status {@code 400 (Bad Request)} if the manager has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/managers")
    public ResponseEntity<ManagerDTO> createManager(@Valid @RequestBody ManagerDTO managerDTO) throws URISyntaxException {
        log.debug("REST request to save Manager : {}", managerDTO);
        if (managerDTO.getId() != null) {
            throw new BadRequestAlertException("A new manager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ManagerDTO result = managerService.save(managerDTO);
        return ResponseEntity.created(new URI("/api/managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /managers} : Updates an existing manager.
     *
     * @param managerDTO the managerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated managerDTO,
     * or with status {@code 400 (Bad Request)} if the managerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the managerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/managers")
    public ResponseEntity<ManagerDTO> updateManager(@Valid @RequestBody ManagerDTO managerDTO) throws URISyntaxException {
        log.debug("REST request to update Manager : {}", managerDTO);
        if (managerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ManagerDTO result = managerService.save(managerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, managerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /managers} : get all the managers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of managers in body.
     */
    @GetMapping("/managers")
    public ResponseEntity<List<ManagerDTO>> getAllManagers(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Managers");
        Page<ManagerDTO> page;
        if (eagerload) {
            page = managerService.findAllWithEagerRelationships(pageable);
        } else {
            page = managerService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /managers/:id} : get the "id" manager.
     *
     * @param id the id of the managerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the managerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/managers/{id}")
    public ResponseEntity<ManagerDTO> getManager(@PathVariable Long id) {
        log.debug("REST request to get Manager : {}", id);
        Optional<ManagerDTO> managerDTO = managerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(managerDTO);
    }

    /**
     * {@code DELETE  /managers/:id} : delete the "id" manager.
     *
     * @param id the id of the managerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/managers/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
        log.debug("REST request to delete Manager : {}", id);
        managerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
