package com.costrella.cechini.service;

import com.costrella.cechini.domain.StoreGroup;
import com.costrella.cechini.repository.StoreGroupRepository;
import com.costrella.cechini.service.dto.StoreGroupDTO;
import com.costrella.cechini.service.mapper.StoreGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StoreGroup}.
 */
@Service
@Transactional
public class StoreGroupService {

    private final Logger log = LoggerFactory.getLogger(StoreGroupService.class);

    private final StoreGroupRepository storeGroupRepository;

    private final StoreGroupMapper storeGroupMapper;

    public StoreGroupService(StoreGroupRepository storeGroupRepository, StoreGroupMapper storeGroupMapper) {
        this.storeGroupRepository = storeGroupRepository;
        this.storeGroupMapper = storeGroupMapper;
    }

    /**
     * Save a storeGroup.
     *
     * @param storeGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public StoreGroupDTO save(StoreGroupDTO storeGroupDTO) {
        log.debug("Request to save StoreGroup : {}", storeGroupDTO);
        StoreGroup storeGroup = storeGroupMapper.toEntity(storeGroupDTO);
        storeGroup = storeGroupRepository.save(storeGroup);
        return storeGroupMapper.toDto(storeGroup);
    }

    /**
     * Get all the storeGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StoreGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StoreGroups");
        return storeGroupRepository.findAll(pageable)
            .map(storeGroupMapper::toDto);
    }


    /**
     * Get one storeGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StoreGroupDTO> findOne(Long id) {
        log.debug("Request to get StoreGroup : {}", id);
        return storeGroupRepository.findById(id)
            .map(storeGroupMapper::toDto);
    }

    /**
     * Delete the storeGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StoreGroup : {}", id);
        storeGroupRepository.deleteById(id);
    }
}
