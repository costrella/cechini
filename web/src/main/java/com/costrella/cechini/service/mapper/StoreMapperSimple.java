package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.Store;
import com.costrella.cechini.service.dto.StoreDTO;
import com.costrella.cechini.service.dto.StoreDTOSimple;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Store} and its DTO {@link StoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {WorkerMapper.class, LocationMapper.class, StoreGroupMapper.class})
public interface StoreMapperSimple extends EntityMapper<StoreDTOSimple, Store> {

    default StoreDTOSimple toDto(Store store) {
        StoreDTOSimple storeDTO = new StoreDTOSimple();
        storeDTO.setId(store.getId());
        storeDTO.setName(store.getName());
        storeDTO.setAddress(store.getAddress());
        return storeDTO;
    }

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
