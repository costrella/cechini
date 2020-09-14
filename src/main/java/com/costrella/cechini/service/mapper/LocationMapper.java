package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.LocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CityMapper.class})
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {

    @Mapping(source = "city.id", target = "cityId")
    LocationDTO toDto(Location location);

    @Mapping(target = "stores", ignore = true)
    @Mapping(target = "removeStore", ignore = true)
    @Mapping(target = "routes", ignore = true)
    @Mapping(target = "removeRoute", ignore = true)
    @Mapping(target = "warehouses", ignore = true)
    @Mapping(target = "removeWarehouse", ignore = true)
    @Mapping(source = "cityId", target = "city")
    Location toEntity(LocationDTO locationDTO);

    default Location fromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }
}
