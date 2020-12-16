package com.costrella.cechini.service.dto;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Report} entity.
 */

public class ReportDTOSimple implements Serializable {

    private Long id;

    private String desc;

    private String managerNote;

    private int photosCount;

    private String storeName;


    public Long getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getManagerNote() {
        return managerNote;
    }

    public int getPhotosCount() {
        return photosCount;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setManagerNote(String managerNote) {
        this.managerNote = managerNote;
    }

    public void setPhotosCount(int photosCount) {
        this.photosCount = photosCount;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
