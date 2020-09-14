package com.costrella.cechini.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.costrella.cechini.domain.OrderItem} entity.
 */
public class OrderItemDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Long quantity;


    private Long productId;

    private String productName;

    private Long orderId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItemDTO)) {
            return false;
        }

        return id != null && id.equals(((OrderItemDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItemDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", productId=" + getProductId() +
            ", productName='" + getProductName() + "'" +
            ", orderId=" + getOrderId() +
            "}";
    }
}
