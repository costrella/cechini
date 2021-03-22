package com.costrella.cechini.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.costrella.cechini.domain.enumeration.OrderFileType;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Warehouse} entity.
 */
public class WarehouseDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String mail;

    private OrderFileType orderFileType;

    
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public OrderFileType getOrderFileType() {
        return orderFileType;
    }

    public void setOrderFileType(OrderFileType orderFileType) {
        this.orderFileType = orderFileType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WarehouseDTO)) {
            return false;
        }

        return id != null && id.equals(((WarehouseDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WarehouseDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", mail='" + getMail() + "'" +
            ", orderFileType='" + getOrderFileType() + "'" +
            "}";
    }
}
