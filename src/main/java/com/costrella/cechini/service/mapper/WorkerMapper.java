package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.WorkerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Worker} and its DTO {@link WorkerDTO}.
 */
@Mapper(componentModel = "spring", uses = {StatusMapper.class})
public interface WorkerMapper extends EntityMapper<WorkerDTO, Worker> {

    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.name", target = "statusName")
    WorkerDTO toDto(Worker worker);

    @Mapping(target = "stores", ignore = true)
    @Mapping(target = "removeStore", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "removeOrder", ignore = true)
    @Mapping(target = "reports", ignore = true)
    @Mapping(target = "removeReport", ignore = true)
    @Mapping(target = "routes", ignore = true)
    @Mapping(target = "removeRoute", ignore = true)
    @Mapping(source = "statusId", target = "status")
    @Mapping(target = "managers", ignore = true)
    @Mapping(target = "removeManager", ignore = true)
    Worker toEntity(WorkerDTO workerDTO);

    default Worker fromId(Long id) {
        if (id == null) {
            return null;
        }
        Worker worker = new Worker();
        worker.setId(id);
        return worker;
    }
}
