package com.costrella.cechini.web.rest;

import com.costrella.cechini.CechiniApp;
import com.costrella.cechini.domain.PhotoFile;
import com.costrella.cechini.repository.PhotoFileRepository;
import com.costrella.cechini.service.PhotoFileService;
import com.costrella.cechini.service.dto.PhotoFileDTO;
import com.costrella.cechini.service.mapper.PhotoFileMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PhotoFileResource} REST controller.
 */
@SpringBootTest(classes = CechiniApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PhotoFileResourceIT {

    private static final byte[] DEFAULT_VALUE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VALUE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_VALUE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VALUE_CONTENT_TYPE = "image/png";

    @Autowired
    private PhotoFileRepository photoFileRepository;

    @Autowired
    private PhotoFileMapper photoFileMapper;

    @Autowired
    private PhotoFileService photoFileService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhotoFileMockMvc;

    private PhotoFile photoFile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhotoFile createEntity(EntityManager em) {
        PhotoFile photoFile = new PhotoFile()
            .value(DEFAULT_VALUE)
            .valueContentType(DEFAULT_VALUE_CONTENT_TYPE);
        return photoFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhotoFile createUpdatedEntity(EntityManager em) {
        PhotoFile photoFile = new PhotoFile()
            .value(UPDATED_VALUE)
            .valueContentType(UPDATED_VALUE_CONTENT_TYPE);
        return photoFile;
    }

    @BeforeEach
    public void initTest() {
        photoFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhotoFile() throws Exception {
        int databaseSizeBeforeCreate = photoFileRepository.findAll().size();
        // Create the PhotoFile
        PhotoFileDTO photoFileDTO = photoFileMapper.toDto(photoFile);
        restPhotoFileMockMvc.perform(post("/api/photo-files").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(photoFileDTO)))
            .andExpect(status().isCreated());

        // Validate the PhotoFile in the database
        List<PhotoFile> photoFileList = photoFileRepository.findAll();
        assertThat(photoFileList).hasSize(databaseSizeBeforeCreate + 1);
        PhotoFile testPhotoFile = photoFileList.get(photoFileList.size() - 1);
        assertThat(testPhotoFile.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPhotoFile.getValueContentType()).isEqualTo(DEFAULT_VALUE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPhotoFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = photoFileRepository.findAll().size();

        // Create the PhotoFile with an existing ID
        photoFile.setId(1L);
        PhotoFileDTO photoFileDTO = photoFileMapper.toDto(photoFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhotoFileMockMvc.perform(post("/api/photo-files").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(photoFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PhotoFile in the database
        List<PhotoFile> photoFileList = photoFileRepository.findAll();
        assertThat(photoFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPhotoFiles() throws Exception {
        // Initialize the database
        photoFileRepository.saveAndFlush(photoFile);

        // Get all the photoFileList
        restPhotoFileMockMvc.perform(get("/api/photo-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(photoFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].valueContentType").value(hasItem(DEFAULT_VALUE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(Base64Utils.encodeToString(DEFAULT_VALUE))));
    }
    
    @Test
    @Transactional
    public void getPhotoFile() throws Exception {
        // Initialize the database
        photoFileRepository.saveAndFlush(photoFile);

        // Get the photoFile
        restPhotoFileMockMvc.perform(get("/api/photo-files/{id}", photoFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(photoFile.getId().intValue()))
            .andExpect(jsonPath("$.valueContentType").value(DEFAULT_VALUE_CONTENT_TYPE))
            .andExpect(jsonPath("$.value").value(Base64Utils.encodeToString(DEFAULT_VALUE)));
    }
    @Test
    @Transactional
    public void getNonExistingPhotoFile() throws Exception {
        // Get the photoFile
        restPhotoFileMockMvc.perform(get("/api/photo-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhotoFile() throws Exception {
        // Initialize the database
        photoFileRepository.saveAndFlush(photoFile);

        int databaseSizeBeforeUpdate = photoFileRepository.findAll().size();

        // Update the photoFile
        PhotoFile updatedPhotoFile = photoFileRepository.findById(photoFile.getId()).get();
        // Disconnect from session so that the updates on updatedPhotoFile are not directly saved in db
        em.detach(updatedPhotoFile);
        updatedPhotoFile
            .value(UPDATED_VALUE)
            .valueContentType(UPDATED_VALUE_CONTENT_TYPE);
        PhotoFileDTO photoFileDTO = photoFileMapper.toDto(updatedPhotoFile);

        restPhotoFileMockMvc.perform(put("/api/photo-files").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(photoFileDTO)))
            .andExpect(status().isOk());

        // Validate the PhotoFile in the database
        List<PhotoFile> photoFileList = photoFileRepository.findAll();
        assertThat(photoFileList).hasSize(databaseSizeBeforeUpdate);
        PhotoFile testPhotoFile = photoFileList.get(photoFileList.size() - 1);
        assertThat(testPhotoFile.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPhotoFile.getValueContentType()).isEqualTo(UPDATED_VALUE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPhotoFile() throws Exception {
        int databaseSizeBeforeUpdate = photoFileRepository.findAll().size();

        // Create the PhotoFile
        PhotoFileDTO photoFileDTO = photoFileMapper.toDto(photoFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhotoFileMockMvc.perform(put("/api/photo-files").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(photoFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PhotoFile in the database
        List<PhotoFile> photoFileList = photoFileRepository.findAll();
        assertThat(photoFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePhotoFile() throws Exception {
        // Initialize the database
        photoFileRepository.saveAndFlush(photoFile);

        int databaseSizeBeforeDelete = photoFileRepository.findAll().size();

        // Delete the photoFile
        restPhotoFileMockMvc.perform(delete("/api/photo-files/{id}", photoFile.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhotoFile> photoFileList = photoFileRepository.findAll();
        assertThat(photoFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
