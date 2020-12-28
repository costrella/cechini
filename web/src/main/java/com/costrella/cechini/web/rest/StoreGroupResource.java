package com.costrella.cechini.web.rest;

import com.costrella.cechini.service.StoreGroupService;
import com.costrella.cechini.web.rest.errors.BadRequestAlertException;
import com.costrella.cechini.service.dto.StoreGroupDTO;

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
 * REST controller for managing {@link com.costrella.cechini.domain.StoreGroup}.
 */
@RestController
@RequestMapping("/api")
public class StoreGroupResource {

    private final Logger log = LoggerFactory.getLogger(StoreGroupResource.class);

    private static final String ENTITY_NAME = "storeGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StoreGroupService storeGroupService;

    public StoreGroupResource(StoreGroupService storeGroupService) {
        this.storeGroupService = storeGroupService;
    }

    /**
     * {@code POST  /store-groups} : Create a new storeGroup.
     *
     * @param storeGroupDTO the storeGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new storeGroupDTO, or with status {@code 400 (Bad Request)} if the storeGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/store-groups")
    public ResponseEntity<StoreGroupDTO> createStoreGroup(@Valid @RequestBody StoreGroupDTO storeGroupDTO) throws URISyntaxException {
        log.debug("REST request to save StoreGroup : {}", storeGroupDTO);
        if (storeGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new storeGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StoreGroupDTO result = storeGroupService.save(storeGroupDTO);
        return ResponseEntity.created(new URI("/api/store-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /store-groups} : Updates an existing storeGroup.
     *
     * @param storeGroupDTO the storeGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storeGroupDTO,
     * or with status {@code 400 (Bad Request)} if the storeGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the storeGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/store-groups")
    public ResponseEntity<StoreGroupDTO> updateStoreGroup(@Valid @RequestBody StoreGroupDTO storeGroupDTO) throws URISyntaxException {
        log.debug("REST request to update StoreGroup : {}", storeGroupDTO);
        if (storeGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StoreGroupDTO result = storeGroupService.save(storeGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, storeGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /store-groups} : get all the storeGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of storeGroups in body.
     */
    @GetMapping("/store-groups")
    public ResponseEntity<List<StoreGroupDTO>> getAllStoreGroups(Pageable pageable) {
        log.debug("REST request to get a page of StoreGroups");
        Page<StoreGroupDTO> page = storeGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/store-groups/all")
    public ResponseEntity<List<StoreGroupDTO>> getAllStoreGroups() {
        return ResponseEntity.ok().body(storeGroupService.findAll());
    }

    /**
     * {@code GET  /store-groups/:id} : get the "id" storeGroup.
     *
     * @param id the id of the storeGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the storeGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/store-groups/{id}")
    public ResponseEntity<StoreGroupDTO> getStoreGroup(@PathVariable Long id) {
        log.debug("REST request to get StoreGroup : {}", id);
        Optional<StoreGroupDTO> storeGroupDTO = storeGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(storeGroupDTO);
    }

    /**
     * {@code DELETE  /store-groups/:id} : delete the "id" storeGroup.
     *
     * @param id the id of the storeGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/store-groups/{id}")
    public ResponseEntity<Void> deleteStoreGroup(@PathVariable Long id) {
        log.debug("REST request to delete StoreGroup : {}", id);
        storeGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
