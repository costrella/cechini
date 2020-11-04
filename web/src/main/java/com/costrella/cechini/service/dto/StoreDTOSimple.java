package com.costrella.cechini.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Store} entity.
 */
public class StoreDTOSimple implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String address;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoreDTOSimple)) {
            return false;
        }

        return id != null && id.equals(((StoreDTOSimple) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StoreDTOSimple{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", address='" + address + '\'' +
            '}';
    }
}
