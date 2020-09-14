package com.costrella.cechini.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Warehouse} entity.
 */
public class WarehouseDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;


    private Long locationId;
    
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

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
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
            ", locationId=" + getLocationId() +
            "}";
    }
}
