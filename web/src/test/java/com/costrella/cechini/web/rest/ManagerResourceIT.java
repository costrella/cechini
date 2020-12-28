package com.costrella.cechini.web.rest;

import com.costrella.cechini.CechiniApp;
import com.costrella.cechini.domain.Manager;
import com.costrella.cechini.repository.ManagerRepository;
import com.costrella.cechini.service.ManagerService;
import com.costrella.cechini.service.dto.ManagerDTO;
import com.costrella.cechini.service.mapper.ManagerMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ManagerResource} REST controller.
 */
@SpringBootTest(classes = CechiniApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ManagerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_HIRED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HIRED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ManagerRepository managerRepository;

    @Mock
    private ManagerRepository managerRepositoryMock;

    @Autowired
    private ManagerMapper managerMapper;

    @Mock
    private ManagerService managerServiceMock;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restManagerMockMvc;

    private Manager manager;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Manager createEntity(EntityManager em) {
        Manager manager = new Manager()
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .hiredDate(DEFAULT_HIRED_DATE);
        return manager;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Manager createUpdatedEntity(EntityManager em) {
        Manager manager = new Manager()
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .hiredDate(UPDATED_HIRED_DATE);
        return manager;
    }

    @BeforeEach
    public void initTest() {
        manager = createEntity(em);
    }

    @Test
    @Transactional
    public void createManager() throws Exception {
        int databaseSizeBeforeCreate = managerRepository.findAll().size();
        // Create the Manager
        ManagerDTO managerDTO = managerMapper.toDto(manager);
        restManagerMockMvc.perform(post("/api/managers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(managerDTO)))
            .andExpect(status().isCreated());

        // Validate the Manager in the database
        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeCreate + 1);
        Manager testManager = managerList.get(managerList.size() - 1);
        assertThat(testManager.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testManager.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testManager.getHiredDate()).isEqualTo(DEFAULT_HIRED_DATE);
    }

    @Test
    @Transactional
    public void createManagerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = managerRepository.findAll().size();

        // Create the Manager with an existing ID
        manager.setId(1L);
        ManagerDTO managerDTO = managerMapper.toDto(manager);

        // An entity with an existing ID cannot be created, so this API call must fail
        restManagerMockMvc.perform(post("/api/managers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(managerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Manager in the database
        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = managerRepository.findAll().size();
        // set the field null
        manager.setName(null);

        // Create the Manager, which fails.
        ManagerDTO managerDTO = managerMapper.toDto(manager);


        restManagerMockMvc.perform(post("/api/managers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(managerDTO)))
            .andExpect(status().isBadRequest());

        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = managerRepository.findAll().size();
        // set the field null
        manager.setSurname(null);

        // Create the Manager, which fails.
        ManagerDTO managerDTO = managerMapper.toDto(manager);


        restManagerMockMvc.perform(post("/api/managers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(managerDTO)))
            .andExpect(status().isBadRequest());

        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllManagers() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList
        restManagerMockMvc.perform(get("/api/managers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manager.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].hiredDate").value(hasItem(DEFAULT_HIRED_DATE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllManagersWithEagerRelationshipsIsEnabled() throws Exception {
        when(managerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restManagerMockMvc.perform(get("/api/managers?eagerload=true"))
            .andExpect(status().isOk());

        verify(managerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllManagersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(managerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restManagerMockMvc.perform(get("/api/managers?eagerload=true"))
            .andExpect(status().isOk());

        verify(managerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getManager() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get the manager
        restManagerMockMvc.perform(get("/api/managers/{id}", manager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(manager.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME))
            .andExpect(jsonPath("$.hiredDate").value(DEFAULT_HIRED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingManager() throws Exception {
        // Get the manager
        restManagerMockMvc.perform(get("/api/managers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateManager() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        int databaseSizeBeforeUpdate = managerRepository.findAll().size();

        // Update the manager
        Manager updatedManager = managerRepository.findById(manager.getId()).get();
        // Disconnect from session so that the updates on updatedManager are not directly saved in db
        em.detach(updatedManager);
        updatedManager
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .hiredDate(UPDATED_HIRED_DATE);
        ManagerDTO managerDTO = managerMapper.toDto(updatedManager);

        restManagerMockMvc.perform(put("/api/managers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(managerDTO)))
            .andExpect(status().isOk());

        // Validate the Manager in the database
        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeUpdate);
        Manager testManager = managerList.get(managerList.size() - 1);
        assertThat(testManager.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testManager.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testManager.getHiredDate()).isEqualTo(UPDATED_HIRED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingManager() throws Exception {
        int databaseSizeBeforeUpdate = managerRepository.findAll().size();

        // Create the Manager
        ManagerDTO managerDTO = managerMapper.toDto(manager);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManagerMockMvc.perform(put("/api/managers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(managerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Manager in the database
        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteManager() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        int databaseSizeBeforeDelete = managerRepository.findAll().size();

        // Delete the manager
        restManagerMockMvc.perform(delete("/api/managers/{id}", manager.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
