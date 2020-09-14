package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.StoreGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StoreGroup} and its DTO {@link StoreGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StoreGroupMapper extends EntityMapper<StoreGroupDTO, StoreGroup> {


    @Mapping(target = "stores", ignore = true)
    @Mapping(target = "removeStore", ignore = true)
    StoreGroup toEntity(StoreGroupDTO storeGroupDTO);

    default StoreGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        StoreGroup storeGroup = new StoreGroup();
        storeGroup.setId(id);
        return storeGroup;
    }
}
