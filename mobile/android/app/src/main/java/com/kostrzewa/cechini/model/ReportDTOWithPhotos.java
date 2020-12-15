package com.kostrzewa.cechini.model;

import java.util.ArrayList;
import java.util.List;

public class ReportDTOWithPhotos extends ReportDTO {

    List<PhotoDTO> photosList = new ArrayList<>();

    public List<PhotoDTO> getPhotosList() {
        return photosList;
    }

    public void setPhotosList(List<PhotoDTO> photosList) {
        this.photosList = photosList;
    }
}
