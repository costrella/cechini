package com.costrella.cechini.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Manager} entity.
 */
public class ManagerDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    private Instant hiredDate;

    private Set<WorkerDTO> workers = new HashSet<>();
    
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Instant getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(Instant hiredDate) {
        this.hiredDate = hiredDate;
    }

    public Set<WorkerDTO> getWorkers() {
        return workers;
    }

    public void setWorkers(Set<WorkerDTO> workers) {
        this.workers = workers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ManagerDTO)) {
            return false;
        }

        return id != null && id.equals(((ManagerDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ManagerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", hiredDate='" + getHiredDate() + "'" +
            ", workers='" + getWorkers() + "'" +
            "}";
    }
}
