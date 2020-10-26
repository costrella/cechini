package com.costrella.cechini.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Report} entity.
 */
public class ReportDTO implements Serializable {
    
    private Long id;

    private Instant reportDate;

    @Size(max = 2000)
    private String desc;

    @Size(max = 2000)
    private String managerNote;


    private Long orderId;

    private Long workerId;

    private String workerSurname;

    private Long storeId;

    private String storeName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getReportDate() {
        return reportDate;
    }

    public void setReportDate(Instant reportDate) {
        this.reportDate = reportDate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getManagerNote() {
        return managerNote;
    }

    public void setManagerNote(String managerNote) {
        this.managerNote = managerNote;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public String getWorkerSurname() {
        return workerSurname;
    }

    public void setWorkerSurname(String workerSurname) {
        this.workerSurname = workerSurname;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportDTO)) {
            return false;
        }

        return id != null && id.equals(((ReportDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportDTO{" +
            "id=" + getId() +
            ", reportDate='" + getReportDate() + "'" +
            ", desc='" + getDesc() + "'" +
            ", managerNote='" + getManagerNote() + "'" +
            ", orderId=" + getOrderId() +
            ", workerId=" + getWorkerId() +
            ", workerSurname='" + getWorkerSurname() + "'" +
            ", storeId=" + getStoreId() +
            ", storeName='" + getStoreName() + "'" +
            "}";
    }
}
