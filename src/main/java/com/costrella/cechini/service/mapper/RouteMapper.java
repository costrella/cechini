package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.RouteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Route} and its DTO {@link RouteDTO}.
 */
@Mapper(componentModel = "spring", uses = {WorkerMapper.class, LocationMapper.class})
public interface RouteMapper extends EntityMapper<RouteDTO, Route> {

    @Mapping(source = "worker.id", target = "workerId")
    @Mapping(source = "worker.surname", target = "workerSurname")
    @Mapping(source = "location.id", target = "locationId")
    RouteDTO toDto(Route route);

    @Mapping(source = "workerId", target = "worker")
    @Mapping(source = "locationId", target = "location")
    Route toEntity(RouteDTO routeDTO);

    default Route fromId(Long id) {
        if (id == null) {
            return null;
        }
        Route route = new Route();
        route.setId(id);
        return route;
    }
}
