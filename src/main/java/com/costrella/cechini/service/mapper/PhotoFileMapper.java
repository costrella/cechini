package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.PhotoFileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PhotoFile} and its DTO {@link PhotoFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PhotoFileMapper extends EntityMapper<PhotoFileDTO, PhotoFile> {


    @Mapping(target = "photo", ignore = true)
    PhotoFile toEntity(PhotoFileDTO photoFileDTO);

    default PhotoFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        PhotoFile photoFile = new PhotoFile();
        photoFile.setId(id);
        return photoFile;
    }
}
