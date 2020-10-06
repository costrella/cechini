package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.StoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Store} and its DTO {@link StoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {WorkerMapper.class, LocationMapper.class, StoreGroupMapper.class})
public interface StoreMapper extends EntityMapper<StoreDTO, Store> {

    @Mapping(source = "worker.id", target = "workerId")
    @Mapping(source = "worker.surname", target = "workerSurname")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.street", target = "locationStreet")
    @Mapping(source = "location.city.name", target = "locationCityName")
    @Mapping(source = "storegroup.id", target = "storegroupId")
    @Mapping(source = "storegroup.name", target = "storegroupName")
    StoreDTO toDto(Store store);

    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "removeNote", ignore = true)
    @Mapping(target = "reports", ignore = true)
    @Mapping(target = "removeReport", ignore = true)
    @Mapping(source = "workerId", target = "worker")
    @Mapping(source = "locationId", target = "location")
    @Mapping(source = "storegroupId", target = "storegroup")
    Store toEntity(StoreDTO storeDTO);

    default Store fromId(Long id) {
        if (id == null) {
            return null;
        }
        Store store = new Store();
        store.setId(id);
        return store;
    }
}
