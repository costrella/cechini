package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.CityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CityMapper extends EntityMapper<CityDTO, City> {


    @Mapping(target = "locations", ignore = true)
    @Mapping(target = "removeLocation", ignore = true)
    City toEntity(CityDTO cityDTO);

    default City fromId(Long id) {
        if (id == null) {
            return null;
        }
        City city = new City();
        city.setId(id);
        return city;
    }
}
