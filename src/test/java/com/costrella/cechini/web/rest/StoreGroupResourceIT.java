package com.costrella.cechini.web.rest;

import com.costrella.cechini.CechiniApp;
import com.costrella.cechini.domain.StoreGroup;
import com.costrella.cechini.repository.StoreGroupRepository;
import com.costrella.cechini.service.StoreGroupService;
import com.costrella.cechini.service.dto.StoreGroupDTO;
import com.costrella.cechini.service.mapper.StoreGroupMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link StoreGroupResource} REST controller.
 */
@SpringBootTest(classes = CechiniApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StoreGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private StoreGroupRepository storeGroupRepository;

    @Autowired
    private StoreGroupMapper storeGroupMapper;

    @Autowired
    private StoreGroupService storeGroupService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStoreGroupMockMvc;

    private StoreGroup storeGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoreGroup createEntity(EntityManager em) {
        StoreGroup storeGroup = new StoreGroup()
            .name(DEFAULT_NAME);
        return storeGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoreGroup createUpdatedEntity(EntityManager em) {
        StoreGroup storeGroup = new StoreGroup()
            .name(UPDATED_NAME);
        return storeGroup;
    }

    @BeforeEach
    public void initTest() {
        storeGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createStoreGroup() throws Exception {
        int databaseSizeBeforeCreate = storeGroupRepository.findAll().size();
        // Create the StoreGroup
        StoreGroupDTO storeGroupDTO = storeGroupMapper.toDto(storeGroup);
        restStoreGroupMockMvc.perform(post("/api/store-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(storeGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the StoreGroup in the database
        List<StoreGroup> storeGroupList = storeGroupRepository.findAll();
        assertThat(storeGroupList).hasSize(databaseSizeBeforeCreate + 1);
        StoreGroup testStoreGroup = storeGroupList.get(storeGroupList.size() - 1);
        assertThat(testStoreGroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createStoreGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = storeGroupRepository.findAll().size();

        // Create the StoreGroup with an existing ID
        storeGroup.setId(1L);
        StoreGroupDTO storeGroupDTO = storeGroupMapper.toDto(storeGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoreGroupMockMvc.perform(post("/api/store-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(storeGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StoreGroup in the database
        List<StoreGroup> storeGroupList = storeGroupRepository.findAll();
        assertThat(storeGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeGroupRepository.findAll().size();
        // set the field null
        storeGroup.setName(null);

        // Create the StoreGroup, which fails.
        StoreGroupDTO storeGroupDTO = storeGroupMapper.toDto(storeGroup);


        restStoreGroupMockMvc.perform(post("/api/store-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(storeGroupDTO)))
            .andExpect(status().isBadRequest());

        List<StoreGroup> storeGroupList = storeGroupRepository.findAll();
        assertThat(storeGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStoreGroups() throws Exception {
        // Initialize the database
        storeGroupRepository.saveAndFlush(storeGroup);

        // Get all the storeGroupList
        restStoreGroupMockMvc.perform(get("/api/store-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storeGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getStoreGroup() throws Exception {
        // Initialize the database
        storeGroupRepository.saveAndFlush(storeGroup);

        // Get the storeGroup
        restStoreGroupMockMvc.perform(get("/api/store-groups/{id}", storeGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(storeGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingStoreGroup() throws Exception {
        // Get the storeGroup
        restStoreGroupMockMvc.perform(get("/api/store-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStoreGroup() throws Exception {
        // Initialize the database
        storeGroupRepository.saveAndFlush(storeGroup);

        int databaseSizeBeforeUpdate = storeGroupRepository.findAll().size();

        // Update the storeGroup
        StoreGroup updatedStoreGroup = storeGroupRepository.findById(storeGroup.getId()).get();
        // Disconnect from session so that the updates on updatedStoreGroup are not directly saved in db
        em.detach(updatedStoreGroup);
        updatedStoreGroup
            .name(UPDATED_NAME);
        StoreGroupDTO storeGroupDTO = storeGroupMapper.toDto(updatedStoreGroup);

        restStoreGroupMockMvc.perform(put("/api/store-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(storeGroupDTO)))
            .andExpect(status().isOk());

        // Validate the StoreGroup in the database
        List<StoreGroup> storeGroupList = storeGroupRepository.findAll();
        assertThat(storeGroupList).hasSize(databaseSizeBeforeUpdate);
        StoreGroup testStoreGroup = storeGroupList.get(storeGroupList.size() - 1);
        assertThat(testStoreGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingStoreGroup() throws Exception {
        int databaseSizeBeforeUpdate = storeGroupRepository.findAll().size();

        // Create the StoreGroup
        StoreGroupDTO storeGroupDTO = storeGroupMapper.toDto(storeGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoreGroupMockMvc.perform(put("/api/store-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(storeGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StoreGroup in the database
        List<StoreGroup> storeGroupList = storeGroupRepository.findAll();
        assertThat(storeGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStoreGroup() throws Exception {
        // Initialize the database
        storeGroupRepository.saveAndFlush(storeGroup);

        int databaseSizeBeforeDelete = storeGroupRepository.findAll().size();

        // Delete the storeGroup
        restStoreGroupMockMvc.perform(delete("/api/store-groups/{id}", storeGroup.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StoreGroup> storeGroupList = storeGroupRepository.findAll();
        assertThat(storeGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
