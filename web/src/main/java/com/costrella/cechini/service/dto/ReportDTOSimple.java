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

    private Instant reportDate;

    private int photosCount;

    private String storeName;

    private Boolean readByWorker;

    private Boolean readByManager;

    private Long orderId;

    private boolean notesExist;

    public boolean isNotesExist() {
        return notesExist;
    }

    public void setNotesExist(boolean notesExist) {
        this.notesExist = notesExist;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Boolean getReadByWorker() {
        return readByWorker;
    }

    public void setReadByWorker(Boolean readByWorker) {
        this.readByWorker = readByWorker;
    }

    public Boolean getReadByManager() {
        return readByManager;
    }

    public void setReadByManager(Boolean readByManager) {
        this.readByManager = readByManager;
    }

    public Instant getReportDate() {
        return reportDate;
    }

    public void setReportDate(Instant reportDate) {
        this.reportDate = reportDate;
    }

    public Long getId() {
        return id;
    }

    public String getDesc() {
        return desc;
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

    public void setPhotosCount(int photosCount) {
        this.photosCount = photosCount;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
