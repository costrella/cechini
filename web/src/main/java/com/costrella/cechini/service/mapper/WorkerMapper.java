package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.WorkerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Worker} and its DTO {@link WorkerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkerMapper extends EntityMapper<WorkerDTO, Worker> {


    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "removeNote", ignore = true)
    @Mapping(target = "stores", ignore = true)
    @Mapping(target = "removeStore", ignore = true)
    @Mapping(target = "reports", ignore = true)
    @Mapping(target = "removeReport", ignore = true)
    @Mapping(target = "tracks", ignore = true)
    @Mapping(target = "removeTrack", ignore = true)
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
