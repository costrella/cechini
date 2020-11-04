package com.costrella.cechini.service;

import com.costrella.cechini.domain.PhotoFile;
import com.costrella.cechini.repository.PhotoFileRepository;
import com.costrella.cechini.service.dto.PhotoFileDTO;
import com.costrella.cechini.service.mapper.PhotoFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link PhotoFile}.
 */
@Service
@Transactional
public class PhotoFileService {

    private final Logger log = LoggerFactory.getLogger(PhotoFileService.class);

    private final PhotoFileRepository photoFileRepository;

    private final PhotoFileMapper photoFileMapper;

    public PhotoFileService(PhotoFileRepository photoFileRepository, PhotoFileMapper photoFileMapper) {
        this.photoFileRepository = photoFileRepository;
        this.photoFileMapper = photoFileMapper;
    }

    /**
     * Save a photoFile.
     *
     * @param photoFileDTO the entity to save.
     * @return the persisted entity.
     */
    public PhotoFileDTO save(PhotoFileDTO photoFileDTO) {
        log.debug("Request to save PhotoFile : {}", photoFileDTO);
        PhotoFile photoFile = photoFileMapper.toEntity(photoFileDTO);
        photoFile = photoFileRepository.save(photoFile);
        return photoFileMapper.toDto(photoFile);
    }

    /**
     * Get all the photoFiles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PhotoFileDTO> findAll() {
        log.debug("Request to get all PhotoFiles");
        return photoFileRepository.findAll().stream()
            .map(photoFileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  Get all the photoFiles where Photo is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<PhotoFileDTO> findAllWherePhotoIsNull() {
        log.debug("Request to get all photoFiles where Photo is null");
        return StreamSupport
            .stream(photoFileRepository.findAll().spliterator(), false)
            .filter(photoFile -> photoFile.getPhoto() == null)
            .map(photoFileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one photoFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PhotoFileDTO> findOne(Long id) {
        log.debug("Request to get PhotoFile : {}", id);
        return photoFileRepository.findById(id)
            .map(photoFileMapper::toDto);
    }

    /**
     * Delete the photoFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PhotoFile : {}", id);
        photoFileRepository.deleteById(id);
    }
}
