package com.costrella.cechini.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Order} entity.
 */
public class OrderDTO implements Serializable {
    
    private Long id;

    @NotNull
    private LocalDate orderDate;


    private Long workerId;

    private String workerSurname;

    private Long storeId;

    private String storeName;

    private Long statusId;

    private String statusName;

    private Long warehouseId;

    private String warehouseName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
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

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        return id != null && id.equals(((OrderDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", workerId=" + getWorkerId() +
            ", workerSurname='" + getWorkerSurname() + "'" +
            ", storeId=" + getStoreId() +
            ", storeName='" + getStoreName() + "'" +
            ", statusId=" + getStatusId() +
            ", statusName='" + getStatusName() + "'" +
            ", warehouseId=" + getWarehouseId() +
            ", warehouseName='" + getWarehouseName() + "'" +
            "}";
    }
}
