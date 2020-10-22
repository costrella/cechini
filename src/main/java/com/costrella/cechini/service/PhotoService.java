package com.costrella.cechini.service;

import com.costrella.cechini.domain.Photo;
import com.costrella.cechini.repository.PhotoRepository;
import com.costrella.cechini.service.dto.PhotoDTO;
import com.costrella.cechini.service.mapper.PhotoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Photo}.
 */
@Service
@Transactional
public class PhotoService {

    private final Logger log = LoggerFactory.getLogger(PhotoService.class);

    private final PhotoRepository photoRepository;

    private final PhotoMapper photoMapper;

    public PhotoService(PhotoRepository photoRepository, PhotoMapper photoMapper) {
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
    }

    /**
     * Save a photo.
     *
     * @param photoDTO the entity to save.
     * @return the persisted entity.
     */
    public PhotoDTO save(PhotoDTO photoDTO) {
        log.debug("Request to save Photo : {}", photoDTO);
        Photo photo = photoMapper.toEntity(photoDTO);
        File file = new File("C:\\users\\mikostrz\\mkostrzewa\\others\\cechini\\test.pdf");
        try {
            byte[] array = Files.readAllBytes(file.toPath());
            System.out.println("hi !");
            photo.setValue(array);
        } catch (IOException e) {
            e.printStackTrace();
        }
        photo = photoRepository.save(photo);
        return photoMapper.toDto(photo);
    }

    public static void main(String []args){
        System.out.println("hi");
        File file = new File("C:\\users\\mikostrz\\mkostrzewa\\others\\cechini\\test.pdf");
        try {
            byte[] array = Files.readAllBytes(file.toPath());
            System.out.println("hi !" + array.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all the photos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PhotoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Photos");
        return photoRepository.findAll(pageable)
            .map(photoMapper::toDto);
    }


    /**
     * Get one photo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PhotoDTO> findOne(Long id) {
        log.debug("Request to get Photo : {}", id);
        return photoRepository.findById(id)
            .map(photoMapper::toDto);
    }

    /**
     * Delete the photo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Photo : {}", id);
        photoRepository.deleteById(id);
    }
}
