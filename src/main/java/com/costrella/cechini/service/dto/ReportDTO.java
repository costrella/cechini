package com.costrella.cechini.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Report} entity.
 */
public class ReportDTO implements Serializable {
    
    private Long id;

    private String number;

    private LocalDate reportDate;

    private String workerDesc;

    private String managerDesc;


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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getWorkerDesc() {
        return workerDesc;
    }

    public void setWorkerDesc(String workerDesc) {
        this.workerDesc = workerDesc;
    }

    public String getManagerDesc() {
        return managerDesc;
    }

    public void setManagerDesc(String managerDesc) {
        this.managerDesc = managerDesc;
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
            ", number='" + getNumber() + "'" +
            ", reportDate='" + getReportDate() + "'" +
            ", workerDesc='" + getWorkerDesc() + "'" +
            ", managerDesc='" + getManagerDesc() + "'" +
            ", workerId=" + getWorkerId() +
            ", workerSurname='" + getWorkerSurname() + "'" +
            ", storeId=" + getStoreId() +
            ", storeName='" + getStoreName() + "'" +
            "}";
    }
}
