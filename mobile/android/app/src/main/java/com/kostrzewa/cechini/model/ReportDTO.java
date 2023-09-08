package com.kostrzewa.cechini.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportDTO implements Serializable {

    private Long id;

    private Date reportDate;

    private String desc;

    private String managerNote;

    private int photosCount;

    private Long orderId;

    private Long workerId;

    private String workerSurname;

    private Long storeId;

    private String storeName;

    private boolean notesExist;

    private OrderDTO orderDTO;

    private List<NoteDTO> notes = new ArrayList<>();

    public List<PhotoFileDTO> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoFileDTO> photos) {
        this.photos = photos;
    }

    private List<PhotoFileDTO> photos = new ArrayList<>();

    public List<NoteDTO> getNotes() {
        return notes;
    }

    public boolean isNotesExist() {
        return notesExist;
    }

    public void setNotesExist(boolean notesExist) {
        this.notesExist = notesExist;
    }

    public void setNotes(List<NoteDTO> notes) {
        this.notes = notes;
    }

    private Boolean readByWorker;

    private Boolean readByManager;

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


    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
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

    public int getPhotosCount() {
        return photosCount;
    }

    public void setPhotosCount(int photosCount) {
        this.photosCount = photosCount;
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
