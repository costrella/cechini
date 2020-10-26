package com.costrella.cechini.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Photo.
 */
@Entity
@Table(name = "photo")
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uri")
    private String uri;

    @Column(name = "value_content_type")
    private String valueContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private PhotoFile file;

    @ManyToOne
    @JsonIgnoreProperties(value = "photos", allowSetters = true)
    private Report report;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public Photo uri(String uri) {
        this.uri = uri;
        return this;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getValueContentType() {
        return valueContentType;
    }

    public Photo valueContentType(String valueContentType) {
        this.valueContentType = valueContentType;
        return this;
    }

    public void setValueContentType(String valueContentType) {
        this.valueContentType = valueContentType;
    }

    public PhotoFile getFile() {
        return file;
    }

    public Photo file(PhotoFile photoFile) {
        this.file = photoFile;
        return this;
    }

    public void setFile(PhotoFile photoFile) {
        this.file = photoFile;
    }

    public Report getReport() {
        return report;
    }

    public Photo report(Report report) {
        this.report = report;
        return this;
    }

    public void setReport(Report report) {
        this.report = report;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Photo)) {
            return false;
        }
        return id != null && id.equals(((Photo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Photo{" +
            "id=" + getId() +
            ", uri='" + getUri() + "'" +
            ", valueContentType='" + getValueContentType() + "'" +
            "}";
    }
}
