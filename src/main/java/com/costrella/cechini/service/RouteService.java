package com.costrella.cechini.service;

import com.costrella.cechini.domain.Route;
import com.costrella.cechini.repository.RouteRepository;
import com.costrella.cechini.service.dto.RouteDTO;
import com.costrella.cechini.service.mapper.RouteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Route}.
 */
@Service
@Transactional
public class RouteService {

    private final Logger log = LoggerFactory.getLogger(RouteService.class);

    private final RouteRepository routeRepository;

    private final RouteMapper routeMapper;

    public RouteService(RouteRepository routeRepository, RouteMapper routeMapper) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
    }

    /**
     * Save a route.
     *
     * @param routeDTO the entity to save.
     * @return the persisted entity.
     */
    public RouteDTO save(RouteDTO routeDTO) {
        log.debug("Request to save Route : {}", routeDTO);
        Route route = routeMapper.toEntity(routeDTO);
        route = routeRepository.save(route);
        return routeMapper.toDto(route);
    }

    /**
     * Get all the routes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RouteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Routes");
        return routeRepository.findAll(pageable)
            .map(routeMapper::toDto);
    }


    /**
     * Get one route by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RouteDTO> findOne(Long id) {
        log.debug("Request to get Route : {}", id);
        return routeRepository.findById(id)
            .map(routeMapper::toDto);
    }

    /**
     * Delete the route by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Route : {}", id);
        routeRepository.deleteById(id);
    }
}
