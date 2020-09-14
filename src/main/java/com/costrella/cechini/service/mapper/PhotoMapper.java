package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.PhotoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Photo} and its DTO {@link PhotoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ReportMapper.class})
public interface PhotoMapper extends EntityMapper<PhotoDTO, Photo> {

    @Mapping(source = "report.id", target = "reportId")
    PhotoDTO toDto(Photo photo);

    @Mapping(source = "reportId", target = "report")
    Photo toEntity(PhotoDTO photoDTO);

    default Photo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Photo photo = new Photo();
        photo.setId(id);
        return photo;
    }
}
