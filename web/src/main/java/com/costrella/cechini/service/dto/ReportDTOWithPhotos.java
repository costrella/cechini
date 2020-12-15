package com.costrella.cechini.service.dto;

import com.costrella.cechini.domain.Photo;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Report} entity.
 */
public class ReportDTOWithPhotos extends ReportDTO {

    List<PhotoDTO> photosList = new ArrayList<>();

    public List<PhotoDTO> getPhotosList() {
        return photosList;
    }

    public void setPhotosList(List<PhotoDTO> photosList) {
        this.photosList = photosList;
    }
}
