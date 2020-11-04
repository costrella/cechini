package com.costrella.cechini.service;

import com.costrella.cechini.domain.Manager;
import com.costrella.cechini.repository.ManagerRepository;
import com.costrella.cechini.service.dto.ManagerDTO;
import com.costrella.cechini.service.mapper.ManagerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Manager}.
 */
@Service
@Transactional
public class ManagerService {

    private final Logger log = LoggerFactory.getLogger(ManagerService.class);

    private final ManagerRepository managerRepository;

    private final ManagerMapper managerMapper;

    public ManagerService(ManagerRepository managerRepository, ManagerMapper managerMapper) {
        this.managerRepository = managerRepository;
        this.managerMapper = managerMapper;
    }

    /**
     * Save a manager.
     *
     * @param managerDTO the entity to save.
     * @return the persisted entity.
     */
    public ManagerDTO save(ManagerDTO managerDTO) {
        log.debug("Request to save Manager : {}", managerDTO);
        Manager manager = managerMapper.toEntity(managerDTO);
        manager = managerRepository.save(manager);
        return managerMapper.toDto(manager);
    }

    /**
     * Get all the managers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ManagerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Managers");
        return managerRepository.findAll(pageable)
            .map(managerMapper::toDto);
    }


    /**
     * Get all the managers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ManagerDTO> findAllWithEagerRelationships(Pageable pageable) {
        return managerRepository.findAllWithEagerRelationships(pageable).map(managerMapper::toDto);
    }

    /**
     * Get one manager by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ManagerDTO> findOne(Long id) {
        log.debug("Request to get Manager : {}", id);
        return managerRepository.findOneWithEagerRelationships(id)
            .map(managerMapper::toDto);
    }

    /**
     * Delete the manager by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Manager : {}", id);
        managerRepository.deleteById(id);
    }
}
