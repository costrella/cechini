package com.costrella.cechini.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Store} entity.
 */
public class StoreDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;


    private Long workerId;

    private String workerSurname;

    private Long locationId;

    private String locationCity;

    private Long storegroupId;

    private String storegroupName;
    
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

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public Long getStoregroupId() {
        return storegroupId;
    }

    public void setStoregroupId(Long storeGroupId) {
        this.storegroupId = storeGroupId;
    }

    public String getStoregroupName() {
        return storegroupName;
    }

    public void setStoregroupName(String storeGroupName) {
        this.storegroupName = storeGroupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoreDTO)) {
            return false;
        }

        return id != null && id.equals(((StoreDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StoreDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", workerId=" + getWorkerId() +
            ", workerSurname='" + getWorkerSurname() + "'" +
            ", locationId=" + getLocationId() +
            ", locationCity='" + getLocationCity() + "'" +
            ", storegroupId=" + getStoregroupId() +
            ", storegroupName='" + getStoregroupName() + "'" +
            "}";
    }
}
