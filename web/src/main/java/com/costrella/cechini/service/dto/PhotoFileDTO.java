package com.costrella.cechini.service.dto;

import com.costrella.cechini.domain.Tenant;

import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.costrella.cechini.domain.PhotoFile} entity.
 */
public class PhotoFileDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] value;

    private String valueContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public String getValueContentType() {
        return valueContentType;
    }

    public void setValueContentType(String valueContentType) {
        this.valueContentType = valueContentType;
    }

    private Tenant tenant;

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhotoFileDTO)) {
            return false;
        }

        return id != null && id.equals(((PhotoFileDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhotoFileDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
