package com.costrella.cechini.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Note} entity.
 */
public class NoteDTO implements Serializable {
    
    private Long id;

    @Size(max = 2000)
    private String value;

    private LocalDate date;


    private Long storeId;

    private Long workerNoteId;

    private Long managerNoteId;

    private Long reportId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getWorkerNoteId() {
        return workerNoteId;
    }

    public void setWorkerNoteId(Long workerId) {
        this.workerNoteId = workerId;
    }

    public Long getManagerNoteId() {
        return managerNoteId;
    }

    public void setManagerNoteId(Long managerId) {
        this.managerNoteId = managerId;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NoteDTO)) {
            return false;
        }

        return id != null && id.equals(((NoteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoteDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", date='" + getDate() + "'" +
            ", storeId=" + getStoreId() +
            ", workerNoteId=" + getWorkerNoteId() +
            ", managerNoteId=" + getManagerNoteId() +
            ", reportId=" + getReportId() +
            "}";
    }
}
