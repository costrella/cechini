package com.costrella.cechini.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PhotoFile.
 */
@Entity
@Table(name = "photo_file")
public class PhotoFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "value")
    private byte[] value;

    @Column(name = "value_content_type")
    private String valueContentType;

    @OneToOne(mappedBy = "file")
    @JsonIgnore
    private Photo photo;
    @ManyToOne
    private Tenant tenant;

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }


    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getValue() {
        return value;
    }

    public PhotoFile value(byte[] value) {
        this.value = value;
        return this;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public String getValueContentType() {
        return valueContentType;
    }

    public PhotoFile valueContentType(String valueContentType) {
        this.valueContentType = valueContentType;
        return this;
    }

    public void setValueContentType(String valueContentType) {
        this.valueContentType = valueContentType;
    }

    public Photo getPhoto() {
        return photo;
    }

    public PhotoFile photo(Photo photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhotoFile)) {
            return false;
        }
        return id != null && id.equals(((PhotoFile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhotoFile{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", valueContentType='" + getValueContentType() + "'" +
            "}";
    }
}
