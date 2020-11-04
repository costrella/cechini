package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.WarehouseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Warehouse} and its DTO {@link WarehouseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WarehouseMapper extends EntityMapper<WarehouseDTO, Warehouse> {


    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "removeOrder", ignore = true)
    Warehouse toEntity(WarehouseDTO warehouseDTO);

    default Warehouse fromId(Long id) {
        if (id == null) {
            return null;
        }
        Warehouse warehouse = new Warehouse();
        warehouse.setId(id);
        return warehouse;
    }
}
