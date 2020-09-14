package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.ManagerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Manager} and its DTO {@link ManagerDTO}.
 */
@Mapper(componentModel = "spring", uses = {WorkerMapper.class})
public interface ManagerMapper extends EntityMapper<ManagerDTO, Manager> {


    @Mapping(target = "removeWorker", ignore = true)

    default Manager fromId(Long id) {
        if (id == null) {
            return null;
        }
        Manager manager = new Manager();
        manager.setId(id);
        return manager;
    }
}
