package com.costrella.cechini.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Route} entity.
 */
public class RouteDTO implements Serializable {
    
    private Long id;

    private String name;


    private Long workerId;

    private String workerSurname;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RouteDTO)) {
            return false;
        }

        return id != null && id.equals(((RouteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RouteDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", workerId=" + getWorkerId() +
            ", workerSurname='" + getWorkerSurname() + "'" +
            ", locationId=" + getLocationId() +
            "}";
    }
}
