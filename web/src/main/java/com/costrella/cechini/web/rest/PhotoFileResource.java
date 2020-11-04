package com.costrella.cechini.web.rest;

import com.costrella.cechini.service.PhotoFileService;
import com.costrella.cechini.web.rest.errors.BadRequestAlertException;
import com.costrella.cechini.service.dto.PhotoFileDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.costrella.cechini.domain.PhotoFile}.
 */
@RestController
@RequestMapping("/api")
public class PhotoFileResource {

    private final Logger log = LoggerFactory.getLogger(PhotoFileResource.class);

    private static final String ENTITY_NAME = "photoFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhotoFileService photoFileService;

    public PhotoFileResource(PhotoFileService photoFileService) {
        this.photoFileService = photoFileService;
    }

    /**
     * {@code POST  /photo-files} : Create a new photoFile.
     *
     * @param photoFileDTO the photoFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new photoFileDTO, or with status {@code 400 (Bad Request)} if the photoFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/photo-files")
    public ResponseEntity<PhotoFileDTO> createPhotoFile(@RequestBody PhotoFileDTO photoFileDTO) throws URISyntaxException {
        log.debug("REST request to save PhotoFile : {}", photoFileDTO);
        if (photoFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new photoFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhotoFileDTO result = photoFileService.save(photoFileDTO);
        return ResponseEntity.created(new URI("/api/photo-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /photo-files} : Updates an existing photoFile.
     *
     * @param photoFileDTO the photoFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated photoFileDTO,
     * or with status {@code 400 (Bad Request)} if the photoFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the photoFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/photo-files")
    public ResponseEntity<PhotoFileDTO> updatePhotoFile(@RequestBody PhotoFileDTO photoFileDTO) throws URISyntaxException {
        log.debug("REST request to update PhotoFile : {}", photoFileDTO);
        if (photoFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhotoFileDTO result = photoFileService.save(photoFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, photoFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /photo-files} : get all the photoFiles.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of photoFiles in body.
     */
    @GetMapping("/photo-files")
    public List<PhotoFileDTO> getAllPhotoFiles(@RequestParam(required = false) String filter) {
        if ("photo-is-null".equals(filter)) {
            log.debug("REST request to get all PhotoFiles where photo is null");
            return photoFileService.findAllWherePhotoIsNull();
        }
        log.debug("REST request to get all PhotoFiles");
        return photoFileService.findAll();
    }

    /**
     * {@code GET  /photo-files/:id} : get the "id" photoFile.
     *
     * @param id the id of the photoFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the photoFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/photo-files/{id}")
    public ResponseEntity<PhotoFileDTO> getPhotoFile(@PathVariable Long id) {
        log.debug("REST request to get PhotoFile : {}", id);
        Optional<PhotoFileDTO> photoFileDTO = photoFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(photoFileDTO);
    }

    /**
     * {@code DELETE  /photo-files/:id} : delete the "id" photoFile.
     *
     * @param id the id of the photoFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/photo-files/{id}")
    public ResponseEntity<Void> deletePhotoFile(@PathVariable Long id) {
        log.debug("REST request to delete PhotoFile : {}", id);
        photoFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
