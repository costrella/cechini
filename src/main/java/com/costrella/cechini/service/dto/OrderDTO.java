package com.costrella.cechini.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Order} entity.
 */
public class OrderDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate orderDate;

    @NotNull
    private LocalDate deliveryDate;

    @Size(max = 2000)
    private String comment;

    private List<OrderItemDTO> orderItems;

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    private Long warehouseId;

    private String warehouseName;

    private Long statusId;

    private String statusName;

    private Long reportId;

    private Long storeId;

    private String storeName;

    private Long workerId;

    private String workerSurname;

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

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
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
            "id=" + id +
            ", orderDate=" + orderDate +
            ", deliveryDate=" + deliveryDate +
            ", comment='" + comment + '\'' +
            ", orderItems=" + orderItems +
            ", warehouseId=" + warehouseId +
            ", warehouseName='" + warehouseName + '\'' +
            ", statusId=" + statusId +
            ", statusName='" + statusName + '\'' +
            ", reportId=" + reportId +
            ", storeId=" + storeId +
            ", storeName='" + storeName + '\'' +
            ", workerId=" + workerId +
            ", workerSurname='" + workerSurname + '\'' +
            '}';
    }
}
