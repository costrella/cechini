package com.costrella.cechini.service;

import com.costrella.cechini.domain.Store;
import com.costrella.cechini.repository.StoreRepository;
import com.costrella.cechini.service.dto.StoreDTO;
import com.costrella.cechini.service.dto.StoreDTOSimple;
import com.costrella.cechini.service.exceptions.StoreExistException;
import com.costrella.cechini.service.mapper.StoreMapper;
import com.costrella.cechini.service.mapper.StoreMapperSimple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Store}.
 */
@Service
@Transactional
public class StoreService {

    private final Logger log = LoggerFactory.getLogger(StoreService.class);

    private final StoreRepository storeRepository;

    private final StoreMapper storeMapper;
    private final StoreMapperSimple storeMapperSimple;

    public StoreService(StoreRepository storeRepository, StoreMapper storeMapper, StoreMapperSimple storeMapperSimple) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
        this.storeMapperSimple = storeMapperSimple;
    }

    /**
     * Save a store.
     *
     * @param storeDTO the entity to save.
     * @return the persisted entity.
     */
    public StoreDTO save(StoreDTO storeDTO) {
        log.debug("Request to save Store : {}", storeDTO);
        Store store = storeMapper.toEntity(storeDTO);
        if (ifExistStoreWithSameNameAndAddress(store)) {
            throw new StoreExistException();
        }
        store = storeRepository.save(store);
        return storeMapper.toDto(store);
    }

    private boolean ifExistStoreWithSameNameAndAddress(Store store) {
        Store example = new Store();
        example.setName(store.getName());
        example.setAddress(store.getAddress());
        return storeRepository.findOne(Example.of(example)).isPresent();
    }

    /**
     * Get all the stores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StoreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Stores");
        return storeRepository.findAll(pageable)
            .map(storeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<StoreDTOSimple> findAll() {
        return storeRepository.findAll().stream()
            .map(storeMapperSimple::toDto).collect(Collectors.toList());
    }


    /**
     * Get one store by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StoreDTO> findOne(Long id) {
        log.debug("Request to get Store : {}", id);
        return storeRepository.findById(id)
            .map(storeMapper::toDto);
    }

    /**
     * Delete the store by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Store : {}", id);
        storeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<StoreDTO> findAllByWorkerId(Pageable pageable, Long id) {
        return storeRepository.findAllByWorkerId(id, pageable)
            .map(storeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<StoreDTO> findAllByWorkerId(Long id) {
        return storeRepository.findAllByWorkerId(id).stream()
            .map(storeMapper::toDto).collect(Collectors.toList());
    }

}
