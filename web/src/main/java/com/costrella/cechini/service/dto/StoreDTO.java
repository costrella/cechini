package com.costrella.cechini.service.dto;

import com.costrella.cechini.domain.Tenant;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Store} entity.
 */
public class StoreDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String nip;

    @Size(max = 2000)
    private String desc;

    private Boolean visited;

    private boolean monthVisited;

    @NotNull
    private String address;


    private Long workerId;

    private String workerSurname;

    private Long locationId;

    private Long storegroupId;

    private String storegroupName;

    private Tenant tenant;

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public boolean isMonthVisited() {
        return monthVisited;
    }

    public void setMonthVisited(boolean monthVisited) {
        this.monthVisited = monthVisited;
    }

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

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean isVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
            ", nip='" + getNip() + "'" +
            ", desc='" + getDesc() + "'" +
            ", visited='" + isVisited() + "'" +
            ", address='" + getAddress() + "'" +
            ", workerId=" + getWorkerId() +
            ", workerSurname='" + getWorkerSurname() + "'" +
            ", locationId=" + getLocationId() +
            ", storegroupId=" + getStoregroupId() +
            ", storegroupName='" + getStoregroupName() + "'" +
            "}";
    }
}
