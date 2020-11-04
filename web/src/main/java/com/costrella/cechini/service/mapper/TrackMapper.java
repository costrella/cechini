package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.TrackDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Track} and its DTO {@link TrackDTO}.
 */
@Mapper(componentModel = "spring", uses = {WorkerMapper.class, LocationMapper.class})
public interface TrackMapper extends EntityMapper<TrackDTO, Track> {

    @Mapping(source = "worker.id", target = "workerId")
    @Mapping(source = "worker.surname", target = "workerSurname")
    @Mapping(source = "location.id", target = "locationId")
    TrackDTO toDto(Track track);

    @Mapping(source = "workerId", target = "worker")
    @Mapping(source = "locationId", target = "location")
    Track toEntity(TrackDTO trackDTO);

    default Track fromId(Long id) {
        if (id == null) {
            return null;
        }
        Track track = new Track();
        track.setId(id);
        return track;
    }
}
